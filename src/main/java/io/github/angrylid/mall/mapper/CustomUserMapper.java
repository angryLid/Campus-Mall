package io.github.angrylid.mall.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import io.github.angrylid.mall.mbg.entity.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CustomUserMapper {
    List<User> getUsers();

    @Select("select * from user where telephone = #{telephone} and passwd = #{password};")
    User getUser(String telephone, String password);
}
