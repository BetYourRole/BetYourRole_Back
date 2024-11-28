package ces.betyourrole.controller;

import ces.betyourrole.dto.AddParticipantRequest;
import ces.betyourrole.dto.OnlyPasswordRequest;
import ces.betyourrole.dto.ParticipantResponse;
import ces.betyourrole.service.ParticipantService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/participant")
public class ParticipantController {
    private final ParticipantService participantService;

    @PostMapping("/{todoRoomId}")
    public ResponseEntity<ParticipantResponse> addParticipant(HttpServletRequest header, @PathVariable("todoRoomId") String roomURL, @RequestBody AddParticipantRequest request){
        ParticipantResponse response = participantService.addParticipant(header.getHeader("Authorization"), roomURL, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{memberId}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // 204 상태 코드
    public void deleteResource(HttpServletRequest header, @PathVariable("memberId") Long participantId, @RequestBody OnlyPasswordRequest request) {
        participantService.deleteParticipant(header.getHeader("Authorization"), participantId, request);
    }

}
