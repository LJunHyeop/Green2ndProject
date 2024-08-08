package com.green.fefu.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter

@Table(name ="chat_room")
public class ChatRoom  extends UpdatedAt{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rood_id")
    private Long roomId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teaId")
    private Teacher teaId;

    @OneToMany
    @Column(name = "chatList")
    private List<ChatMsg> chatMsgList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parents_id")
    private Parents parentsId;


    @Builder
    public ChatRoom(Teacher teaId, Parents parentsId) {
        this.teaId = teaId;
        this.parentsId = parentsId;
    }

}
