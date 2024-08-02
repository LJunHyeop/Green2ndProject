package com.green.fefu.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Class extends UpdatedAt {
    @Id
    private long classId ;

    @OneToOne
    @JoinColumn(name = "tea_id",nullable = false)
    private Teacher teaId ;
}
