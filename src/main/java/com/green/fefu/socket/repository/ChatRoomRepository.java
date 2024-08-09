package com.green.fefu.socket.repository;

import com.green.fefu.entity.ChatRoom;
import com.green.fefu.entity.Parents;
import com.green.fefu.entity.Teacher;
import com.green.fefu.socket.model.ChatRoomDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> ,ChatRoomRepositoryCustom{
    List<ChatRoomDto> getChatRoomList(Long roomId);

    List<ChatRoomDto> getChatRoomId(Parents ParentsId, Teacher teacher);

    @Query("SELECT cr FROM ChatRoom cr WHERE cr.teaId = :teacherId")
    List<ChatRoom> findAllByTeacherId(@Param("teacherId") Teacher teacherId);

    @Query("SELECT cr FROM ChatRoom cr WHERE cr.teaId = :parentsId")
    List<ChatRoom> findAllByParentsId(@Param("parentId") Parents parentsId);



}
