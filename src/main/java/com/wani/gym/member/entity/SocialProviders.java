package com.wani.gym.member.entity;

import lombok.Getter;

@Getter
public class SocialProviders {

    KAKAO("https://kapi.kakao.com/v1/user/me",KaKaoUserProperty .class),
    NAVER("https://openapi.naver.com/v1/nid/me",NaverUserProperty .class);

    private String userInfoEndPoint;
    private Class<? extends SocialUserProperty> propertyMetaClass;

}
