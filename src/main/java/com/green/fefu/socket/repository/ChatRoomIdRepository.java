package com.green.fefu.socket.repository;

import com.green.fefu.entity.ChatRoomId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomIdRepository extends JpaRepository<ChatRoomId, Long> {
}
