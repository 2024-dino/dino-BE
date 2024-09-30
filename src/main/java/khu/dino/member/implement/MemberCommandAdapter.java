package khu.dino.member.implement;

import jakarta.transaction.Transactional;
import khu.dino.common.annotation.Adapter;
import khu.dino.member.persistence.Member;
import khu.dino.member.persistence.repository.MemberRepository;
import khu.dino.member.presentation.dto.MemberRequestDto;
import lombok.RequiredArgsConstructor;

@Adapter
@RequiredArgsConstructor
public class MemberCommandAdapter {
    private final MemberRepository memberRepository;

    @Transactional
    public Member changeNickname(Member member, MemberRequestDto.MemberRequest request) {
            member.ChangeNickname(request.getNickname());
            return memberRepository.save(member);
    }
}
