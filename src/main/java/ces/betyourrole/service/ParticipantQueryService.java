package ces.betyourrole.service;

import ces.betyourrole.domain.TodoRoom;
import ces.betyourrole.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ParticipantQueryService {

    private final ParticipantRepository participantRepository;

    public Integer countParticipantsByRoom(TodoRoom room){
        return participantRepository.countByRoom(room);
    }
}
