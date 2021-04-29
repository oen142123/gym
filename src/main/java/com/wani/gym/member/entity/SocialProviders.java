package com.wani.gym.member.entity;

import com.fasterxml.jackson.annotation.JsonValue;
import com.wani.gym.security.social.KaKaoUserProperty;
import com.wani.gym.security.social.NaverUserProperty;
import com.wani.gym.security.social.SocialUserProperty;
import lombok.Getter;

@Getter
public enum SocialProviders {

    KAKAO("https://kapi.kakao.com/v1/user/me", KaKaoUserProperty.class),

    NAVER("https://openapi.naver.com/v1/nid/me", NaverUserProperty.class);

    private String userInfoEndPoint;
    private Class<? extends SocialUserProperty> propertyMetaClass;

    SocialProviders(String userInfoEndPoint, Class<? extends SocialUserProperty> propertyMetaClass) {
        this.userInfoEndPoint = userInfoEndPoint;
        this.propertyMetaClass = propertyMetaClass;
    }

    @JsonValue
    public String getProviderName() {
        return this.name();
    }
}
