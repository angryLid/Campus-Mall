package io.github.angrylid.mall.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import io.github.angrylid.mall.generated.entity.User;

public interface CustomUserMapper {
    List<User> getUsers();

    @Select("select * from user where telephone = #{telephone} and passwd = #{password};")
    User getUser(String telephone, String password);

    @Insert("insert into user(telephone, passwd, nikename) values(#{telephone},#{password}, #{nickname})")
    int addUser(String telephone, String password, String nickname);

    @Select("select * from user where telephone = #{telephone};")
    User getUserByTel(String telephone);

    @Select("select * from user where id = #{id};")
    User getUserById(int id);

    @Select("select count(id) from relation where user_id = #{id};")
    int getFollowingSpecificUser(int id);

    @Select("select count(id) from scott.relation where follower_id = #{id};")
    int getFollowedSpecificUser(int id);
}
