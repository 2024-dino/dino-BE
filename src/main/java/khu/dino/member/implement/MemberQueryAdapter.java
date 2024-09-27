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

    public Member findById(Long id){
        return memberRepository.findById(id).get();
    }

    public PrincipalDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findBySocialId(username).orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
        return  PrincipalDetails.builder()
                .member(member)
                .build();


    }

}
