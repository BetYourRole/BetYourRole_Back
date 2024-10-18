package ces.betyourrole.service;

import ces.betyourrole.domain.Todo;
import ces.betyourrole.domain.TodoRoom;
import ces.betyourrole.dto.CreateTodoRoomRequest;
import ces.betyourrole.dto.TodoRoomResponse;
import ces.betyourrole.exception.InvalidRangeException;
import ces.betyourrole.repository.TodoRepository;
import ces.betyourrole.repository.TodoRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoRoomService {

    private final TodoRoomRepository todoRoomRepository;
    private final TodoRepository todoRepository;

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

        return new TodoRoomResponse(room);
    }
}
