package ces.betyourrole.controller;

import ces.betyourrole.dto.CreateTodoRoomRequest;
import ces.betyourrole.dto.OnlyPasswordRequest;
import ces.betyourrole.dto.TodoRoomResponse;
import ces.betyourrole.service.TodoRoomService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{roomId}")
    public ResponseEntity<TodoRoomResponse> getTodoRoom(@PathVariable("roomId") String key){
        TodoRoomResponse response = todoRoomService.getRoomData(key);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{roomId}/draw")
    public ResponseEntity<TodoRoomResponse> drawTodoRoom(HttpServletRequest header, @PathVariable("roomId") String key, @RequestBody OnlyPasswordRequest request){
        TodoRoomResponse response = todoRoomService.determineWinner(header.getHeader("Authorization"), request, key);

        return ResponseEntity.ok(response);
    }

}
