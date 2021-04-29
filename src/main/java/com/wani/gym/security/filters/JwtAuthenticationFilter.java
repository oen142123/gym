package com.wani.gym.security.filters;

import com.wani.gym.security.handlers.JwtAuthenticationFailureHandler;
import com.wani.gym.security.jwt.HeaderTokenExtractor;
import com.wani.gym.security.token.JwtPreProcessingToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private JwtAuthenticationFailureHandler failureHandler;
    private HeaderTokenExtractor extractor;

    protected JwtAuthenticationFilter(RequestMatcher requestMatcher) {
        super(requestMatcher);
    }


    public JwtAuthenticationFilter(RequestMatcher matcher, JwtAuthenticationFailureHandler failureHandler, HeaderTokenExtractor tokenExtractor) {
        super(matcher);

        this.failureHandler = failureHandler;
        this.extractor = tokenExtractor;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String tokenPayload = request.getHeader("Authorization");

        JwtPreProcessingToken token = new JwtPreProcessingToken(this.extractor.extract(tokenPayload));

        return super.getAuthenticationManager().authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);

        chain.doFilter(request, response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();

        super.unsuccessfulAuthentication(request, response, failed);
    }
}
