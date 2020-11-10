package io.spring.guides.service.impl;

import io.spring.guides.mbg.dao.UserMapper;
import io.spring.guides.mbg.entity.User;
import io.spring.guides.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper mapper;

    @Override
    public User queryUserById(long id) {
        return this.mapper.selectByPrimaryKey(id);
//        return new User(233L,"USERL","F");
    }
}
