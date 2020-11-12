package io.spring.guides.mbg.dao;

import io.spring.guides.mbg.entity.User;
import java.util.List;

public interface UserMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbg.generated Thu Nov 12 16:50:45 CST 2020
     */
    int deleteByPrimaryKey(Long uid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbg.generated Thu Nov 12 16:50:45 CST 2020
     */
    int insert(User record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbg.generated Thu Nov 12 16:50:45 CST 2020
     */
    User selectByPrimaryKey(Long uid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbg.generated Thu Nov 12 16:50:45 CST 2020
     */
    List<User> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user
     *
     * @mbg.generated Thu Nov 12 16:50:45 CST 2020
     */
    int updateByPrimaryKey(User record);
}