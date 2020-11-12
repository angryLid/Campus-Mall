package io.spring.guides.service;

import io.spring.guides.mbg.entity.User;

import java.util.Date;
import java.util.List;

public interface UserService {

    User queryUserById(long id);

    List<User> fetchAllUsers();

    List<User> fetchUsers();

    String login(String name, String password);

    String login(long primaryKey, String password) throws IllegalArgumentException;

    boolean modifyUser(long primaryKey,Date separation, String department, String position, boolean isAdmin, String password);

    boolean addUser(String name, String gender, Date entry, String department, String position, boolean isAdmin, String password);

}
