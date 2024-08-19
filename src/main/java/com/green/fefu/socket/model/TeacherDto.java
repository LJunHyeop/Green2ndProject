package com.green.fefu.socket.model;

import com.green.fefu.entity.Teacher;
import lombok.Data;

@Data
public class TeacherDto {
    private Long teaId;

    private String name;

    public TeacherDto(Teacher data) {
        this.teaId = data.getTeaId() ;
        this.name = data.getName() ;
    }

}
