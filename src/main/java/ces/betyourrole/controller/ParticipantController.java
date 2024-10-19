package ces.betyourrole.controller;

import ces.betyourrole.dto.AddParticipantRequest;
import ces.betyourrole.dto.ParticipantResponse;
import ces.betyourrole.service.ParticipantService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/participant")
public class ParticipantController {
    private final ParticipantService participantService;

    @PostMapping("")
    public ResponseEntity<ParticipantResponse> addParticipant(HttpServletRequest header, @RequestBody AddParticipantRequest request){
        ParticipantResponse response = participantService.addParticipant(header.getHeader("Authorization"), request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
