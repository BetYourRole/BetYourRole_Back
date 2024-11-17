package ces.betyourrole.service;

import ces.betyourrole.domain.MatchingState;
import ces.betyourrole.domain.Todo;
import ces.betyourrole.dto.TodoResponse;
import ces.betyourrole.dto.UpdateTodoRequest;
import ces.betyourrole.exception.CompletedTodoRoomException;
import ces.betyourrole.exception.IdNotFoundException;
import ces.betyourrole.exception.InvalidPasswordException;
import ces.betyourrole.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoResponse updateTodo(String token, UpdateTodoRequest request, Long todoId){
        Todo todo = todoRepository.findById(todoId).orElseThrow(() -> new IdNotFoundException("존재하지 않는 역할입니다."));
        //token검증 else
        if(!todo.getRoom().isPasswordCorrect(request.getPassword())) throw new InvalidPasswordException();
        if(todo.getRoom().getState() != MatchingState.BEFORE) throw new CompletedTodoRoomException();
        todo.updateTodo(request.getName(), request.getInscription());

        return new TodoResponse(todo);
    }
}
