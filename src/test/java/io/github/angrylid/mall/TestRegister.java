package io.github.angrylid.mall;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.github.angrylid.mall.mapper.AccountMapper;
import io.github.angrylid.mall.mapper.CustomUserMapper;
import io.github.angrylid.mall.mbg.entity.User;

@SpringBootTest
public class TestRegister {
    @Autowired
    private CustomUserMapper customUserMapper;

    @Autowired
    private AccountMapper accountMapper;

    @Test
    public void testSelect() {
        List<User> userList = customUserMapper.selectList(null);
        assertEquals(22, userList.size());
        userList.forEach(System.out::println);
    }

    @Test
    public void findDeveloper() {
        List<Long> idList = accountMapper.getUserIdByTelephone("17132954889");
        assertEquals(10001L, idList.get(0));
    }

    @Test
    public void findFake() {
        List<Long> idList = accountMapper.getUserIdByTelephone("17132954888");
        assertEquals(10002L, idList.get(0));
    }
}
