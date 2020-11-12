package io.spring.guides.mapper;

import io.spring.guides.mbg.entity.Message;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CustomMessageMapper {

    @Select("SELECT * FROM message WHERE user_id = #{user_id}")
    List<Message> getMessagesByUser(@Param("user_id") Long id);
}
