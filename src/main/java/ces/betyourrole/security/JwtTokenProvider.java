package ces.betyourrole.security;

import ces.betyourrole.exception.CustomException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static ces.betyourrole.exception.ErrorCode.INVALID_TOKEN;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final RedisTemplate<String, String> redisTemplate;

    private String SECRET_KEY = "ThisIsMyVeryVeryVerySecretTestKeyasdfhqewulthaekjskfjhwajkfasbdkjbfsdamnfbwakhqrgfasdfsabdfkjhwhlasiuhdfihwlaiudhslufhkljahwliuahsldkjfhlwiuahesjdknvzxmncvkjahglifuhawfkljshlfkhwlifuhxcvnalsdufhliawusjdhvkjnldfhua";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 24 * 60 * 60 * 1000L;
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 7 * 24 * 60 * 60 * 1000L;

    public String createAccessToken(String email) {
        return createToken(email, ACCESS_TOKEN_EXPIRE_TIME);
    }

    public String createRefreshToken(String email) {
        String refreshToken = createToken(email, REFRESH_TOKEN_EXPIRE_TIME);
        redisTemplate.opsForValue().set("refreshToken:" + email, refreshToken, REFRESH_TOKEN_EXPIRE_TIME, TimeUnit.MILLISECONDS);
        return refreshToken;
    }

    // JWT 생성
    public String createToken(String email, long expireTime) {
        Claims claims = Jwts.claims().setSubject(email);
        Date now = new Date();
        Date validity = new Date(now.getTime() + expireTime);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes(StandardCharsets.UTF_8))
                .compact();
    }

    // JWT 토큰에서 이메일 가져오기
    public String getEmailFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // JWT 유효성 확인
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // 토큰 파싱
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // Refresh Token을 이용한 Access Token 재발급
    public ResponseEntity<String> refreshAccessToken(String refreshToken) {
        // Refresh Token 검증
        if (!validateToken(refreshToken)) {
            throw new CustomException(INVALID_TOKEN);
        }

        // Refresh Token이 유효하다면 사용자가 누구인지 확인
        String email = getEmailFromToken(refreshToken);

        // Redis에서 현재 Refresh Token을 조회
        String storedRefreshToken = redisTemplate.opsForValue().get("refreshToken:" + email);
        if (storedRefreshToken == null || !storedRefreshToken.equals(refreshToken)) {
            throw new CustomException(INVALID_TOKEN);
        }

        // 새로운 Access Token 생성
        String newAccessToken = createAccessToken(email);

        // 기존 Refresh Token 만료 시 새로운 Refresh Token 생성 및 저장 (Optional, Refresh Token Rotation 적용 가능)
        String newRefreshToken = createRefreshToken(email);

        return ResponseEntity.ok(newAccessToken);
    }

    public long getRefreshTokenValidity() {
        return REFRESH_TOKEN_EXPIRE_TIME;
    }

//    // 토큰 생성 및 쿠키 설정
//    public String createTokensAndSetCookie(String email) {
//        String accessToken = createAccessToken(email);
//        String refreshToken = createRefreshToken(email);
//
//        // 리프레시 토큰 레디스에 저장 (유효기간 설정)
//        redisTemplate.opsForValue().set("refreshToken:" + email, refreshToken, REFRESH_TOKEN_EXPIRE_TIME, TimeUnit.MILLISECONDS);
//
//        // 리프레시 토큰 http only 쿠키로 설정
//        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", refreshToken)
//                .httpOnly(true)
//                .secure(true)
//                .path("/")
//                .maxAge(REFRESH_TOKEN_EXPIRE_TIME / 1000)
//                .build();
//
//        return accessToken;
//    }
}
