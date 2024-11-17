package ces.betyourrole.controller;

import ces.betyourrole.dto.TodoResponse;
import ces.betyourrole.dto.UpdateTodoRequest;
import ces.betyourrole.service.TodoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/todo")
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;

    @PutMapping("/{todoId}")
    public ResponseEntity<TodoResponse> getTodoRoom(HttpServletRequest header, @RequestBody UpdateTodoRequest request, @PathVariable("todoId") Long roomId){
        TodoResponse response = todoService.updateTodo(header.getHeader("Authorization"), request, roomId);

        return ResponseEntity.ok(response);
    }
}
