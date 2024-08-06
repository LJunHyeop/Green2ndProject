package com.green.fefu.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class HaesolOnlineMultiple {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long AnswerId;

    private Integer num;

    @ManyToOne
    @JoinColumn(name="que_id", nullable = false)
    private HaesolOnline haesolOnline;

    @Column(length=30, nullable = false)
    private String sentence;
}
