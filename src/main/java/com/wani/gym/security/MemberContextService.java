package com.wani.gym.security;

import com.wani.gym.member.entity.Member;
import com.wani.gym.member.entity.MemberRole;
import com.wani.gym.member.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class MemberContextService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public MemberContextService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Member member = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new NoSuchElementException("아이디에 맞는 계정이 없습니다."));

        return getMemberContext(member);
    }

    private MemberContext getMemberContext(Member member) {
        return MemberContext.fromMemberModel(member);
    }
}
