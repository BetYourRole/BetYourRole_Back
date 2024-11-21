package ces.betyourrole.security;

import ces.betyourrole.exception.CustomException;
import ces.betyourrole.exception.InvalidTokenException;
import io.jsonwebtoken.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static ces.betyourrole.exception.ErrorCode.INVALID_TOKEN;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final RedisTemplate<String, String> redisTemplate;

    private String SECRET_KEY = "ThisIsMyVeryVeryVerySecretTestKeyasdfhqewulthaekjskfjhwajkfasbdkjbfsdamnfbwakhqrgfasdfsabdfkjhwhlasiuhdfihwlaiudhslufhkljahwliuahsldkjfhlwiuahesjdknvzxmncvkjahglifuhawfkljshlfkhwlifuhxcvnalsdufhliawusjdhvkjnldfhua";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 60 * 1000L;
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 14 * 24 * 60 * 60 * 1000L;

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
                .setSigningKey(SECRET_KEY.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String getEmailFromBearerToken(String bearerToken){
        return getEmailFromToken(resolveToken(bearerToken));
    }

    public String getEmailFromBearerToken(HttpServletRequest request){
        return getEmailFromToken(resolveToken(request));
    }

    // access token 유효성 확인
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY.getBytes(StandardCharsets.UTF_8)).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // refresh token 유효성 확인
    public boolean validateRefreshToken(String refreshToken) {
        // Redis에서 해당 사용자의 Refresh Token을 조회
        String email = getEmailFromToken(refreshToken);
        String storedRefreshToken = redisTemplate.opsForValue().get("refreshToken:" + email);

        if (storedRefreshToken == null) {
            return false;
        }

        return storedRefreshToken.equals(refreshToken);
    }

    // 토큰 파싱
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public String resolveToken(String bearerToken) {
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    // access token 재발급
    public String reissueAccessToken(String refreshToken) {
        if (!validateRefreshToken(refreshToken)) {
            throw new InvalidTokenException();
        }

        String email = getEmailFromToken(refreshToken);

        return createAccessToken(email);
    }

    // access token 재발급
    public String reissueRefreshToken(String refreshToken) {
        if (!validateRefreshToken(refreshToken)) {
            throw new CustomException(INVALID_TOKEN);
        }

        String email = getEmailFromToken(refreshToken);

        return createRefreshToken(email);
    }

    public long getRefreshTokenValidity() {
        return REFRESH_TOKEN_EXPIRE_TIME;
    }

    public String resolveRefreshToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("RefreshToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
