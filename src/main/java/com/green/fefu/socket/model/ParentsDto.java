package com.green.fefu.socket.model;

import com.green.fefu.entity.Parents;
import lombok.Data;

@Data
public class ParentsDto {
    private Long parentId;

    private String name;

    public ParentsDto(Parents data) {
        this.parentId = data.getParentsId();
        this.name = data.getName();
    }
}
