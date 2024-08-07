package com.green.fefu.entity.dummy;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"subject_id", "type_num"})}) //복합키
public class TypeTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long TagId;

    @ManyToOne
    @JoinColumn(name="subject_id", nullable = false)
    private Subject subject;

    private Long typeNum;

    private String tagName;

}
