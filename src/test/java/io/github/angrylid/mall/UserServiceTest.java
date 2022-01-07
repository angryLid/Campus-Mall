package io.github.angrylid.mall;

import io.github.angrylid.mall.entity.AccountInformation;
import io.github.angrylid.mall.generated.entity.User;
import io.github.angrylid.mall.mapper.CustomUserMapper;
import io.github.angrylid.mall.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private CustomUserMapper customUserMapper;

    @Autowired
    private UserService userService;

    @Test
    public void testGetUserByTel() {
        User user = this.customUserMapper.getUserByTel("17132954889");
        assertEquals("17132954889", user.getTelephone());
    }

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
    public void testGetFriends() {
        AccountInformation friend = this.userService.getFollowingAndFollowedOfCurrentUser(10001);
        assertEquals(7, friend.getFollowing());
        assertEquals(3, friend.getFollowed());

    }

    @Test
    public void loginAdmin() {
        User user = this.customUserMapper.getUser("17132954889", "8080");
        assertEquals(10001L, user.getId());
    }

    @Test
    public void addUser() {
        String telephone = "12100090009";
        String password = "12345678";
        String nickname = "user888";

        int id = this.customUserMapper.addUser(telephone, password, nickname);

        assertEquals(0, id);
    }


}
