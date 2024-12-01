package ces.betyourrole.service;

import ces.betyourrole.domain.Member;
import ces.betyourrole.dto.MyPageTodoRoomResponse;
import ces.betyourrole.repository.MemberRepository;
import ces.betyourrole.repository.ParticipantRepository;
import ces.betyourrole.repository.TodoRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberQueryService {
    private final ParticipantRepository participantRepository;
    private final TodoRoomRepository todoRoomRepository;

    // 내가 참여한 방 조회
    public List<MyPageTodoRoomResponse> getParticipatedTodoRoomDTOs(Member member) {
        return participantRepository.findTodoRoomsByMember(member.getId());
    }

    // 내가 만든 방 조회
    public List<MyPageTodoRoomResponse> getCreatedTodoRoomDTOs(Member member) {
        return todoRoomRepository.findCreatedTodoRoomsByMember(member.getId());
    }
}
