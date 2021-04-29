package com.wani.gym.security.token;

import com.wani.gym.member.dto.SocialLoginDto;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class SocialPreAuthorizationToken extends UsernamePasswordAuthenticationToken {

    public SocialPreAuthorizationToken(SocialLoginDto dto) {
        super(dto.getProvider(), dto);
    }

    public SocialLoginDto getDto() {
        return (SocialLoginDto) super.getCredentials();
    }

}
