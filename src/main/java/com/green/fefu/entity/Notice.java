package com.green.fefu.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Notice extends UpdatedAt{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long noticeId;

    @ManyToOne
    @JoinColumn(name="writer_id", nullable=false)
    private Teacher teacher;

    //class 없음 만들어야함
    //@ManyToOne
    //@JoinColumn(name="class_id", nullable=false)
    //private Class classes;

    @Column(length=50)
    private String title;

    @Column(length=1000)
    private String content;

    private int state;
}
