package com.green.fefu.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class OnlineKorean extends UpdatedAt{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long queId;

    @Column(length=1000)
    private String question;

    @Column(length=1000)
    private String contents;

    private Integer answer;

    @ManyToOne
    @JoinColumn(name="tea_id", nullable = false)
    private Teacher teaId;


    //학년정보 추가
}
