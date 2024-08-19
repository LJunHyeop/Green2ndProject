package com.green.fefu.socket.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GetMemberChat {

    private ParentsDto parentsId;
    private Long roomId;
    private TeacherDto teaId;


    @Builder
    public GetMemberChat(Long roomId, TeacherDto teaId, ParentsDto parentsId) {
        this.roomId = roomId;
        this.teaId = teaId;
        this.parentsId = parentsId;


    }
}
