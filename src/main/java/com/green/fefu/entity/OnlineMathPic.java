package com.green.fefu.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class OnlineMathPic extends CreatedAt{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long picId;

    @ManyToOne
    @JoinColumn(name="que_id", nullable=false)
    private OnlineMath onlineMath;

    @Column(length=70)
    private String pic;
}
