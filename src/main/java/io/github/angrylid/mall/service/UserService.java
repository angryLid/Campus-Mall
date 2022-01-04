package io.github.angrylid.mall.service;

import io.github.angrylid.mall.mbg.entity.User;

import java.util.Date;
import java.util.List;

public interface UserService {

    User queryUserById(long id);

    List<User> fetchAllUsers();

    List<User> fetchUsers();

    List<User> fetchUsers(int pageNum, int pageSize);

    String login(String telephone, String password);

    String login(long primaryKey, String password) throws IllegalArgumentException;

    boolean modifyUser(long primaryKey, String department, String position, boolean isAdmin, String password);

    boolean addUser(String name, String gender, Date entry, String department, String position, boolean isAdmin, String password);

    boolean fireUser(Long id);

    boolean registerUser(String telephone,String password) throws IllegalArgumentException ;
}
