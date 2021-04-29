package com.wani.gym.member.repository;

import com.wani.gym.member.entity.Member;
import com.wani.gym.member.entity.SocialProviders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUserId(String userId);

    Optional<Member> findByUsername(String username);

    Optional<Member> findBySocialIdAndSocialProviders(long socialId, SocialProviders socialProviders);

}
