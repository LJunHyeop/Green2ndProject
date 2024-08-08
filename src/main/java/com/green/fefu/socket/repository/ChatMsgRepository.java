    package com.green.fefu.socket.repository;

    import com.green.fefu.entity.ChatMsg;
    import org.springframework.data.jpa.repository.JpaRepository;

    public interface ChatMsgRepository extends JpaRepository<ChatMsg, Long> {
    }
