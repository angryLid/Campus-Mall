package io.spring.guides.service.impl;

import io.spring.guides.mapper.UserMapper;
import io.spring.guides.model.User;
import io.spring.guides.service.UserService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper mapper;

    @Override
    public User queryUserById(long id) {
        return this.mapper.selectById(id);
//        return new User(233L,"USERL","F");
    }
}
