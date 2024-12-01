package ces.betyourrole.controller;

import ces.betyourrole.dto.CreateTodoRoomRequest;
import ces.betyourrole.dto.OnlyPasswordRequest;
import ces.betyourrole.dto.CheckPermissionResponse;
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

    @GetMapping("/{key}")
    public ResponseEntity<TodoRoomResponse> getTodoRoom(@PathVariable("key") String key){
        TodoRoomResponse response = todoRoomService.getRoomData(key);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{key}/draw")
    public ResponseEntity<TodoRoomResponse> drawTodoRoom(HttpServletRequest header, @PathVariable("key") String key, @RequestBody OnlyPasswordRequest request){
        TodoRoomResponse response = todoRoomService.determineWinner(header.getHeader("Authorization"), request, key);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{key}/check-permission")
    public ResponseEntity<CheckPermissionResponse> checkPermission(HttpServletRequest header, @PathVariable("key") String key, @RequestBody OnlyPasswordRequest request){
        CheckPermissionResponse response = todoRoomService.checkPermission(header.getHeader("Authorization"), request, key);
        return ResponseEntity.ok(response);
    }

}
