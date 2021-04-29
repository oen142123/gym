package com.wani.gym.member.entity;

import lombok.Getter;

import java.util.Arrays;
import java.util.NoSuchElementException;

@Getter
public enum MemberRole {

    SUPER("ROLE_SUPER"), TRAINER("ROLE_TRAINER");

    private String roleName;

    MemberRole(String roleName) {
        this.roleName = roleName;
    }

    public boolean isCorrectName(String name) {
        return name.equalsIgnoreCase(this.roleName);
    }

    public static MemberRole getRoleByName(String roleName) {
        return Arrays.stream(MemberRole.values())
                .filter(role -> role.isCorrectName(roleName))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("검색된 권한이 없습니다."));
    }
}
