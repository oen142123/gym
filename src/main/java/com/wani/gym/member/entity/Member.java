package com.wani.gym.member.entity;

import com.wani.gym.common.entity.BasicEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String username;

    private String userId;

    private String password;

    private MemberRole memberRoles;

    private Long socialId;

    private SocialProviders socialProviders;

    private String profileHref;

    public Member(Long id, String username, String userId, String password, MemberRole memberRoles, Long socialId, SocialProviders socialProviders, String profileHref) {
        this.id = id;
        this.username = username;
        this.userId = userId;
        this.password = password;
        this.memberRoles = memberRoles;
        this.socialId = socialId;
        this.socialProviders = socialProviders;
        this.profileHref = profileHref;
    }
}
