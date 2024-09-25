package khu.dino.member.business;

import khu.dino.common.annotation.Mapper;
import khu.dino.member.persistence.Member;
import khu.dino.member.presentation.dto.MemberResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Mapper
@Slf4j
@RequiredArgsConstructor
public class MemberMapper {


    public MemberResponseDto.MemberResponse toMemberResponse(Member member) {
        return MemberResponseDto.MemberResponse.builder()
                .id(member.getId())
                .socialId(member.getSocialId())
                .nickname(member.getNickname())
                .userRole(member.getUserRole())
                .oAuth2Provider(member.getOAuth2Provider())
                .socialId(member.getSocialId())
                .memberStatus(member.getMemberStatus())
                .build();
    }
    public static MemberResponseDto.saveFcmToken toSaveFcmToken(Member member, boolean isSuccess) {
        return MemberResponseDto.saveFcmToken.builder()
                .memberId(member.getId())
                .isSaveFcmToken(isSuccess)
                .build();
    }
}
