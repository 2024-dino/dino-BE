package khu.dino.common.exception.auth;

import org.springframework.security.core.AuthenticationException;


//JwtAuthenticationException class
public class JwtAuthenticationException extends AuthenticationException {

        public JwtAuthenticationException(String msg) {
            super(msg);
        }
}
