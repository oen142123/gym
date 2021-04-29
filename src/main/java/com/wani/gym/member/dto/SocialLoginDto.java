package com.wani.gym.member.dto;

import com.wani.gym.member.entity.SocialProviders;

public class SocialLoginDto {

    private SocialProviders provider;

    private String token;

    public SocialLoginDto() {
    }

    public SocialLoginDto(SocialProviders provider, String token) {
        this.provider = provider;
        this.token = token;
    }

    public SocialProviders getProvider() {
        return provider;
    }

    public String getToken() {
        return token;
    }
}
