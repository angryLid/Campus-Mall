package io.github.angrylid.mall;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.github.angrylid.mall.mapper.CustomUserMapper;
import io.github.angrylid.mall.mbg.entity.User;

@SpringBootTest
public class TestRegister {
    @Autowired
    private CustomUserMapper customUserMapper;

    @Test
    public void testSelect() {
        List<User> userList = customUserMapper.selectList(null);
        assertEquals(22, userList.size());
        userList.forEach(System.out::println);
    }
}
