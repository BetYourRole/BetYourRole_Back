package ces.betyourrole.domain;

import ces.betyourrole.exception.RequiredFieldMissingException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "todo")
@NoArgsConstructor
@Getter
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private TodoRoom room;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "winner")
    private Participant winner; // 낙찰자

    @Column(nullable = false, name = "todo_name")
    private String name;

    @Column(name = "todo_inscription")
    private String inscription;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createDate;

    public Todo(TodoRoom room, String name, String inscription) {
        this.room = room;
        if(name==null || name.isEmpty()){
            throw new RequiredFieldMissingException("역할의 이름은 필수 입력입니다.");
        }
        this.name = name;
        this.inscription = inscription;
        this.createDate = LocalDateTime.now();
    }

    public void updateTodo(String name, String inscription){
        this.name = name;
        this.inscription = inscription;
    }

}
