package io.spring.guides.mapper;

import io.spring.guides.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Select("select * from user where uid=#{id}")
    @Results(id = "user", value = {
            @Result(property = "uid", column = "uid", javaType = Long.class),
            @Result(property = "uname", column = "uname", javaType = String.class),
            @Result(property = "usex", column = "usex", javaType = String.class)
    })
    User selectById(long id);
}
