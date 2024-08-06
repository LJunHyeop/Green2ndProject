package com.green.fefu.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class OnlineMath extends UpdatedAt{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long queId;

    @Column(length=1000)
    private String question;

    @Column(length=1000)
    private String contents;

    private Integer answer;

    // 난이도
    private Integer level;

    @ManyToOne
    @JoinColumn(name="tea_id", nullable = false)
    private Teacher teaId;

    // 학년정보 추가
    private Long classId;

    // 문제 유형
    private Long typeTag;

    // 문제 타입 1->객관식 2-> 주관식
    private Long queTag;

}
