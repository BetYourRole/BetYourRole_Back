package ces.betyourrole.service;

import ces.betyourrole.domain.MatchingState;
import ces.betyourrole.domain.Participant;
import ces.betyourrole.domain.Todo;
import ces.betyourrole.domain.TodoRoom;
import ces.betyourrole.dto.CreateTodoRoomRequest;
import ces.betyourrole.dto.OnlyPasswordRequest;
import ces.betyourrole.dto.CheckPermissionResponse;
import ces.betyourrole.dto.TodoRoomResponse;
import ces.betyourrole.exception.*;
import ces.betyourrole.repository.TodoRepository;
import ces.betyourrole.repository.TodoRoomRepository;
import ces.betyourrole.selector.DetermineWinnerAlgorithm;
import ces.betyourrole.selector.SelectorFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class TodoRoomService {

    private final MemberService memberService;
    private final TodoRoomRepository todoRoomRepository;
    private final TodoRepository todoRepository;

    private final SelectorFactory selectorFactory;

    private final ParticipantQueryService participantQueryService;
    private final TodoRoomQueryService todoRoomQueryService;

    public TodoRoomResponse createTodoRoom(String token, CreateTodoRoomRequest request){
        TodoRoom room;
        if(token != null && !token.isEmpty()) room = request.toEntity(memberService.getMemberByToken(token));
        else room = request.toEntity();

        todoRoomRepository.save(room);

        List<Todo> todos = request.getTodos().stream()
                .map(todoDTO -> todoDTO.toEntity(room)).toList();

        todoRepository.saveAll(todos);

        return new TodoRoomResponse(room, todos,new ArrayList<>());
    }

    public TodoRoomResponse determineWinner(String token, OnlyPasswordRequest request, String roomURL){
        TodoRoom room = todoRoomQueryService.findByRandomURL(roomURL);
        if(room.getState() != MatchingState.BEFORE) throw new CompletedTodoRoomException();

        if(!memberService.isAccessible(token, room.getActiveSession())) {
            if (!room.isPasswordCorrect(request.getPassword())) throw new InvalidPasswordException();
        }
        List<Participant> participants = participantQueryService.findByRoom(room);
        if(!Objects.equals(participants.size(), todoRoomQueryService.CountTodosByTodoRoom(room))){
            throw new InvalidCapacityException("역할의 수와 참가자의 수가 일치하지 않습니다.");
        }

        DetermineWinnerAlgorithm selector = selectorFactory.getAlgorithm(room.getMatchingType());
        Map<Long, Long> winners = selector.determineWinner(todoRoomQueryService.findBettingsByTodoRoom(room));

        List<Todo> todos = todoRoomQueryService.findTodosByRoom(room);
        todos.forEach(todo -> todo.setWinner(participantQueryService.findById(winners.get(todo.getId()))));
        room.completeRoom();

        return new TodoRoomResponse(room, todos, participants);
    }

    @Transactional(readOnly = true)
    public TodoRoomResponse getRoomData(String url) {
        TodoRoom room = todoRoomQueryService.findByRandomURL(url);
        return new TodoRoomResponse(room, todoRoomQueryService.findTodosByRoom(room), participantQueryService.findByRoom(room));
    }

    @Transactional(readOnly = true)
    public CheckPermissionResponse checkPermission(String token, OnlyPasswordRequest request, String roomURL){
        TodoRoom room = todoRoomQueryService.findByRandomURL(roomURL);
        if(!memberService.isAccessible(token, room.getActiveSession())) {
            if (!room.isPasswordCorrect(request.getPassword())) return new CheckPermissionResponse(false);
        }
        return new CheckPermissionResponse(true);
    }


}
