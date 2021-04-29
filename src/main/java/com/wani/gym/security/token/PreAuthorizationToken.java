package com.wani.gym.security.token;

import com.wani.gym.member.dto.FormLoginDto;
import com.wani.gym.member.dto.SocialLoginDto;
import com.wani.gym.member.entity.SocialProviders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class PreAuthorizationToken extends UsernamePasswordAuthenticationToken {

    private PreAuthorizationToken(String username, String password) {
        super(username, password);
    }

    private PreAuthorizationToken(SocialProviders providers, SocialLoginDto dto) {
        super(providers, dto);
    }

    public PreAuthorizationToken(FormLoginDto dto) {
        this(dto.getId(), dto.getPassword());
    }

    public PreAuthorizationToken(SocialLoginDto dto) {
        this(dto.getProvider(), dto);
    }

    public String getUsername() {
        return (String)super.getPrincipal();
    }

    public String getUserPassword() {
        return (String)super.getCredentials();
    }
}
