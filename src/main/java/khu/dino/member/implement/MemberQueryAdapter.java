package khu.dino.member.implement;

import khu.dino.common.annotation.Adapter;
import khu.dino.common.auth.PrincipalDetails;
import khu.dino.common.exception.ErrCode;
import khu.dino.common.exception.event.EventException;
import khu.dino.common.exception.member.MemberException;
import khu.dino.member.persistence.Member;
import khu.dino.member.persistence.repository.MemberRepository;
import khu.dino.member.presentation.dto.MemberRequestDto;
import khu.dino.member.presentation.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Adapter
@RequiredArgsConstructor
public class MemberQueryAdapter {
    private final MemberRepository memberRepository;

    public Member findById(Long id){
        return memberRepository.findById(id).orElseThrow(() -> new MemberException(ErrCode.EVENT_NOT_FOUND.getMessage()));
    }

    public PrincipalDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findBySocialId(username).orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
        return  PrincipalDetails.builder()
                .member(member)
                .build();


    }

}
