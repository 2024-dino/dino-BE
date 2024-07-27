package khu.dino.member.implement;

import khu.dino.common.annotation.Adapter;
import khu.dino.member.persistence.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Adapter
@RequiredArgsConstructor
public class MemberQueryAdapter {
    private final MemberRepository memberRepository;
}
