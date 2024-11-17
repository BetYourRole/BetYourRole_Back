package ces.betyourrole.service;

import ces.betyourrole.domain.*;
import ces.betyourrole.dto.AddParticipantRequest;
import ces.betyourrole.dto.BettingRequest;
import ces.betyourrole.dto.ParticipantResponse;
import ces.betyourrole.exception.*;
import ces.betyourrole.repository.BettingRepository;
import ces.betyourrole.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ParticipantService {

    private final TodoRoomQueryService todoRoomQueryService;
    private final ParticipantRepository participantRepository;
    private final BettingRepository bettingRepository;

    public ParticipantResponse addParticipant(String token, AddParticipantRequest request){

        TodoRoom room = todoRoomQueryService.findById(request.getRoomId());
        if(room.getState() != MatchingState.BEFORE){
            throw new ParticipationNotAllowedException("참여가 불가능한 방입니다.");
        }

        isValidBettingList(room, request.getBettings());

        Participant p;
        //토큰 검증 로직 필요
        p = request.toEntity(room);

        if(request.getBettings().stream().mapToInt(BettingRequest::getPoint).sum() > room.getPoint()){
            throw new InvalidRangeException("합계 포인트가 최대 포인트를 초과합니다.");
        }

        List<Betting> bettings = request.getBettings().stream().map(dto -> dto.toEntity(todoRoomQueryService.getTodo(dto.getTodoId()), p)).toList();

        try{
            participantRepository.save(p);
            bettingRepository.saveAll(bettings);
        }catch(DataIntegrityViolationException e){
            throw new IdNotFoundException("올바르지 않은 역할 정보가 포함되어 있습니다.");
        }

        return new ParticipantResponse(p, bettings);

    }

    private void isValidBettingList(TodoRoom room, List<BettingRequest> request){

        Set<Long> requestedTodoIds = request
                .stream()
                .map(BettingRequest::getTodoId)
                .collect(Collectors.toSet());

        Set<Long> roomTodoIds = todoRoomQueryService.findTodosByRoom(room)
                .stream()
                .map(Todo::getId)
                .collect(Collectors.toSet());

        if (!requestedTodoIds.equals(roomTodoIds)) {
            throw new InvalidBettingDataException("방 정보와 베팅 정보가 일치하지 않습니다.");
        }
    }

}
