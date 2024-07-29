package com.green.fefu.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Student extends UserST {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stuId;

    @Column(nullable = false)
    private Integer grade;

    @Column(length = 255)
    private String pic;

    @Column(length = 1000)
    private String etc;

    @Column(length = 30)
    private String engName;

//    부모 pk 넣어야 함
//    @ManyToOne
//    @JoinColumn(name = "parent_id")
//    private Parent parent;
}
