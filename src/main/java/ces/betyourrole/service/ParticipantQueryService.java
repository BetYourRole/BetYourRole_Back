package ces.betyourrole.service;

import ces.betyourrole.domain.Betting;
import ces.betyourrole.domain.Participant;
import ces.betyourrole.domain.TodoRoom;
import ces.betyourrole.exception.IdNotFoundException;
import ces.betyourrole.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ParticipantQueryService {

    private final ParticipantRepository participantRepository;

    public Integer countParticipantsByRoom(TodoRoom room){
        return participantRepository.countByRoom(room);
    }

    public Participant findById(Long id) {
        return participantRepository.findById(id).orElseThrow(() -> new IdNotFoundException("해당 참여자를 찾을 수 없습니다."));
    }

    public List<Participant> findByRoom(TodoRoom room){
        return participantRepository.findByRoom(room);
    }

}
