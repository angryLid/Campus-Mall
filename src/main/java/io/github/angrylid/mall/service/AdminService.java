package io.github.angrylid.mall.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.angrylid.mall.generated.entity.Qualification;
import io.github.angrylid.mall.generated.mapper.QualificationMapper;

@Service
public class AdminService {
    @Autowired
    private QualificationMapper qualificationMapper;

    public void selectAll() {
        QueryWrapper<Qualification> queryWrapper = new QueryWrapper<>();

    }

}
