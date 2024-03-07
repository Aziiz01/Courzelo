package tn.esprit.user.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class CookieUtil {
    public ResponseCookie createAccessTokenCookie(String accessToken, Long duration) {
        return ResponseCookie.from("accessToken", accessToken)
                .maxAge(duration / 1000000)
                .httpOnly(true)
                .path("/")
                .build();
    }

    public ResponseCookie createRefreshTokenCookie(String refreshToken, Long duration) {
        return ResponseCookie.from("refreshToken", refreshToken)
                .maxAge(duration / 1000000)
                .httpOnly(true)
                .path("/")
                .build();
    }

    public String getAccessTokenFromCookies(HttpServletRequest request) {
        log.info("getAccessTokenFromCookies : Getting token...");
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("accessToken")) {
                    log.info("getAccessTokenFromCookies : Cookie found : " + cookie.getValue());
                    return cookie.getValue();
                }
            }
            log.error("getAccessTokenFromCookies : for is null");
            return null;
        }
        log.error("getAccessTokenFromCookies : request.getCookies() is null");
        return null;
    }

    public String getRefreshTokenFromCookies(HttpServletRequest request) {
        log.info("getRefreshTokenFromCookies : Getting token...");
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("refreshToken")) {
                    log.info("getRefreshTokenFromCookies : Cookie found : " + cookie.getValue());
                    return cookie.getValue();
                }
            }
        }
        log.error("getRefreshTokenFromCookies : request.getCookies() is null");
        return null;
    }

}