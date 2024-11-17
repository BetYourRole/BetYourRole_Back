package ces.betyourrole.service;

import ces.betyourrole.domain.MatchingState;
import ces.betyourrole.domain.Todo;
import ces.betyourrole.domain.TodoRoom;
import ces.betyourrole.dto.CreateTodoRoomRequest;
import ces.betyourrole.dto.DetermineWinnerRequest;
import ces.betyourrole.dto.TodoRoomResponse;
import ces.betyourrole.exception.*;
import ces.betyourrole.repository.TodoRepository;
import ces.betyourrole.repository.TodoRoomRepository;
import ces.betyourrole.selector.DetermineWinnerAlgorithm;
import ces.betyourrole.selector.SelectorFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoRoomService {

    private final TodoRoomRepository todoRoomRepository;
    private final TodoRepository todoRepository;

    private final SelectorFactory selectorFactory;

    private final ParticipantQueryService participantQueryService;
    private final TodoRoomQueryService todoRoomQueryService;

    @Transactional
    public TodoRoomResponse createTodoRoom(String token, CreateTodoRoomRequest request){
        TodoRoom room;
//        if(token...) 토큰 확인 및 검증 로직 필요
        room = request.toEntity();

        todoRoomRepository.save(room);

        List<Todo> todos = request.getTodos().stream()
                .map(todoDTO -> todoDTO.toEntity(room)).toList();

        todoRepository.saveAll(todos);

        return new TodoRoomResponse(room, todos,0);
    }

    @Transactional
    public TodoRoomResponse determineWinner(String token, DetermineWinnerRequest request){
        TodoRoom room = todoRoomRepository.findById(request.getId()).orElseThrow(IdNotFoundException::new);
        if(room.getState() != MatchingState.BEFORE) throw new CompletedTodoRoomException();
        //        if (token) ... 암튼 토큰 검증 로직 필요 else
        if(!room.isPasswordCorrect(request.getPassword())) throw new InvalidPasswordException();

        Integer participantsCount = participantQueryService.countParticipantsByRoom(room);
        if(!Objects.equals(participantsCount, todoRoomQueryService.CountTodosByTodoRoom(room))){
            throw new InvalidCapacityException("역할의 수와 참가자의 수가 일치하지 않습니다.");
        }

        DetermineWinnerAlgorithm selector = selectorFactory.getAlgorithm(room.getMatchingType());
        Map<Long, Long> winners = selector.determineWinner(todoRoomQueryService.findBettingsByTodoRoom(room));

        List<Todo> todos = todoRoomQueryService.findTodosByRoom(room);
        todos.forEach(todo -> todo.setWinner(participantQueryService.findById(winners.get(todo.getId()))));
        room.completeRoom();

        return new TodoRoomResponse(room, todos, participantsCount);
    }

    @Transactional(readOnly = true)
    public TodoRoomResponse getRoomData(Long id) {
        TodoRoom room = todoRoomQueryService.findById(id);
        return new TodoRoomResponse(room, todoRoomQueryService.findTodosByRoom(room), participantQueryService.countParticipantsByRoom(room));
    }

}
