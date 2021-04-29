package com.wani.gym.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wani.gym.member.dto.SocialLoginDto;
import com.wani.gym.security.token.SocialPreAuthorizationToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class SocialLoginFilter extends AbstractAuthenticationProcessingFilter {

    private AuthenticationSuccessHandler successHandler;
    private ObjectMapper objectMapper;

    protected SocialLoginFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    public SocialLoginFilter(String url, AuthenticationSuccessHandler handler, ObjectMapper objectMapper) {
        super(url);
        this.successHandler = handler;
        this.objectMapper = objectMapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        SocialLoginDto dto = objectMapper.readValue(request.getReader(), SocialLoginDto.class);
        return super.getAuthenticationManager().authenticate(new SocialPreAuthorizationToken(dto));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
    }
}
