package com.example.diary.global.config;

import com.example.diary.domain.member.entity.Role;
import com.example.diary.global.auth.exception.CustomAuthenticationEntryPoint;
import com.example.diary.global.auth.filter.JwtAuthenticationFilter;
import com.example.diary.global.auth.handler.CustomAccessDeniedHandler;
import com.example.diary.global.auth.handler.OAuth2AuthenticationFailureHandler;
import com.example.diary.global.auth.handler.OAuth2AuthenticationSuccessHandler;
import com.example.diary.global.auth.repository.OAuth2AuthorizationRequestCookieRepository;
import com.example.diary.global.auth.service.CustomOAuth2UserService;
import com.example.diary.global.auth.token.AuthTokenProvider;
import com.example.diary.global.properties.AuthProperties;
import com.example.diary.global.properties.CorsProperties;
import com.example.diary.global.properties.OAuth2Properties;
import com.example.diary.global.redis.RedisService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Component
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CorsProperties corsProperties;
    private final AuthProperties authProperties;
    private final OAuth2Properties oAuth2Properties;

    private final ObjectMapper objectMapper;

    private final RedisService redisService;
    private final CustomOAuth2UserService oAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http

                .cors()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .accessDeniedHandler(new CustomAccessDeniedHandler())
                .and()
                .authorizeRequests(auth -> auth
                        .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                        .antMatchers("/", "/hello", "/login").permitAll()
                        .antMatchers("/h2-console/**").permitAll()
                        .antMatchers("/api/v1/sign/**").permitAll()
                        .antMatchers("/api/v1/emotion/**").permitAll()
                        .antMatchers("/api/v1/**").hasRole(Role.MEMBER.name())
                        .anyRequest().authenticated())
                .oauth2Login()
                .authorizationEndpoint()
                .baseUri("/oauth2/authorization")
                .authorizationRequestRepository(oAuth2AuthorizationRequestBasedOnCookieRepository())
                .and()
                .redirectionEndpoint()
                .baseUri("/*/oauth2/code/*")
                .and()
                .userInfoEndpoint()
                .userService(oAuth2UserService)
                .and()
                .successHandler(oAuth2AuthenticationSuccessHandler())
                .failureHandler(oAuth2AuthenticationFailureHandler());

        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .antMatchers("/h2-console/**", "/css/**", "/image/**", "/js/**", "/fonts/**", "/favicon.ico");
    }

    /**
     * Auth Manger 설정
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthTokenProvider tokenProvider() {
        return new AuthTokenProvider(authProperties.getSecretKey());
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 토큰 필터 설정
     */
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(tokenProvider(), redisService);
    }

    /**
     * 쿠키 기반 인가 Repository / 인가 응답을 연계 하고 검증할 때 사용
     */
    @Bean
    public OAuth2AuthorizationRequestCookieRepository oAuth2AuthorizationRequestBasedOnCookieRepository() {
        return new OAuth2AuthorizationRequestCookieRepository();
    }

    @Bean
    public OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler() {
        return new OAuth2AuthenticationSuccessHandler(
                objectMapper,
                tokenProvider(),
                authProperties,
                oAuth2Properties,
                redisService,
                oAuth2AuthorizationRequestBasedOnCookieRepository()
        );
    }

    @Bean
    public OAuth2AuthenticationFailureHandler oAuth2AuthenticationFailureHandler() {
        return new OAuth2AuthenticationFailureHandler(oAuth2AuthorizationRequestBasedOnCookieRepository());
    }

    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedHeaders(List.of(corsProperties.getAllowedHeaders().split(",")));
        corsConfiguration.setAllowedMethods(List.of(corsProperties.getAllowedMethods().split(",")));
        corsConfiguration.setAllowedOrigins(List.of(corsProperties.getAllowedOrigins().split(",")));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setMaxAge(corsProperties.getMaxAge());

        corsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return corsConfigurationSource;
    }


}
