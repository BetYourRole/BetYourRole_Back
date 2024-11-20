package ces.betyourrole.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String email;
    private String name;
    private LocalDateTime createDate;
    private LocalDateTime lastLogin;

    @Enumerated(EnumType.STRING)
    private MemberState memberState;

    public Member(String email, String name) {
        this.email = email;
        this.name = name;
        this.createDate = LocalDateTime.now();
        this.lastLogin = LocalDateTime.now();
        this.memberState = MemberState.ACTIVE;
    }

    public void updateLastLogin() {
        this.lastLogin = LocalDateTime.now();
    }

    public void deactivate() {
        this.memberState = MemberState.DEACTIVATED;
    }
}
