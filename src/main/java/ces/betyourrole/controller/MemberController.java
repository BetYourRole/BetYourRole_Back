package ces.betyourrole.controller;

import ces.betyourrole.domain.Member;
import ces.betyourrole.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    // 기본키로 멤버 조회
    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable Long id) {
        Member member = memberService.getMemberById(id);
        return ResponseEntity.ok(member);
    }

    // 이름으로 멤버 조회
    @GetMapping("/name/{name}")
    public ResponseEntity<List<Member>> getMembersByName(@PathVariable String name) {
        List<Member> members = memberService.getMemberByName(name);

        return ResponseEntity.ok(members);
    }

    // 이메일로 멤버 조회
    @GetMapping("/name/{email}")
    public ResponseEntity<Member> getMembersByEmail(@PathVariable String email) {
        Member members = memberService.getMemberByEmail(email);

        return ResponseEntity.ok(members);
    }
}
