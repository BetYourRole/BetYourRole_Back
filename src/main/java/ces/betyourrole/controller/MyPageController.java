package ces.betyourrole.controller;

import ces.betyourrole.domain.Member;
import ces.betyourrole.dto.MyPageTodoRoomResponse;
import ces.betyourrole.service.MemberQueryService;
import ces.betyourrole.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/my-page")
@RequiredArgsConstructor
public class MyPageController {
    private final MemberService memberService;
    private final MemberQueryService memberQueryService;

    // 내가 참여한 방 목록 조회
    @GetMapping("/participated-rooms")
    public ResponseEntity<List<MyPageTodoRoomResponse>> getParticipatedRooms(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        Member member = memberService.getMemberByToken(token);

        List<MyPageTodoRoomResponse> participatedRooms = memberQueryService.getParticipatedTodoRoomDTOs(member);

        return ResponseEntity.ok(participatedRooms);
    }

    // 내가 만든 방 목록 조회
    @GetMapping("/created-rooms")
    public ResponseEntity<List<MyPageTodoRoomResponse>> getCreatedRooms(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        Member member = memberService.getMemberByToken(token);

        List<MyPageTodoRoomResponse> createdRooms = memberQueryService.getCreatedTodoRoomDTOs(member);

        return ResponseEntity.ok(createdRooms);
    }
}
