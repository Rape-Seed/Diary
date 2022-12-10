package com.example.diary.global.auth.handler;

import static com.example.diary.global.auth.repository.OAuth2AuthorizationRequestCookieRepository.ACCESS_TOKEN;
import static com.example.diary.global.auth.repository.OAuth2AuthorizationRequestCookieRepository.REDIRECT_URI_PARAM_COOKIE_NAME;
import static com.example.diary.global.auth.repository.OAuth2AuthorizationRequestCookieRepository.REFRESH_TOKEN;

import com.example.diary.domain.member.entity.LoginResponseDto;
import com.example.diary.domain.member.entity.PlatformType;
import com.example.diary.domain.member.entity.Role;
import com.example.diary.global.auth.entity.CustomUserDetails;
import com.example.diary.global.auth.info.OAuth2UserInfo;
import com.example.diary.global.auth.info.OAuth2UserInfoFactory;
import com.example.diary.global.auth.repository.OAuth2AuthorizationRequestCookieRepository;
import com.example.diary.global.auth.token.AuthToken;
import com.example.diary.global.auth.token.AuthTokenProvider;
import com.example.diary.global.common.dto.ResponseDto;
import com.example.diary.global.properties.AuthProperties;
import com.example.diary.global.properties.OAuth2Properties;
import com.example.diary.global.redis.RedisService;
import com.example.diary.global.utils.CookieUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final ObjectMapper objectMapper;
    private final AuthTokenProvider tokenProvider;
    private final AuthProperties authProperties;
    private final OAuth2Properties oAuth2Properties;
    private final RedisService redisService;
    private final OAuth2AuthorizationRequestCookieRepository oAuthRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) authentication;
        PlatformType platformType = PlatformType.valueOf(authToken.getAuthorizedClientRegistrationId().toUpperCase());
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(platformType,
                principal.getAttributes());

        String accessToken = createAccessToken(oAuth2UserInfo);
        String refreshToken = createRefreshToken(oAuth2UserInfo, principal);

        addAccessTokenByCookie(request, response, accessToken);
        addRefreshTokenByCookie(request, response, refreshToken);

        makeResponse(response, new LoginResponseDto(principal.getUsername(), accessToken, refreshToken));

        String targetUrl = determineTargetUrl(request, response, authentication);

        if (response.isCommitted()) {
            log.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    @Override
    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {
        //TODO 로그인 하기 이전 페이지를 기억해서 이전 페이지로 리다이렉션 해야함
        //TODO 최초로그인 시 생년월일 확인하는 페이지로 리다이렉션
        Optional<String> redirectUri = CookieUtil.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);

        if (redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
            throw new IllegalArgumentException("승인되지 않은 Redirect URL가 존재합니다.");
        }

        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

        return UriComponentsBuilder.fromUriString(targetUrl)
                .build().toUriString();
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        oAuthRepository.removeAuthorizationRequestCookies(request, response);
    }

    private void addRefreshTokenByCookie(HttpServletRequest request, HttpServletResponse response,
                                         String refreshToken) {
        int cookieRefreshMaxAge = (int) (new Date().getTime() + authProperties.getRefreshTokenExpiry());
        CookieUtil.deleteCookie(request, response, REFRESH_TOKEN);
        CookieUtil.addCookie(response, REFRESH_TOKEN, refreshToken, cookieRefreshMaxAge, true);
    }

    private void addAccessTokenByCookie(HttpServletRequest request, HttpServletResponse response, String accessToken) {
        int cookieAccessMaxAge = (int) (new Date().getTime() + authProperties.getAccessTokenExpiry());
        CookieUtil.deleteCookie(request, response, ACCESS_TOKEN);
        CookieUtil.addCookie(response, ACCESS_TOKEN, accessToken, cookieAccessMaxAge, false);
    }

    private void makeResponse(HttpServletResponse response, LoginResponseDto loginResponseDto)
            throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        response.getWriter().write(objectMapper.writeValueAsString(
                new ResponseDto<>(loginResponseDto, "Login Success", HttpStatus.OK)
        ));
    }

    private boolean isAuthorizedRedirectUri(String uri) {
        URI clientRedirectUri = URI.create(uri);

        return oAuth2Properties.getAuthorizedRedirectUris()
                .stream()
                .anyMatch(authorizedRedirectUri -> {
                    // Only validate host and port. Let the clients use different paths if they want to
                    URI authorizedURI = URI.create(authorizedRedirectUri);
                    return authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
                            && authorizedURI.getPort() == clientRedirectUri.getPort();
                });
    }

    private String createAccessToken(OAuth2UserInfo oAuth2UserInfo) {
        AuthToken accessToken = tokenProvider.createAuthToken(
                oAuth2UserInfo.getEmail(),
                Role.MEMBER.name(),
                new Date(new Date().getTime() + authProperties.getAccessTokenExpiry()));
        return accessToken.getToken();
    }

    private String createRefreshToken(OAuth2UserInfo oAuth2UserInfo, CustomUserDetails principal) {
        long refreshTokenExpiry = new Date().getTime() + authProperties.getRefreshTokenExpiry();
        AuthToken refreshToken = tokenProvider.createAuthToken(
                oAuth2UserInfo.getEmail(),
                new Date(refreshTokenExpiry));

        redisService.setDataWithExpiration(principal.getUsername(), refreshToken.getToken(), refreshTokenExpiry);
        return refreshToken.getToken();
    }

}
