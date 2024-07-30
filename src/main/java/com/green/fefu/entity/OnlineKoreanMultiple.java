package com.green.fefu.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class OnlineKoreanMultiple {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long queId;

    private Integer num;

    @Column(length=30)
    private String sentence;
}
