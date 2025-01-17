package khu.dino.common.auth;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("dev")
@Component
public class RedirectFromDev implements RedirectFromProfile{
    @Override
    public String getRedirectUrl() {
//        return "https://www.khu-dino.n-e.kr";
        return "https://dino-fe.vercel.app";
//        return "http://localhost:3000";
    }
}
