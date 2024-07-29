package com.green.fefu.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@MappedSuperclass
public class User extends UpdatedAt{
    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false, length = 30, unique = true)
    private String uid;

    @Column(length = 70)
    private String upw;

    @Column(length = 20, nullable = false)
    private String phone;

    @Column(length = 255, nullable = false, unique = true)
    private String email;

    @Column(length = 30, nullable = false)
    private String auth;

    @Column(length = 300)
    private String addr;

    @Column(nullable = false)
    @ColumnDefault("2")
    private Integer accept;
}
