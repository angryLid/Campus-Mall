package io.spring.guides.service;

import io.spring.guides.mbg.entity.User;

import java.util.List;

public interface UserService {

    User queryUserById(long id);

    List<User> fetchAllUsers();

    List<User> fetchUsers();
}
