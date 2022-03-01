package io.github.angrylid.mall.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.angrylid.mall.dto.response.StatisticDTO;
import io.github.angrylid.mall.entity.HandleProcedure;
import io.github.angrylid.mall.generated.entity.Admin;
import io.github.angrylid.mall.generated.entity.Qualification;
import io.github.angrylid.mall.generated.mapper.AdminMapper;
import io.github.angrylid.mall.generated.mapper.ProductMapper;
import io.github.angrylid.mall.generated.mapper.QualificationMapper;
import io.github.angrylid.mall.generated.mapper.TradeOrderMapper;
import io.github.angrylid.mall.generated.mapper.UserMapper;
import io.github.angrylid.mall.jwt.JwtUtil;

@Service
public class AdminService {

    private AdminMapper adminMapper;
    private ProductMapper productMapper;
    private UserMapper userMapper;
    private QualificationMapper qualificationMapper;
    private TradeOrderMapper tradeOrderMapper;
    private JwtUtil jwtUtil;

    @Autowired
    public AdminService(AdminMapper adminMapper, ProductMapper productMapper, UserMapper userMapper,
            QualificationMapper qualificationMapper, TradeOrderMapper tradeOrderMapper, JwtUtil jwtUtil) {
        this.adminMapper = adminMapper;
        this.productMapper = productMapper;
        this.userMapper = userMapper;
        this.qualificationMapper = qualificationMapper;
        this.tradeOrderMapper = tradeOrderMapper;
        this.jwtUtil = jwtUtil;
    }

    /**
     * 根据用户名,密码查询管理员
     * 
     * @param name
     * @param password
     * @return
     */
    public Admin selectAdmin(String name, String password) {
        return adminMapper.selectOne(new QueryWrapper<Admin>().eq("name", name).eq("password", password));
    }

    /**
     * 签发Token
     * 
     * @param name
     * @param password
     * @return
     * @throws Exception
     */
    public String generateToken(String name, String password) throws Exception {

        Admin admin = selectAdmin(name, password);
        if (admin == null) {
            throw new IllegalArgumentException("找不到用户");
        }
        return jwtUtil.signAdmin(name, password);

    }

    /**
     * 检索数据量
     */
    public StatisticDTO selectSum() {
        Long user = userMapper.selectCount(new QueryWrapper<>());
        Long product = productMapper.selectCount(new QueryWrapper<>());
        Long merchant = qualificationMapper
                .selectCount(
                        new QueryWrapper<Qualification>().eq("current_status", HandleProcedure.APPROVED.getValue()));

        Long order = tradeOrderMapper.selectCount(new QueryWrapper<>());

        return new StatisticDTO(user, product, merchant, order);
    }

}
