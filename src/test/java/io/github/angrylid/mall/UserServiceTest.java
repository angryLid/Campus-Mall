package io.github.angrylid.mall;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.github.angrylid.mall.mapper.CustomUserMapper;
import io.github.angrylid.mall.service.UserService;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private CustomUserMapper customUserMapper;

    @Autowired
    private UserService userService;

}
