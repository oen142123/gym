package com.wani.gym.security.sevices.specification;

import com.wani.gym.member.dto.SocialLoginDto;
import com.wani.gym.security.social.SocialUserProperty;

public interface SocialFetchService {

    SocialUserProperty getSocialUserInfo(SocialLoginDto dto);
}
