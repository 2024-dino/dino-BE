package khu.dino.common.auth;

import khu.dino.member.persistence.enums.OAuth2Provider;
import khu.dino.member.persistence.enums.UserRole;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
@EqualsAndHashCode
public class PrincipalDetails implements UserDetails {

    private Long id;
    private String nickname; //유저 이름
    private String socialId; //OAuth2 고유 식별자
    private OAuth2Provider oAuth2Provider; //OAuth2 제공자
    @Builder.Default
    private UserRole userRole = UserRole.ROLE_USER; //ROLE, 기본은 USER

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(this.userRole.name()));
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.socialId;
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
