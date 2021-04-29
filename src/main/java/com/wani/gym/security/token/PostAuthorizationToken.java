package com.wani.gym.security.token;

import com.wani.gym.security.MemberContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class PostAuthorizationToken extends UsernamePasswordAuthenticationToken {


    public PostAuthorizationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

    public MemberContext getMemberContext() {
        return (MemberContext) super.getPrincipal();
    }

    public static PostAuthorizationToken getTokenFromAccountContext(MemberContext memberContext) {
        return new PostAuthorizationToken(memberContext, memberContext.getPassword(), memberContext.getAuthorities());
    }
}
