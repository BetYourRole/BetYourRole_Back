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
    private String description;
    private String winner;

    public TodoResponse(Todo todo){
        this.id = todo.getId();
        this.name = todo.getName();
        this.description = todo.getDescription();
        if(todo.getWinner() != null) this.winner = todo.getWinner().getName();
    }
}
