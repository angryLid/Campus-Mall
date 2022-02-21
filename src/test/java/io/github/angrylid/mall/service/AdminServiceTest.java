package io.github.angrylid.mall.service;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AdminServiceTest {

    private AdminService adminService;

    @Autowired
    public AdminServiceTest(AdminService adminService) {
        this.adminService = adminService;
    }

    @Test
    void testSelectSum() {

        Map<String, Long> map = adminService.selectSum();

        System.out.println(map);
    }
}
