package ces.betyourrole.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class DetermineWinnerRequest {

    private Long id;
    private String password;

}
