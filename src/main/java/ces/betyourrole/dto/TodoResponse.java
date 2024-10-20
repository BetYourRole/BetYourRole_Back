package ces.betyourrole.dto;

import ces.betyourrole.domain.Todo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TodoResponse {
    private Long id;
    private String name;
    private String inscription;

    public TodoResponse(Todo todo){
        this.id = todo.getId();
        this.name = todo.getName();
        this.inscription = todo.getInscription();
    }
}
