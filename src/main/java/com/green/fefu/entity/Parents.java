package com.green.fefu.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table
public class Parents extends UpdatedAt{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long parentsId ;
    @Column(length = 30)
    private String uid ;
    @Column(length = 70)
    private String upw ;
    @Column(length = 20)
    private String nm ;
    @Column(length = 20)
    private String phone ;
    @Column(length = 20)
    private String subPhone ;
    @Column(length = 255)
    private String email ;
    @Column(length = 10)
    private String connect ;
    @Column(length = 30)
    private String auth ;
    @Column(length = 300)
    private String addr ;
    private int accept ;
}
