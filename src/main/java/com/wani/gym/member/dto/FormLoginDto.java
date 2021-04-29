package com.wani.gym.member.dto;

public class FormLoginDto {

    private String id;
    private String password;

    public FormLoginDto() {
    }

    public FormLoginDto(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }
}
