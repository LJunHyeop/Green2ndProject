package com.green.fefu.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class OnlineEnglishWord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wordId;

    //낱말, 정답, 사진, 영어

    @Column(length=1000)
    private String word;

    @Column(length=1000)
    private String answer;

    private String pic;

    @ManyToOne
    @JoinColumn(name="tea_id", nullable = false)
    private Teacher teaId;

    // 난이도
    // private Integer level;


    // 학년정보 추가
    // private Long classId;

}
