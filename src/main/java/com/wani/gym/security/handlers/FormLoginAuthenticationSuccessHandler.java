package com.wani.gym.security.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wani.gym.member.dto.TokenDto;
import com.wani.gym.security.MemberContext;
import com.wani.gym.security.jwt.JwtFactory;
import com.wani.gym.security.token.PostAuthorizationToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class FormLoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtFactory jwtFactory;
    private final ObjectMapper objectMapper;

    public FormLoginAuthenticationSuccessHandler(JwtFactory jwtFactory, ObjectMapper objectMapper) {
        this.jwtFactory = jwtFactory;
        this.objectMapper = objectMapper;
    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        MemberContext context = ((PostAuthorizationToken) authentication).getMemberContext();

        String token = jwtFactory.generateToken(context);

        processResponse(response, writeDto(token));

    }


    private TokenDto writeDto(String token) {
        return new TokenDto(token);
    }


    private void processResponse(HttpServletResponse response, TokenDto dto) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.OK.value());
        response.getWriter().write(objectMapper.writeValueAsString(dto));
    }
}
