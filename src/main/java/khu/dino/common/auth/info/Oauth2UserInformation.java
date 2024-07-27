package khu.dino.auth.info;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public abstract class Oauth2UserInformation {
    protected Map<String, Object> attributes;

    public abstract String getIdentifierKey();
    //google : sub
    //kakao : id

    public abstract String  getNickname();

}
