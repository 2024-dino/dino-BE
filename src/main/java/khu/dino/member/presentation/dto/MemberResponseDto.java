package khu.dino.member.presentation.dto;

import khu.dino.member.persistence.enums.MemberStatus;
import khu.dino.member.persistence.enums.OAuth2Provider;
import khu.dino.member.persistence.enums.UserRole;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberResponseDto {
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    public static class MemberResponse {
        private Long id;
        private String socialId;
        private OAuth2Provider oAuth2Provider;
        private MemberStatus memberStatus;
        private UserRole userRole;
        private String nickname;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class saveFcmToken {
        private Long memberId;
        private boolean isSaveFcmToken;
    }
}
