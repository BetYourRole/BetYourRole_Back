package ces.betyourrole.service;

import ces.betyourrole.domain.Betting;
import ces.betyourrole.domain.Participant;
import ces.betyourrole.domain.TodoRoom;
import ces.betyourrole.dto.AddParticipantRequest;
import ces.betyourrole.dto.BettingDTO;
import ces.betyourrole.dto.ParticipantResponse;
import ces.betyourrole.exception.IdNotFoundException;
import ces.betyourrole.exception.InvalidRangeException;
import ces.betyourrole.repository.BettingRepository;
import ces.betyourrole.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ParticipantService {

    private final TodoRoomService todoRoomService;
    private final ParticipantRepository participantRepository;
    private final BettingRepository bettingRepository;

    public ParticipantResponse addParticipant(String token, AddParticipantRequest request){

        /**
         * 추가 필요 기능
         * todo-room과 todo가 서로 연관되어있는 것이 맞는지 검사
         * bettings의 개수가 todo-room의 역할 개수와 일치하는지 검사
        */

        TodoRoom room = todoRoomService.findById(request.getRoomId());

        Participant p;
        //토큰 검증 로직 필요
        p = request.toEntity(room);
        participantRepository.save(p);

        if(request.getBettings().stream().mapToInt(BettingDTO::getPoint).sum() > room.getPoint()){
            throw new InvalidRangeException("합계 포인트가 최대 포인트를 초과합니다.");
        }

        List<Betting> bettings = request.getBettings().stream().map(dto -> dto.toEntity(todoRoomService.getTodo(dto.getTodoId()), p)).toList();

        try{
            bettingRepository.saveAll(bettings);
        }catch(DataIntegrityViolationException e){
            throw new IdNotFoundException("올바르지 않은 역할 정보가 포함되어 있습니다.");
        }

        return new ParticipantResponse(p);

    }

}
