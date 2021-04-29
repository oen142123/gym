package com.wani.gym.member.entity;

import com.wani.gym.common.entity.BasicEntity;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Customer extends BasicEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long id;


    private String username;

    private String profileImageUrl;

    @Embedded
    private PhoneNumber phoneNumber;

    @Embedded
    private CustomerInfo customerInfo;
}
