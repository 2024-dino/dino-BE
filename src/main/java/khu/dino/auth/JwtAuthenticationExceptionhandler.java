package khu.dino.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import khu.dino.exception.ErrCode;
import khu.dino.exception.ErrResponse;
import khu.dino.exception.auth.JwtAuthenticationException;
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationExceptionhandler extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request,response);
        } catch (JwtAuthenticationException e) {
            response.setContentType("application/json; charset=UTF-8");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());

            ObjectMapper objectMapper  = new ObjectMapper();

            ErrResponse errResponse = ErrResponse.builder()
                    .code(ErrCode.INVALID_ACCESS_TOKEN.getCode())
                    .message(ErrCode.INVALID_ACCESS_TOKEN.getMessage())
                    .status(ErrCode.INTERNAL_SERVER_ERROR.getStatus())
                    .build();


            response.getWriter().write(objectMapper.writeValueAsString(errResponse));
            response.getWriter().flush();
            response.getWriter().close();
        }
    }
}
