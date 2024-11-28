package ces.betyourrole.service;

import ces.betyourrole.domain.Betting;
import ces.betyourrole.domain.Todo;
import ces.betyourrole.domain.TodoRoom;
import ces.betyourrole.exception.IdNotFoundException;
import ces.betyourrole.exception.URLNotFoundException;
import ces.betyourrole.repository.BettingRepository;
import ces.betyourrole.repository.TodoRepository;
import ces.betyourrole.repository.TodoRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TodoRoomQueryService {

    private final TodoRoomRepository todoRoomRepository;
    private final TodoRepository todoRepository;
    private final BettingRepository bettingRepository;

    public List<Betting> findBettingsByTodoRoom(TodoRoom room){
        return bettingRepository.findByTodo_room(room);
    }

    public TodoRoom findById(Long id) {
        return todoRoomRepository.findById(id).orElseThrow(IdNotFoundException::new);
    }

    public List<Todo> findTodosByRoom(TodoRoom room) {
        return todoRepository.findByRoom(room);
    }

    public Todo getTodo(Long id) {
        return todoRepository.getReferenceById(id);
    }

    public Integer CountTodosByTodoRoom(TodoRoom room){
        return todoRepository.countByRoom(room);
    }

    public TodoRoom findByRandomURL(String url){
        return todoRoomRepository.findByRandomKey(url).orElseThrow(URLNotFoundException::new);
    }
}