package ces.betyourrole.dto;

import ces.betyourrole.domain.Betting;
import ces.betyourrole.domain.Todo;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TodoResponse {
    private Long id;
    private String name;
    private String description;
    private WinnerResponse winner;


    public TodoResponse(Todo todo){
        this.id = todo.getId();
        this.name = todo.getName();
        this.description = todo.getDescription();
    }

    public TodoResponse(Todo todo, List<Betting> bettings){
        this(todo);
        if(todo.getWinner() != null) this.winner = new WinnerResponse(todo, bettings);
    }
}
