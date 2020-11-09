package io.spring.guides.service;

import io.spring.guides.model.User;

public interface UserService {
    User queryUserById(long id);
}
