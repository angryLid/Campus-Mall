package io.spring.guides.service;

import io.spring.guides.mbg.entity.User;

public interface UserService {
    User queryUserById(long id);
}
