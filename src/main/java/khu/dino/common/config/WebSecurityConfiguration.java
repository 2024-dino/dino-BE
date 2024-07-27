package khu.dino.config;


import khu.dino.auth.*;
import khu.dino.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;


@Configuration
@EnableMethodSecurity
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final JwtAuthenticationExceptionhandler jwtAuthenticationExceptionhandler;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource()))
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .authorizeHttpRequests(
                        req -> req
                                .requestMatchers(PathRequest.toH2Console()).permitAll()
                                .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.OPTIONS, "/**")).permitAll() //일단 다 허용~
                                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                                .anyRequest().permitAll()
                )
                .sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(exceptionHanding -> exceptionHanding.accessDeniedHandler(customAccessDeniedHandler))
                .exceptionHandling(authenticationEntryPoint -> authenticationEntryPoint.authenticationEntryPoint(customAuthenticationEntryPoint))
                .oauth2Login(httpSecurityOAuth2LoginConfigurer -> {
                    httpSecurityOAuth2LoginConfigurer.successHandler(oAuth2LoginSuccessHandler);
                    httpSecurityOAuth2LoginConfigurer.failureHandler(oAuth2LoginFailureHandler);
                    httpSecurityOAuth2LoginConfigurer.userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig.userService(customOAuth2UserService));
                })
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                //UserNamePasswordAuthenticationFilter 앞에 jwtAuthenticationFilter를 추가함으로써 JWT를 먼저 검사하도록 설정
                .addFilterBefore(jwtAuthenticationExceptionhandler, JwtAuthenticationFilter.class);
                //JWT 인증 에러 시 필터체인을 다시 타서 응답 서블릿이 반환되도록 설정
        return http.build();
    }



    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> {
            web.ignoring().requestMatchers(PathRequest.toH2Console());
        };
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        //LOCAL 및 DEV 서버 도메인 허용
        configuration.setAllowedOrigins(List.of("http://localhost:3000", "http://localhost:60000", "https://www.khu-dino.n-e.kr", "https://www.khu-dino.n-e.kr:60000"));
        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedMethod("*");

        configuration.addAllowedHeader("authorization");
        configuration.addAllowedHeader("Content-Type");
        configuration.addExposedHeader("Cache-Control");

        configuration.addExposedHeader("authorization");
        configuration.addExposedHeader("Cache-Control");
        configuration.addExposedHeader("Content-Type");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
