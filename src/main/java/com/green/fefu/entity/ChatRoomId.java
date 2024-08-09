package com.green.fefu.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "chat_room_id")
public class ChatRoomId extends UpdatedAt {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "room_id")
        private Long id;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "teacher_id")
        private Teacher teacher;

        @OneToMany(mappedBy = "chatRoomId", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<ChatRoomParents> parents = new ArrayList<>();

        public void addParticipant(Parents parent) {
            ChatRoomParents participant = new ChatRoomParents(this, parent);
            parents.add(participant);
    }

}
