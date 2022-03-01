package io.github.angrylid.mall.api.admin;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import io.github.angrylid.mall.api.annotation.AdminController;
import io.github.angrylid.mall.dto.CustomResponse;
import io.github.angrylid.mall.jwt.annotation.AdminRequired;
import io.github.angrylid.mall.service.AdminService;

@AdminController("/static")
public class StatisticApi {

    private AdminService adminService;

    @Autowired
    public StatisticApi(AdminService adminService) {
        this.adminService = adminService;
    }

    /**
     * 查询统计信息
     * 
     * 
     * @param token
     * @return
     */
    @AdminRequired
    @GetMapping()
    public CustomResponse<?> getStatics() {
        Map<String, Long> map = adminService.selectSum();
        return CustomResponse.success(map);
    }

}
