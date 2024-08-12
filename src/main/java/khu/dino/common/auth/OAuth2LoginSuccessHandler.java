package khu.dino.common.auth;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import khu.dino.common.auth.info.CustomOAuth2User;
import khu.dino.member.persistence.Member;
import khu.dino.member.persistence.enums.UserRole;
import khu.dino.member.persistence.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtProviderService jwtProviderService;
    private final MemberRepository memberRepository;
    private final RedirectFromProfile redirectFromProfile;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("소셜 로그인 성공");
        String username = extractUsername(authentication);
        String accessToken = jwtProviderService.generateAccessToken(username, UserRole.ROLE_USER.name());


        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json;charset=UTF-8");

        Member member = memberRepository.findBySocialId(username).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("id", String.valueOf(member.getId()));
        queryParams.add("access-token", accessToken);

        String enNickname = URLEncoder.encode(member.getNickname(), StandardCharsets.UTF_8);
        queryParams.add("nickname", enNickname);
        log.info(enNickname);
        String deNicname = URLDecoder.decode(enNickname, StandardCharsets.UTF_8);
        log.info(deNicname);
        response.sendRedirect(redirectFromProfile.getRedirectUrl() + "/#/oauth2/login?id=" + member.getId() + "&access-code=" + accessToken + "&nickname=" + enNickname);   // sendRedirect() 메서드를 이용해 Frontend 애플리케이션 쪽으로 리다이렉트
    }


    private String extractUsername(Authentication authentication) {
        log.info(authentication.toString());
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        log.info(oAuth2User.getUsername());
        return oAuth2User.getUsername();
    }


}
