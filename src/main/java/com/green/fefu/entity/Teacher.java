package com.green.fefu.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Teacher extends UserST {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teaId;

}
