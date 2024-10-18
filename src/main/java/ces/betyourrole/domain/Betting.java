package ces.betyourrole.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Table(
        name = "betting",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"todo_id", "participant_id"})
        }
)
public class Betting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "betting_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todo_id", nullable = false)
    private Todo todo; // 연관된 Todo

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participant_id", nullable = false)
    private Participant participant; // 연관된 Participant

    @Column(nullable = false)
    private Integer point = 0; // 배팅 금액 (기본값 0)

    @Column
    private String comment; // 배팅에 대한 코멘트

    // 생성자
    public Betting(Todo todo, Participant participant, Integer point, String comment) {
        this.todo = todo;
        this.participant = participant;
        this.point = point;
        this.comment = comment;
    }
}
