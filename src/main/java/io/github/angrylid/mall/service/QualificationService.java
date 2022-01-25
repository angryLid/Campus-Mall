package io.github.angrylid.mall.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.angrylid.mall.generated.entity.Qualification;
import io.github.angrylid.mall.generated.mapper.QualificationMapper;

@Service
public class QualificationService {
    @Autowired
    private QualificationMapper qualificationMapper;

    /**
     * 检索一条审批请求
     * 
     * @param applicantId 申请人ID
     * @return
     */
    public Qualification selectOne(Integer applicantId) {
        QueryWrapper<Qualification> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("applicant_id", applicantId);
        return qualificationMapper.selectOne(queryWrapper);
    }
}
