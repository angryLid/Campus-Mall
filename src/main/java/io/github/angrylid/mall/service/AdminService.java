package io.github.angrylid.mall.service;

import static java.util.Map.entry;
import static java.util.Map.ofEntries;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.angrylid.mall.generated.entity.Admin;
import io.github.angrylid.mall.generated.mapper.AdminMapper;
import io.github.angrylid.mall.jwt.JwtUtil;

@Service
public class AdminService {
    @Autowired
    private AdminMapper adminMapper;

    /**
     * 签发Token
     * 
     * @param name
     * @param password
     * @return
     * @throws Exception
     */
    public String generateToken(String name, String password) throws Exception {

        List<Admin> result = adminMapper.selectByMap(ofEntries(
                entry("name", name),
                entry("password", password)));
        if (result.size() != 1) {
            throw new Exception("找不到用户");
        }
        return JwtUtil.signAdmin(name, password);

    }

}
