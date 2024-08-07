package com.green.fefu.socket.repository;

import com.green.fefu.entity.ChatRoom;
import com.green.fefu.entity.Teacher;
import com.green.fefu.entity.Parents;
import com.green.fefu.socket.model.ChatRoomDto;
import com.green.fefu.socket.repository.ChatRoomRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public class ChatRoomRepositoryCustomImpl implements ChatRoomRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<ChatRoomDto> getChatRoomList(Long roomId) {
        String jpql = "SELECT new com.green.fefu.socket.model.ChatRoomDto(c.roomId, c.teaId, c.parentsId) " +
                "FROM ChatRoom c " +
                "WHERE c.roomId = :roomId";

        TypedQuery<ChatRoomDto> query = em.createQuery(jpql, ChatRoomDto.class);
        query.setParameter("roomId", roomId);

        return query.getResultList();
    }

    @Override
    public List<ChatRoomDto> getChatRoomId(Parents parents, Teacher teacher) {
        String jpql = "SELECT new com.green.fefu.socket.model.ChatRoomDto(c.roomId, c.teaId, c.parentsId) " +
                "FROM ChatRoom c " +
                "WHERE c.teaId = :teacher AND c.parentsId = :parents";

        TypedQuery<ChatRoomDto> query = em.createQuery(jpql, ChatRoomDto.class);
        query.setParameter("teacher", teacher);
        query.setParameter("parents", parents);

        return query.getResultList();
    }
}