package io.github.angrylid.mall;

import io.github.angrylid.mall.mapper.CustomUserMapper;
import io.github.angrylid.mall.mbg.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TestLogin {

    @Autowired
    private CustomUserMapper customUserMapper;

    @Test
    public void testUserLogin() {
        List<User> userList = this.customUserMapper.getUsers();
        assertEquals(2, userList.size());
        for (User user : userList) {
            System.out.println(user);
        }
        assertEquals(10002L, userList.get(0).getId());

    }

    @Test
    public void loginAdmin() {
        User user = this.customUserMapper.getUser("17132954889", "8080");
        assertEquals(10001L, user.getId());
    }
}
