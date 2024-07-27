package khu.dino.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import khu.dino.common.exception.ErrCode;
import khu.dino.common.exception.ErrResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    //권한이 없는 유저가 접근 할 시 처리하는 핸들러

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        ErrResponse errResponse = ErrResponse.builder()
                .code(ErrCode.NO_FORBIDDEN.getCode())
                .message(ErrCode.NO_FORBIDDEN.getMessage())
                .status(ErrCode.NO_FORBIDDEN.getStatus())
                .build();

        response.getWriter().write(objectMapper.writeValueAsString(errResponse));
        response.getWriter().flush();
        response.getWriter().close();
    }

}
