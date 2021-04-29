package com.wani.gym.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wani.gym.security.filters.FilterSkipMatcher;
import com.wani.gym.security.filters.FormLoginFilter;
import com.wani.gym.security.filters.JwtAuthenticationFilter;
import com.wani.gym.security.filters.SocialLoginFilter;
import com.wani.gym.security.handlers.FormLoginAuthenticationSuccessHandler;
import com.wani.gym.security.handlers.JwtAuthenticationFailureHandler;
import com.wani.gym.security.jwt.HeaderTokenExtractor;
import com.wani.gym.security.providers.FormLoginAuthenticationProvider;
import com.wani.gym.security.providers.JwtAuthenticationProvider;
import com.wani.gym.security.providers.SocialLoginAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final FormLoginAuthenticationSuccessHandler formLoginAuthenticationSuccessHandler;
    private final FormLoginAuthenticationProvider formLoginProvider;
    private final JwtAuthenticationProvider jwtProvider;
    private final SocialLoginAuthenticationProvider socialProvider;
    private final JwtAuthenticationFailureHandler jwtAuthenticationFailureHandler;
    private final HeaderTokenExtractor headerTokenExtractor;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(FormLoginAuthenticationSuccessHandler formLoginAuthenticationSuccessHandler, FormLoginAuthenticationProvider formLoginProvider,
                          JwtAuthenticationProvider jwtProvider, SocialLoginAuthenticationProvider socialProvider,
                          JwtAuthenticationFailureHandler jwtAuthenticationFailureHandler,
                          HeaderTokenExtractor headerTokenExtractor, ObjectMapper objectMapper, PasswordEncoder passwordEncoder) {
        this.formLoginAuthenticationSuccessHandler = formLoginAuthenticationSuccessHandler;
        this.formLoginProvider = formLoginProvider;
        this.jwtProvider = jwtProvider;
        this.socialProvider = socialProvider;
        this.jwtAuthenticationFailureHandler = jwtAuthenticationFailureHandler;
        this.headerTokenExtractor = headerTokenExtractor;
        this.objectMapper = objectMapper;
        this.passwordEncoder = passwordEncoder;
    }


    @Bean
    public AuthenticationManager getAuthenticationManager() throws Exception {
        return super.authenticationManagerBean();
    }

    protected FormLoginFilter formLoginFilter() throws Exception {
        FormLoginFilter filter = new FormLoginFilter("/formlogin", formLoginAuthenticationSuccessHandler, null, objectMapper);

        filter.setAuthenticationManager(super.authenticationManagerBean());

        return filter;
    }

    protected JwtAuthenticationFilter jwtFilter() throws Exception {
        FilterSkipMatcher matcher = new FilterSkipMatcher(Arrays.asList("/formlogin", "social"), "/api/**");
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(matcher, jwtAuthenticationFailureHandler, headerTokenExtractor);

        filter.setAuthenticationManager(super.authenticationManagerBean());
        return filter;
    }

    protected SocialLoginFilter socialLoginFilter() throws Exception {
        SocialLoginFilter filter = new SocialLoginFilter("/social", formLoginAuthenticationSuccessHandler, objectMapper);

        filter.setAuthenticationManager(super.authenticationManagerBean());

        return filter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .authenticationProvider(this.formLoginProvider)
                .authenticationProvider(this.jwtProvider)
                .authenticationProvider(this.socialProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http
                .csrf().disable();

        http
                .headers().frameOptions().disable();

        http
                .authorizeRequests()
                .antMatchers("/h2-console**").permitAll();

        http
                .addFilterBefore(formLoginFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(socialLoginFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
