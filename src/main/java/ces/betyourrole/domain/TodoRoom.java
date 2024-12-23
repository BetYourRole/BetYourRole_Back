package ces.betyourrole.domain;

import ces.betyourrole.exception.InvalidRangeException;
import ces.betyourrole.exception.RequiredFieldMissingException;
import ces.betyourrole.util.RandomKeyGenerator;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "todo_room")
public class TodoRoom {

    private static final int maxHeadCount = 10;
    private static final int minPoint = 10;
    private static final int maxPoint = 1000;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "todo_room_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_active_session", nullable = true)
    private Member activeSession; // 로그인하지 않은 경우 null

    @Column(nullable = false, name = "todo_room_name")
    private String name; // 방 이름

    @Column(name = "todo_room_description")
    private String description; // 방 설명

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MatchingType matchingType; // 낙찰 알고리즘

    @Column(nullable = false)
    private Integer point = 100; // 인당 배팅 가능 포인트 (기본값 100)

    @Column(nullable = false, updatable = false)
    private LocalDateTime createDate; // 생성일자, 자동 생성

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Getter
    private MatchingState state; // 진행 여부

    @Column(nullable = false)
    private Boolean visibility = false; // 종료 후 배팅 금액 공개 여부

    @Column
    private String password; // 비밀번호 (수정/참여자 삭제용)

    @Column(unique = true, nullable = false)
    private String randomKey;

    @PrePersist
    public void generateRandomKey() {
        this.randomKey = RandomKeyGenerator.generateRandomKey();
    }

    private TodoRoom(String name, String description, Integer headCount, MatchingType matchingType, Integer point, Boolean visibility) {

        if(name == null || name.isEmpty()){
            throw new RequiredFieldMissingException("방 이름은 필수입력입니다.");
        }
        this.name = name;
        this.description = description;

        if(headCount < 2 || headCount > maxHeadCount){
            throw new InvalidRangeException(MessageFormat.format("참여인원은 2인 이상 {0}인 이하만 가능합니다.", maxHeadCount));
        }
        this.matchingType = matchingType;

        if(point<minPoint || point>maxPoint){
            throw new InvalidRangeException(MessageFormat.format("포인트는 {0}~{1} 범위 내만 가능합니다.", minPoint, maxPoint));
        }
        this.point = point;

        this.createDate = LocalDateTime.now();
        this.state = MatchingState.BEFORE;
        this.visibility = visibility;

    }

    public TodoRoom(Member activeSession, String name, String description, Integer headCount, MatchingType matchingType, Integer point, Boolean visibility) {
        this(name,description,headCount,matchingType,point,visibility);

        this.activeSession = activeSession;
    }

    public TodoRoom(String password, String name, String description, Integer headCount, MatchingType matchingType, Integer point, Boolean visibility) {
        this(name,description,headCount,matchingType,point,visibility);

        //암호화로직 추가
        this.password = password;
    }

    public boolean isPasswordCorrect(String input){
        //암호화 로직 적용 필요
        return Objects.equals(password, input);
    }

    public void completeRoom(){
        this.state = MatchingState.COMPLETED;
    }

    public boolean checkSession(Member member){
        if(this.activeSession == null) return false;
        return this.activeSession.equals(member);
    }

    public String getRoomOwnerEmail() {
        if(this.activeSession == null){
            return "비회원";
        }
        return this.activeSession.getEmail();
    }
}
