package ces.betyourrole.controller;

import ces.betyourrole.dto.CreateTodoRoomRequest;
import ces.betyourrole.dto.TodoRoomResponse;
import ces.betyourrole.service.TodoRoomService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/todo-room")
@RequiredArgsConstructor
public class TodoRoomController {

    private final TodoRoomService todoRoomService;
    @PostMapping("")
    public ResponseEntity<TodoRoomResponse> createTodoRoom(HttpServletRequest header, @RequestBody CreateTodoRoomRequest request){
        TodoRoomResponse response = todoRoomService.createTodoRoom(header.getHeader("Authorization"), request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
