package ces.betyourrole.DTO;

import ces.betyourrole.domain.Member;
import ces.betyourrole.exception.AuthException;
import lombok.Builder;

import java.util.Map;

import static ces.betyourrole.exception.ErrorCode.ILLEGAL_REGISTRATION_ID;

@Builder
public record OAuth2UserInfo (
        String name,
        String email
) {
    public static OAuth2UserInfo of(String registrationId, Map<String, Object> attributes) {
        return switch (registrationId) { // registration id별로 userInfo 생성
            case "google" -> ofGoogle(attributes);
            default -> throw new AuthException(ILLEGAL_REGISTRATION_ID);
        };
    }

    private static OAuth2UserInfo ofGoogle(Map<String, Object> attributes) {
        return OAuth2UserInfo.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .build();
    }

//    public Member toEntity() {
//        return Member.builder()
//                .name(name)
//                .email(email)
//                .memberKey(KeyGenerator.generateKey())
//                .build();
//    }
}
