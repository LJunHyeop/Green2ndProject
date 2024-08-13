package com.green.fefu.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class User extends UpdatedAt {
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
    private Integer accept = 2;

    @Column(nullable = false)
    private Integer state = 1;
}
