package ces.betyourrole.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        if (requestURI.startsWith("/")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = jwtTokenProvider.resolveToken(request);

        if (jwtTokenProvider.validateToken(token)) {
            String email = jwtTokenProvider.getEmailFromToken(token);
            SecurityContextHolder.getContext().setAuthentication(new JwtAuthenticationToken(email));
        } else {
            String refreshToken = resolveRefreshToken(request);

            if (jwtTokenProvider.validateRefreshToken(refreshToken)) {
                String newAccessToken = jwtTokenProvider.reissueAccessToken(refreshToken);
                String newRefreshToken = jwtTokenProvider.reissueRefreshToken(refreshToken);

                response.setHeader("Authorization", "Bearer " + newAccessToken);

                Cookie refreshTokenCookie = new Cookie("RefreshToken", newRefreshToken);
                refreshTokenCookie.setHttpOnly(true);
//                refreshTokenCookie.setSecure(true);
                refreshTokenCookie.setPath("/");
                refreshTokenCookie.setMaxAge((int) jwtTokenProvider.getRefreshTokenValidity() / 1000);

                response.addCookie(refreshTokenCookie);

                // 새로 발급된 토큰으로 인증 객체 생성
                String email = jwtTokenProvider.getEmailFromToken(newAccessToken);
                SecurityContextHolder.getContext().setAuthentication(new JwtAuthenticationToken(email));
            }
        }

        filterChain.doFilter(request, response);
    }

    private String resolveRefreshToken(HttpServletRequest request) {
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
