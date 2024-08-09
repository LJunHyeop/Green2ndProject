package com.green.fefu.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Entity
@Getter
@Setter
@Table(name = "chat_room_participant")
public class ChatRoomParents extends UpdatedAt {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "room_id")
        private ChatRoomId chatRoomId;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "parent_id")
        private Parents parent;

        @Builder
        public ChatRoomParents(ChatRoomId chatRoomId, Parents parent) {
            this.chatRoomId = chatRoomId;
            this.parent = parent;

    }
}
