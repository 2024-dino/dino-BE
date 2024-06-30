package khu.dino.auth;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("dev")
@Component
public class RedirectFromDev implements RedirectFromProfile{
    @Override
    public String getRedirectUrl() {
        return "https://www.khu-dino.n-e.kr";
    }
}
