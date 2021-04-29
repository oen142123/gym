package com.wani.gym.security.social;

import java.util.HashMap;

public class KaKaoUserProperty implements SocialUserProperty {

    private String userEmail;
    private Boolean verified;
    private Long userUniqueId;
    private HashMap<String, String> userProperties;

    @Override
    public String getUserId() {
        return userUniqueId.toString();
    }

    @Override
    public String getUserNickName() {
        return userProperties.get("nickname");
    }

    @Override
    public String getProfileHref() {
        return userProperties.get("profile_image");
    }

    @Override
    public String getEmail() {
        return userEmail;
    }
}
