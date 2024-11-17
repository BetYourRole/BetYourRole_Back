package ces.betyourrole.service;

import ces.betyourrole.domain.Todo;
import ces.betyourrole.domain.TodoRoom;
import ces.betyourrole.dto.CreateTodoRoomRequest;
import ces.betyourrole.dto.DetermineWinnerRequest;
import ces.betyourrole.dto.TodoRoomResponse;
import ces.betyourrole.exception.InvalidPasswordException;
import ces.betyourrole.exception.InvalidRangeException;
import ces.betyourrole.exception.IdNotFoundException;
import ces.betyourrole.repository.BettingRepository;
import ces.betyourrole.repository.TodoRepository;
import ces.betyourrole.repository.TodoRoomRepository;
import ces.betyourrole.selector.DetermineWinnerAlgorithm;
import ces.betyourrole.selector.SelectorFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoRoomService {

    private final TodoRoomRepository todoRoomRepository;
    private final TodoRepository todoRepository;
    private final ParticipantQueryService participantQueryService;
    private final SelectorFactory selectorFactory;
    private final BettingRepository bettingRepository;

    @Transactional
    public TodoRoomResponse createTodoRoom(String token, CreateTodoRoomRequest request){
        if(request.getTodos().size() != request.getHeadCount()){
            throw new InvalidRangeException("인원 수와 역할의 수가 일치하지 않습니다.");
        }

        TodoRoom room;
//        if(token...) 토큰 확인 및 검증 로직 필요
        room = request.toEntity();

        todoRoomRepository.save(room);

        List<Todo> todos = request.getTodos().stream()
                .map(todoDTO -> todoDTO.toEntity(room)).toList();

        todoRepository.saveAll(todos);

        return new TodoRoomResponse(room, todos,0);
    }

    public TodoRoomResponse getRoomData(Long id){
        TodoRoom room = findById(id);
        return new TodoRoomResponse(room, findTodosByRoom(room), participantQueryService.countParticipantsByRoom(room));
    }

    public TodoRoom findById(Long id){
        return todoRoomRepository.findById(id).orElseThrow(IdNotFoundException::new);
    }

    public List<Todo> findTodosByRoom(TodoRoom room){
        return todoRepository.findByRoom(room);
    }

    public Todo getTodo(Long id){
        return todoRepository.getReferenceById(id);
    }

    @Transactional
    public TodoRoomResponse determineWinner(String token, DetermineWinnerRequest request){
        TodoRoom room = todoRoomRepository.findById(request.getId()).orElseThrow(IdNotFoundException::new);
        //        if (token) ... 암튼 토큰 검증 로직 필요 else
        if(!room.isPasswordCorrect(request.getPassword())) throw new InvalidPasswordException();

        Integer participantsCount = participantQueryService.countParticipantsByRoom(room);
        room.isValidParticipantCount(participantsCount);

        DetermineWinnerAlgorithm selector = selectorFactory.getAlgorithm(room.getMatchingType());
        Map<Long, Long> winners = selector.determineWinner(bettingRepository.findByTodo_room(room));

        List<Todo> todos = findTodosByRoom(room);
        todos.forEach(todo -> todo.setWinner(participantQueryService.findById(winners.get(todo.getId()))));

        return new TodoRoomResponse(room, todos, participantsCount);
    }


}
