package com.green.fefu.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stuId;

//    부모 추가 해야함
//    @ManyToOne
//    @JoinColumn(name = "parents_id")
//    private Parent
    private Integer grade;
    private String name;
    private String gender;

    private String phone;
}
