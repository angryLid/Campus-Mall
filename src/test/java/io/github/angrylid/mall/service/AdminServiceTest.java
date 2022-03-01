package io.github.angrylid.mall.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.github.angrylid.mall.dto.response.StatisticDTO;

@SpringBootTest
public class AdminServiceTest {

    private AdminService adminService;

    @Autowired
    public AdminServiceTest(AdminService adminService) {
        this.adminService = adminService;
    }

    @Test
    void testSelectSum() {

        StatisticDTO map = adminService.selectSum();

        System.out.println(map);
    }
}
