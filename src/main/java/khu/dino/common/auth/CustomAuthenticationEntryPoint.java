package khu.dino.common.auth;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import khu.dino.common.exception.ErrCode;
import khu.dino.common.exception.ErrResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    //인증안된 사용자가 접근하려고 할 떄.

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException  {

        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json;charset=UTF-8");
        ErrResponse errResponse = ErrResponse.builder()
                .code(ErrCode.UNAUTHORIZED.getCode())
                .message(ErrCode.UNAUTHORIZED.getMessage())
                .status(ErrCode.UNAUTHORIZED.getStatus()).build();

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(objectMapper.writeValueAsString(errResponse));
        response.getWriter().flush();
        response.getWriter().close();
    }

}
