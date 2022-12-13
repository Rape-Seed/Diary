package com.example.diary.global.utils;

import static com.example.diary.global.auth.repository.OAuth2AuthorizationRequestCookieRepository.ACCESS_TOKEN;
import static com.example.diary.global.auth.repository.OAuth2AuthorizationRequestCookieRepository.REFRESH_TOKEN;

import com.example.diary.global.properties.AuthProperties;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.util.SerializationUtils;

public class CookieUtil {

    public static Optional<Cookie> getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    return Optional.of(cookie);
                }
            }
        }
        return Optional.empty();
    }

    public static String getToken(HttpServletRequest request, String tokenType) {
        Optional<Cookie> cookie = getCookie(request, tokenType);
        if (cookie.isEmpty()) {
            return null;
        }
        return cookie.get().getValue();
    }

    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge,
                                 boolean httpOnly) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setHttpOnly(httpOnly);
        cookie.setMaxAge(maxAge);

        response.addCookie(cookie);
    }

    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    cookie.setValue("");
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }
    }

    public static String serialize(Object obj) {
        return Base64.getUrlEncoder()
                .encodeToString(SerializationUtils.serialize(obj));
    }

    public static <T> T deserialize(Cookie cookie, Class<T> cls) {
        return cls.cast(
                SerializationUtils.deserialize(
                        Base64.getUrlDecoder().decode(cookie.getValue())
                )
        );
    }

    public static void addRefreshTokenByCookie(HttpServletRequest request, HttpServletResponse response,
                                               String refreshToken, AuthProperties authProperties) {
        int cookieRefreshMaxAge = (int) (new Date().getTime() + authProperties.getRefreshTokenExpiry());
        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
        CookieUtil.addCookie(response, REFRESH_TOKEN, refreshToken, cookieRefreshMaxAge, true);
    }

    public static void addAccessTokenByCookie(HttpServletRequest request, HttpServletResponse response,
                                              String accessToken, AuthProperties authProperties) {
        int cookieAccessMaxAge = (int) (new Date().getTime() + authProperties.getAccessTokenExpiry());
        CookieUtil.deleteCookie(request, response, ACCESS_TOKEN);
        CookieUtil.addCookie(response, ACCESS_TOKEN, accessToken, cookieAccessMaxAge, false);
    }
}
