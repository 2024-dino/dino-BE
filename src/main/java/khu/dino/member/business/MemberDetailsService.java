package khu.dino.member.business;



import khu.dino.common.auth.PrincipalDetails;
import khu.dino.member.persistence.Member;
import khu.dino.member.persistence.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
        log.info("사용자 이메일 정보: " + member.getUsername());
        log.info("계정 제공업체 정보: " + member.getOAuth2Provider());
        return PrincipalDetails.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .role(member.getRole())
                .oAuth2Provider(member.getOAuth2Provider())
                .username(member.getUsername()) //사용자를 구별하는 고유한 값(OAUTH 벤더 식별자)
                .build();
    }
}
