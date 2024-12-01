package ces.betyourrole.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CheckPermissionResponse {
    boolean isValid;

    public CheckPermissionResponse(boolean isValid) {
        this.isValid = isValid;
    }
}
