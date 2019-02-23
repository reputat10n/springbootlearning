package com.chau.abear.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public @Data class User {

    @Id
    @GeneratedValue
    private Long userId;

    @Column(nullable = false)
    private String passWord;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private String nickName;

    @Column(nullable = false)
    private String loginTime;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String email;

    public User() {
        // non param constructor need by JPA
    }

}
