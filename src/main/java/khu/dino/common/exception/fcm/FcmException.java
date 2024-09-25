package khu.dino.common.exception.fcm;

import com.nimbusds.oauth2.sdk.GeneralException;
import org.springframework.security.core.AuthenticationException;

public class FcmException extends RuntimeException {

    public FcmException(String msg) {
        super(msg);
    }
}
