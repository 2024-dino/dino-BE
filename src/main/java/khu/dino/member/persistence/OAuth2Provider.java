package khu.dino.member.persistence;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum OAuth2Provider {
    KAKAO("KAKAO"),
    GOOGLE("GOOGLE");

    private final String provider;
}
