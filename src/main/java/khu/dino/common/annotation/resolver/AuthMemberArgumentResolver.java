package khu.dino.common.annotation.resolver;

import jakarta.servlet.http.HttpServletRequest;
import khu.dino.common.annotation.AuthMember;
import khu.dino.common.auth.JwtProviderService;
import khu.dino.common.auth.PrincipalDetails;
import khu.dino.common.exception.auth.JwtAuthenticationException;
import khu.dino.member.business.MemberService;
import khu.dino.member.persistence.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


@Component
@Slf4j
@RequiredArgsConstructor
public class AuthMemberArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        AuthMember authMember = parameter.getParameterAnnotation(AuthMember.class);
        if (authMember == null) return false;
        return parameter.getParameterType().equals(PrincipalDetails.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof PrincipalDetails) {
            return authentication.getPrincipal();
        } else{
            throw new JwtAuthenticationException("JWT가 유효하지 않습니다.");
        }

    }
}
