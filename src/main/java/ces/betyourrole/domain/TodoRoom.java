package ces.betyourrole.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "todo_room")
public class TodoRoom {

    @Id @GeneratedValue @Column(name = "todo_room_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_active_session", nullable = true)
    private Member activeSession; // 로그인하지 않은 경우 null

    @Column(nullable = false)
    private String name; // 방 이름

    @Column
    private String inscription; // 방 설명

    @Column(nullable = false)
    private Integer headCount; // 인원수 (2 이상)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MatchingType matchingType; // 낙찰 알고리즘

    @Column(nullable = false)
    private Integer point = 100; // 인당 배팅 가능 포인트 (기본값 100)

    @Column(nullable = false, updatable = false)
    private LocalDateTime createDate = LocalDateTime.now(); // 생성일자, 자동 생성

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MatchingState state = MatchingState.BEFORE; // 진행 여부

    @Column(nullable = false)
    private boolean visibility = false; // 종료 후 배팅 금액 공개 여부

    @Column
    private String password; // 비밀번호 (수정/참여자 삭제용)

}
