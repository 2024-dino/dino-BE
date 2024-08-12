package khu.dino.member.implement;

import khu.dino.common.annotation.Adapter;
import khu.dino.common.auth.PrincipalDetails;
import khu.dino.member.persistence.Member;
import khu.dino.member.persistence.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Adapter
@RequiredArgsConstructor
public class MemberQueryAdapter {
    private final MemberRepository memberRepository;


    public PrincipalDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findBySocialId(username).orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
        return PrincipalDetails.builder()
                .id(member.getId())
                .nickname(member.getNickname())
                .userRole(member.getUserRole())
                .oAuth2Provider(member.getOAuth2Provider())
                .socialId(member.getSocialId()) //사용자를 구별하는 고유한 값(OAUTH 벤더 식별자)
                .build();
    }


    public Member validateJwt(PrincipalDetails principalDetails) {
        return memberRepository.findById(principalDetails.getId()).orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

    }
}
