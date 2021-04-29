package com.wani.gym.security.sevices.impl;

import com.wani.gym.member.dto.SocialLoginDto;
import com.wani.gym.member.entity.SocialProviders;
import com.wani.gym.security.sevices.specification.SocialFetchService;
import com.wani.gym.security.social.SocialUserProperty;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class SocialFetchServiceProd implements SocialFetchService {

    private static final String HEADER_PREFIX = "Bearer ";


    @Override
    public SocialUserProperty getSocialUserInfo(SocialLoginDto dto) {
        SocialProviders provider = dto.getProvider();
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<String> entity = new HttpEntity<>("parameter" , generateHeader(dto.getToken()));

        return restTemplate.exchange(provider.getUserInfoEndPoint(), HttpMethod.GET , entity , provider.getPropertyMetaClass()).getBody();
    }

    private HttpHeaders generateHeader(String token) {
        HttpHeaders headers = new HttpHeaders();

        headers.add("Authorization", generateHeaderContent(token));
        return headers;
    }

    private String generateHeaderContent(String token) {
        StringBuilder sb = new StringBuilder();

        sb.append(HEADER_PREFIX);
        sb.append(token);

        return sb.toString();
    }
}
