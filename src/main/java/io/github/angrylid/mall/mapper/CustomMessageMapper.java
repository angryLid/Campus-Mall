package io.github.angrylid.mall.mapper;

import io.github.angrylid.mall.mbg.entity.Message;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CustomMessageMapper {

    @Select("SELECT * FROM message WHERE user_id = #{user_id}")
    List<Message> getMessagesByUser(@Param("user_id") Long id);
}
