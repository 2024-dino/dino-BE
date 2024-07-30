package khu.dino.common.auth;

import khu.dino.domain.OAuth2Provider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class PrincipalDetails implements UserDetails {

    private Long id;
    private String nickname; //유저 이름
    private String username; //OAuth2 고유 식별자
    private OAuth2Provider oAuth2Provider; //OAuth2 제공자
    @Builder.Default
    private String role = "ROLE_USER"; //ROLE, 기본은 USER

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(this.role));
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.username;
    } //사용자의 고유 OAUTH 식별자

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
