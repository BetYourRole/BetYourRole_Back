package ces.betyourrole.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "participant")
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participant_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participant_active_session", nullable = true)
    private Member activeSession; // 로그인하지 않은 경우 null

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private TodoRoom room; // 참가자가 속한 방

    @Column(nullable = false, name = "participant_name")
    private String name; // 참가자명

    @Column(nullable = false, updatable = false)
    private LocalDateTime createDate = LocalDateTime.now(); // 생성일자

    @Column(nullable = false)
    private LocalDateTime updateDate = LocalDateTime.now(); // 수정 시 자동 반영

    @Column
    private String password; // 수정 비밀번호 (비회원 전용)

    private Participant(TodoRoom room, String name) {
        this.room = room;
        this.name = name;
        this.createDate = this.updateDate = LocalDateTime.now();
    }

    public Participant(Member member, TodoRoom room, String name){
        this(room, name);
        activeSession = member;
    }

    public Participant(String password, TodoRoom room, String name){
        this(room, name);

        //비밀번호 암호화 로직 추가
        this.password = password;
    }
}
