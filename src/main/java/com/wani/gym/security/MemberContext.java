package com.wani.gym.security;

import com.wani.gym.member.entity.Member;
import com.wani.gym.member.entity.MemberRole;
import com.wani.gym.security.token.JwtPostProcessingToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MemberContext extends User {

    private Member member;

    private MemberContext(Member member, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.member = member;
    }

    public MemberContext(String username, String password, String role) {
        super(username, password, parseAuthorities(role));
    }

    public static MemberContext fromMemberModel(Member member) {
        return new MemberContext(member, member.getUserId(), member.getPassword(), parseAuthorities(member.getMemberRoles()));
    }

    public static MemberContext fromJwtPostToken(JwtPostProcessingToken token) {
        return new MemberContext(null, token.getUserId(), token.getPassword(), token.getAuthorities());
    }

    private static List<SimpleGrantedAuthority> parseAuthorities(MemberRole role) {
        return Arrays.asList(role)
                .stream()
                .map(r -> new SimpleGrantedAuthority(r.getRoleName()))
                .collect(Collectors.toList());
    }

    private static List<SimpleGrantedAuthority> parseAuthorities(String role) {
        return parseAuthorities(MemberRole.getRoleByName(role));
    }

    public Member getMember() {
        return member;
    }
}
