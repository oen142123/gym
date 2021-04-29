package com.wani.gym.security.providers;

import com.wani.gym.member.dto.SocialLoginDto;
import com.wani.gym.member.entity.Member;
import com.wani.gym.member.entity.MemberRole;
import com.wani.gym.member.entity.SocialProviders;
import com.wani.gym.member.repository.MemberRepository;
import com.wani.gym.security.MemberContext;
import com.wani.gym.security.sevices.specification.SocialFetchService;
import com.wani.gym.security.social.SocialUserProperty;
import com.wani.gym.security.token.PostAuthorizationToken;
import com.wani.gym.security.token.SocialPreAuthorizationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SocialLoginAuthenticationProvider implements AuthenticationProvider {

    private final MemberRepository memberRepository;
    private final SocialFetchService socialFetchService;

    public SocialLoginAuthenticationProvider(MemberRepository memberRepository, SocialFetchService socialFetchService) {
        this.memberRepository = memberRepository;
        this.socialFetchService = socialFetchService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SocialPreAuthorizationToken token = (SocialPreAuthorizationToken) authentication;
        SocialLoginDto dto = token.getDto();
        return PostAuthorizationToken.getTokenFromAccountContext(MemberContext.fromMemberModel(getMember(dto)));
    }

    private Member getMember(SocialLoginDto dto) {
        SocialUserProperty property = socialFetchService.getSocialUserInfo(dto);

        String userId = property.getUserId();
        SocialProviders provider = dto.getProvider();

        return memberRepository.findBySocialIdAndSocialProviders(Long.parseLong(userId), provider)
                .orElseGet(() -> memberRepository.save(
                        new Member(null, property.getUserNickName(), "SOCIAL_USER", String.valueOf(UUID.randomUUID().getMostSignificantBits()), MemberRole.TRAINER,
                                Long.valueOf(property.getUserId()), provider, property.getProfileHref())
                ));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SocialPreAuthorizationToken.class.isAssignableFrom(authentication);
    }
}
