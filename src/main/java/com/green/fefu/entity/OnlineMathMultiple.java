package com.green.fefu.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class OnlineMathMultiple {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long AnswerId;

    @ManyToOne
    @JoinColumn(name="que_id", nullable = false)
    private OnlineMath onlineMath;

    private Integer num;

    @Column(length=30)
    private String sentence;

}
