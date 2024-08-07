package com.green.fefu.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "chat_msg")
public class ChatMsg extends UpdatedAt {
    // 채팅 데이터 베이스에 저장하는 컬럼
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_name")
    private Long id;

    private String sender;

    private String msg;

    @ManyToOne(fetch = FetchType.LAZY, cascade =  CascadeType.ALL)
    @JoinColumn(name = "room_id")
    private ChatRoom chatRoom;


    @Builder
    public ChatMsg(String sender, String msg, ChatRoom chatRoom) {
        this.sender = sender;
        this.msg = msg;
        this.chatRoom = chatRoom;
    }
}
