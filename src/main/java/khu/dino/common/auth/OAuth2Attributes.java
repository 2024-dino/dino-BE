package khu.dino.common.auth;


import khu.dino.common.auth.info.GoogleUserInformation;
import khu.dino.common.auth.info.KakaoUserInformation;
import khu.dino.common.auth.info.Oauth2UserInformation;
import khu.dino.member.persistence.enums.MemberStatus;
import khu.dino.member.persistence.enums.OAuth2Provider;
import khu.dino.member.persistence.Member;
import khu.dino.member.persistence.enums.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;


@Getter
@Slf4j
public class OAuth2Attributes {

    private String nameAttributeKey;
    private Oauth2UserInformation oauth2UserInfo;

    @Builder
    public OAuth2Attributes(String nameAttributeKey, Oauth2UserInformation oauth2UserInfo) {
        this.nameAttributeKey = nameAttributeKey;
        this.oauth2UserInfo = oauth2UserInfo;
    }


    public static OAuth2Attributes of(OAuth2Provider socialType,
                                      String userNameAttributeName, Map<String ,Object> attributes) {

        if (socialType == OAuth2Provider.KAKAO) {
            log.info("카카오 로그인임");
            return ofKakao(userNameAttributeName, attributes);
        }else {
            log.info("구글 로그인임");
            return ofGoogle(userNameAttributeName, attributes);
        }
    }

    private static OAuth2Attributes ofKakao(String userNameAttributeName, Map<String ,Object> attributes) {
        return OAuth2Attributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oauth2UserInfo(new KakaoUserInformation(attributes))
                .build();
    }

    public static OAuth2Attributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuth2Attributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oauth2UserInfo(new GoogleUserInformation(attributes))
                .build();
    }


    public Member toEntity(OAuth2Provider oAuth2Provider, Oauth2UserInformation oauth2UserInfo) {
        return Member.builder()
                .oAuth2Provider(oAuth2Provider)
                .memberStatus(MemberStatus.ACTIVE)
                .socialId(oauth2UserInfo.getIdentifierKey())
                .nickname(oauth2UserInfo.getNickname())
                .userRole(UserRole.ROLE_USER)
                .build();
    }
}
