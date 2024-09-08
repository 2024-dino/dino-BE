package khu.dino.common.auth;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("local")
@Component
public class RedirectFromLocal implements RedirectFromProfile{

    @Override
    public String getRedirectUrl() {
        return "http://localhost:3000";
    }
}
