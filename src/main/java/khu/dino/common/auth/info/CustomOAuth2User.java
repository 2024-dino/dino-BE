package khu.dino.common.auth.info;

import khu.dino.member.persistence.enums.UserRole;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.Collection;
import java.util.Map;

@Getter
public class CustomOAuth2User extends DefaultOAuth2User {
    private final UserRole role;

    private final String username;

    public CustomOAuth2User(Collection<? extends GrantedAuthority> authorities, Map<String, Object> attributes, String nameAttributeKey, String username, UserRole role) {
        super(authorities, attributes, nameAttributeKey);
        this.role = role;
        this.username = username;

    }
}
