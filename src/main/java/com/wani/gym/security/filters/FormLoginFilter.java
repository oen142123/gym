package com.wani.gym.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wani.gym.member.dto.FormLoginDto;
import com.wani.gym.security.token.PreAuthorizationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FormLoginFilter extends AbstractAuthenticationProcessingFilter {

    private AuthenticationSuccessHandler authenticationSuccessHandler;
    private AuthenticationFailureHandler authenticationFailureHandler;
    private ObjectMapper objectMapper;

    public FormLoginFilter(String defaultUrl, AuthenticationSuccessHandler successHandler, AuthenticationFailureHandler failureHandler, ObjectMapper objectMapper) {
        super(defaultUrl);

        this.authenticationSuccessHandler = successHandler;
        this.authenticationFailureHandler = failureHandler;
        this.objectMapper = objectMapper;
    }

    protected FormLoginFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        FormLoginDto dto = objectMapper.readValue(request.getReader(), FormLoginDto.class);
        PreAuthorizationToken token = new PreAuthorizationToken(dto);

        return super.getAuthenticationManager().authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        this.authenticationSuccessHandler.onAuthenticationSuccess(request, response, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        AuthenticationFailureHandler handler = (req, res, exception) -> {
            Logger log = LoggerFactory.getLogger("authentication_failure");

            log.error(exception.getMessage());
        };

        handler.onAuthenticationFailure(request, response, failed);
    }
}
