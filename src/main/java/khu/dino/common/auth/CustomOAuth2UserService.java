package khu.dino.common.auth;



import khu.dino.common.auth.info.CustomOAuth2User;
import khu.dino.member.persistence.OAuth2Provider;
import khu.dino.member.persistence.Member;
import khu.dino.member.persistence.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j

public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final MemberRepository memberRepository;

    private static final String KAKAO = "kakao";

    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        log.info("Oauth2User : " + oAuth2User.toString());

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        log.info("registrationId : " + registrationId);
        OAuth2Provider oAuth2Provider = getSocialType(registrationId);
        log.info("oauth2Provider : " + oAuth2Provider);
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        OAuth2Attributes extractAttributes = OAuth2Attributes.of(oAuth2Provider, userNameAttributeName, attributes);

        Optional<Member> member = memberRepository.findByUsername(extractAttributes.getOauth2UserInfo().getIdentifierKey());
        if(!member.isPresent()){
            log.info("존재하지 않음");

            Member createdMember = memberRepository.save(extractAttributes.toEntity(oAuth2Provider, extractAttributes.getOauth2UserInfo()));
            return new CustomOAuth2User(
                    Collections.singleton(new SimpleGrantedAuthority(createdMember.getRole())),
                    attributes,
                    extractAttributes.getNameAttributeKey(),
                    createdMember.getUsername(),
                    createdMember.getRole()
            );
        }else{
            return new CustomOAuth2User(
                    Collections.singleton(new SimpleGrantedAuthority(member.get().getRole())),
                    attributes,
                    extractAttributes.getNameAttributeKey(),
                    member.get().getUsername(),
                    member.get().getRole()
            );
        }
    }

    private OAuth2Provider getSocialType(String registrationId) {
//        log.info("getSocialType의 메서드 : " + registrationId);
        if(KAKAO.equals(registrationId)) {
            return OAuth2Provider.KAKAO;
        }else{
            return OAuth2Provider.GOOGLE;
        }
    }






}
