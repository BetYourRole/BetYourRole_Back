package ces.betyourrole.controller;

import ces.betyourrole.exception.CustomException;
import ces.betyourrole.security.JwtTokenProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/login")
public class LoginController {
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/reissue")
    public ResponseEntity<String> reissueToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = jwtTokenProvider.resolveRefreshToken(request);

        if (jwtTokenProvider.validateRefreshToken(token)) {
            String newAccessToken = jwtTokenProvider.reissueAccessToken(token);
            String newRefreshToken = jwtTokenProvider.reissueRefreshToken(token);

            Cookie refreshTokenCookie = new Cookie("RefreshToken", newRefreshToken);
            refreshTokenCookie.setHttpOnly(true);
            refreshTokenCookie.setPath("/");
            refreshTokenCookie.setMaxAge((int) jwtTokenProvider.getRefreshTokenValidity() / 1000);

            response.addCookie(refreshTokenCookie);

            return new ResponseEntity<>(newAccessToken, HttpStatus.OK);
        }
        throw new CustomException("님 토큰죽음", HttpStatus.UNAUTHORIZED);
    }
}
