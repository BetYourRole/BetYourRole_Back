package ces.betyourrole.dto;

import ces.betyourrole.domain.MatchingState;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MyPageTodoRoomResponse {
    private String name;
    private String description;
    private String url;
    private MatchingState state;
    private Integer participantCount;

    public MyPageTodoRoomResponse(String name, String description, String url, MatchingState state, long participantCount) {
        this.name = name;
        this.description = description;
        this.url = url;
        this.state = state;
        this.participantCount = (int) participantCount;
    }
}
