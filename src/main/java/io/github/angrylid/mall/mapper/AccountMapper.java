package io.github.angrylid.mall.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface AccountMapper {
    @Select("SELECT id FROM user WHERE telephone = #{telephone}")
    List<Long> getUserIdByTelephone(@Param("telephone") String telephone);
}
