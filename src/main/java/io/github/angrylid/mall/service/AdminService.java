package io.github.angrylid.mall.service;

import static java.util.Map.entry;
import static java.util.Map.ofEntries;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.angrylid.mall.entity.HandleProcedure;
import io.github.angrylid.mall.generated.entity.Admin;
import io.github.angrylid.mall.generated.entity.Qualification;
import io.github.angrylid.mall.generated.mapper.AdminMapper;
import io.github.angrylid.mall.generated.mapper.ProductMapper;
import io.github.angrylid.mall.generated.mapper.QualificationMapper;
import io.github.angrylid.mall.generated.mapper.UserMapper;
import io.github.angrylid.mall.jwt.JwtUtil;

@Service
public class AdminService {

    private AdminMapper adminMapper;

    private ProductMapper productMapper;

    private UserMapper userMapper;

    private QualificationMapper qualificationMapper;

    @Autowired
    public AdminService(AdminMapper adminMapper, ProductMapper productMapper, UserMapper userMapper,
            QualificationMapper qualificationMapper) {
        this.adminMapper = adminMapper;
        this.productMapper = productMapper;
        this.userMapper = userMapper;
        this.qualificationMapper = qualificationMapper;
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
        return JwtUtil.signAdmin(name, password);

    }

    /**
     * 检索数据量
     */
    public Map<String, Long> selectSum() {
        Long user = userMapper.selectCount(new QueryWrapper<>());
        Long product = productMapper.selectCount(new QueryWrapper<>());
        Long merchant = qualificationMapper
                .selectCount(
                        new QueryWrapper<Qualification>().eq("current_status", HandleProcedure.APPROVED.getStatus()));
        return ofEntries(
                entry("user", user),
                entry("product", product),
                entry("merchant", merchant),
                entry("order", 99L));
    }

}
