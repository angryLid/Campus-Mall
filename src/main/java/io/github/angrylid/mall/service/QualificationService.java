package io.github.angrylid.mall.service;

import java.io.IOException;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import io.github.angrylid.mall.dto.QualificationDto;
import io.github.angrylid.mall.generated.entity.Qualification;
import io.github.angrylid.mall.generated.mapper.QualificationMapper;
import io.github.angrylid.mall.utils.Minio;

@Service
public class QualificationService {
    @Autowired
    private QualificationMapper qualificationMapper;

    @Autowired
    private Minio minio;

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

    /**
     * 检索所有等待审核的请求
     * 
     * @return
     */
    public List<Qualification> selectAllWaiting() {
        QueryWrapper<Qualification> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("current_status", "waiting");
        return qualificationMapper.selectList(queryWrapper);

    }

    /**
     * 插入一条申请或更新一条申请
     * 
     * @param dto         材料
     * @param applicantId 申请人ID
     * @return
     * @throws IOException
     */
    public Boolean insertOne(QualificationDto dto, Integer applicantId) throws IOException {
        var entity = new Qualification();
        entity.setEnterpriseName(dto.getEnterpriseName());
        entity.setEnterpriseType(dto.getEnterpriseType());
        entity.setApplicantId(applicantId);

        for (MultipartFile file = dto.getImage0(); file != null; file = null) {
            String name = minio.upload(file);
            entity.setImage0(name);
        }
        for (MultipartFile file = dto.getImage1(); file != null; file = null) {
            String name = minio.upload(file);
            entity.setImage1(name);
        }
        for (MultipartFile file = dto.getImage2(); file != null; file = null) {
            String name = minio.upload(file);
            entity.setImage2(name);
        }
        for (MultipartFile file = dto.getImage3(); file != null; file = null) {
            String name = minio.upload(file);
            entity.setImage3(name);
        }
        for (MultipartFile file = dto.getImage4(); file != null; file = null) {
            String name = minio.upload(file);
            entity.setImage4(name);
        }
        for (MultipartFile file = dto.getImage5(); file != null; file = null) {
            String name = minio.upload(file);
            entity.setImage5(name);
        }

        Integer row;
        Qualification exsited = selectOne(applicantId);

        if (exsited != null) {
            entity.setId(exsited.getId());
            row = qualificationMapper.updateById(entity);
        } else {
            row = qualificationMapper.insert(entity);
        }

        if (row != 1) {
            return false;
        }
        return true;
    }

    /**
     * 修改一条申请
     * 
     * @param id            主键
     * @param comentary     评论
     * @param currentStatus 状态
     * @return
     */
    public Boolean updateOne(Integer id, String comentary, String currentStatus) {
        Qualification qualification = new Qualification();
        qualification.setId(id);
        qualification.setComentary(comentary);
        qualification.setCurrentStatus(currentStatus);

        Integer row = qualificationMapper.updateById(qualification);
        if (row == 1) {
            return true;
        }
        return false;
    }
}
