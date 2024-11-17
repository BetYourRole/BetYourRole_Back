package ces.betyourrole.dto;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class UpdateTodoRequest {
    private Long id;
    private String password;
    private String name;
    private String inscription;
}
