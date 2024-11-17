package ces.betyourrole.controller;

import ces.betyourrole.dto.CreateTodoRoomRequest;
import ces.betyourrole.dto.DetermineWinnerRequest;
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
    public ResponseEntity<TodoRoomResponse> getTodoRoom(@PathVariable("roomId") Long roomId){
        TodoRoomResponse response = todoRoomService.getRoomData(roomId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/draw")
    public ResponseEntity<TodoRoomResponse> drawTodoRoom(HttpServletRequest header, @RequestBody DetermineWinnerRequest request){
        TodoRoomResponse response = todoRoomService.determineWinner(header.getHeader("Authorization"), request);

        return ResponseEntity.ok(response);
    }

//    @PutMapping("/{roomId}")
//    public ResponseEntity<TodoRoomResponse> updateTodoRoom(@PathVariable("roomId") Long roomId){
////        TodoRoomResponse response = todoRoomService.getRoomData(roomId);
////
////        return ResponseEntity.ok(response);
//    }
}
