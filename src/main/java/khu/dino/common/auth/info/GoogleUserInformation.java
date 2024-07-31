package khu.dino.common.auth.info;

import java.util.Map;

public class GoogleUserInformation extends Oauth2UserInformation{

    private final String id;
    private final String nickname;
    public GoogleUserInformation(Map<String, Object> attributes) {
        super(attributes);
        this.id = (String) attributes.get("sub");
        this.nickname = (String) attributes.get("name");

    }

    @Override
    public String getIdentifierKey() {
        return this.id;
    }

    @Override
    public String getNickname() {
        return this.nickname;
    }
}
