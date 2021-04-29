package com.wani.gym.security.providers;

import com.wani.gym.member.entity.Member;
import com.wani.gym.member.repository.MemberRepository;
import com.wani.gym.security.MemberContext;
import com.wani.gym.security.token.PostAuthorizationToken;
import com.wani.gym.security.token.PreAuthorizationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class FormLoginAuthenticationProvider implements AuthenticationProvider {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    public FormLoginAuthenticationProvider(PasswordEncoder passwordEncoder, MemberRepository memberRepository) {
        this.passwordEncoder = passwordEncoder;
        this.memberRepository = memberRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        PreAuthorizationToken token = (PreAuthorizationToken) authentication;

        String username = token.getUsername();
        String password = token.getUserPassword();

        Member member = memberRepository.findByUserId(username)
                .orElseThrow(() -> new NoSuchElementException("정보에 맞는 계정이 없습니다."));

        if (isCorrectPassword(password, member)) {
            return PostAuthorizationToken.getTokenFromAccountContext(MemberContext.fromMemberModel(member));
        }
        System.out.println("member = " + member.getPassword());
        System.out.println("PRE = " + password);


        throw new NoSuchElementException("인증 정보가 정확하지 않습니다.");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PreAuthorizationToken.class.isAssignableFrom(authentication);
    }

    private boolean isCorrectPassword(String password, Member member) {
        return passwordEncoder.matches(password, member.getPassword());
    }
}
