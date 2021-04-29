package com.wani.gym;

import com.wani.gym.member.entity.Member;
import com.wani.gym.member.entity.MemberRole;
import com.wani.gym.member.repository.MemberRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class GymApplication {

    public static void main(String[] args) {
        SpringApplication.run(GymApplication.class, args);
    }


    @Bean
    CommandLineRunner bootstrapTestMember(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            Member member = new Member(null, "test", "test", passwordEncoder.encode("test"), MemberRole.SUPER, null, null, null);
            memberRepository.save(member);
        };
    }
}
