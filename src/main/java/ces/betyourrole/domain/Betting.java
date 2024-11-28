package ces.betyourrole.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    @Getter
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todo_id", nullable = false)
    private Todo todo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)//임시
    private Participant participant;

    @Column(nullable = false)
    private Integer point = 0;

    @Column
    private String comment;

    public Betting(Todo todo, Participant participant, Integer point, String comment) {
        this.todo = todo;
        this.participant = participant;
        this.point = point;
        this.comment = comment;
    }

}
