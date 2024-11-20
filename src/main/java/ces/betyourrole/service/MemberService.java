package ces.betyourrole.service;

import ces.betyourrole.domain.Member;
import ces.betyourrole.domain.MemberState;
import ces.betyourrole.repository.MemberRepository;
import ces.betyourrole.security.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    // 사용자 등록(구글 로그인 시 자동 등록)
    @Transactional
    public Member registerMember(String email, String name) {
        return memberRepository.findByEmail(email)
                .orElseGet(() -> memberRepository.save(new Member(email, name)));
    }

    // 사용자 조회(기본키)
    public Member getMemberById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }

    // 사용자 조회(이메일)
    public Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }

    public Member getMemberByToken(String token){
        return getMemberByEmail(jwtTokenProvider.getEmailFromBearerToken(token));
    }

    public Member getMemberByToken(HttpServletRequest request){
        return getMemberByEmail(jwtTokenProvider.getEmailFromBearerToken(request));
    }

    // 사용자 조회(닉네임)
    public List<Member> getMemberByName(String name) {
        List<Member> members = memberRepository.findByName(name);
        if (members.isEmpty()) {
            throw new IllegalArgumentException("해당 이름을 가진 사용자를 찾을 수 없습니다.");
        }

        return memberRepository.findByName(name);
    }

    // 활성 상태인 멤버 조회
    public List<Member> getActiveMembers() {
        return memberRepository.findByMemberState(MemberState.ACTIVE);
    }

    // 회원 탈퇴
    @Transactional
    public void deactivateMember(Long id) {
        Member member = getMemberById(id);
        member.deactivate();
        memberRepository.save(member);
    }
}
