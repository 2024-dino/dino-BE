package khu.dino.common.auth;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import khu.dino.member.business.MemberService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;


@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    private final JwtProviderService jwtProviderService;

    private final MemberService memberService;



    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        final Optional<String> jwt = jwtProviderService.extractAccessToken(request);


        if (jwt.isPresent() && SecurityContextHolder.getContext().getAuthentication() == null && jwtProviderService.validateToken(jwt.get())) {
            //토큰이 유효할 떄 아래 로직을 수행한다.
            String OAuth2Id = jwtProviderService.extractOAuth2Id(jwt.get());
            UserDetails userDetails = memberService.loadUserByUsername(OAuth2Id);
            Authentication authentication = jwtProviderService.getAuthentication(userDetails);

            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            securityContext.setAuthentication(authentication);
            SecurityContextHolder.setContext(securityContext);

        }

        filterChain.doFilter(request, response);

    }
}
