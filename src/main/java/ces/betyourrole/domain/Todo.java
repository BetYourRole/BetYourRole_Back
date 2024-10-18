package ces.betyourrole.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "winner")
    private Participant winner; // 낙찰자

    @Column(nullable = false)
    private String name;

    private String inscription;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createDate = LocalDateTime.now();


    // 생성자
    public Todo(TodoRoom room, String name, String inscription, Participant winner) {
        this.room = room;
        this.name = name;
        this.inscription = inscription;
        this.winner = winner;
        this.createDate = LocalDateTime.now();
    }
}
