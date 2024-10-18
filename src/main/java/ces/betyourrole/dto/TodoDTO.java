package ces.betyourrole.dto;

import ces.betyourrole.domain.Todo;
import ces.betyourrole.domain.TodoRoom;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class TodoDTO {

    private String name;
    private String inscription;

    public Todo toEntity(TodoRoom room){
        return new Todo(room, this.name, this.inscription);
    }

}
