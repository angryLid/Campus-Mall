package io.github.angrylid.mall.service;

import java.io.IOException;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import io.github.angrylid.mall.dto.request.QualificationDto;
import io.github.angrylid.mall.entity.HandleProcedure;
import io.github.angrylid.mall.generated.entity.Qualification;
import io.github.angrylid.mall.generated.entity.User;
import io.github.angrylid.mall.generated.mapper.QualificationMapper;
import io.github.angrylid.mall.generated.mapper.UserMapper;
import io.github.angrylid.mall.utils.Minio;

@Service
public class QualificationService {
    @Autowired
    private QualificationMapper qualificationMapper;

    @Autowired
    private UserMapper userMapper;

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
        entity.setCurrentStatus(HandleProcedure.WAITING.getValue());
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

        Integer row = qualificationMapper.insert(entity);

        return row == 1;
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
        Qualification qualification = qualificationMapper.selectById(id);

        if (currentStatus.equals(HandleProcedure.APPROVED.getValue())) {
            User user = new User();
            System.out.println("applicantID" + qualification.getApplicantId());
            user.setId(qualification.getApplicantId());

            user.setMerchantId(-1);
            userMapper.updateById(user);
        }
        qualification.setComentary(comentary);
        qualification.setCurrentStatus(currentStatus);
        Integer row = qualificationMapper.updateById(qualification);
        return row == 1;
    }

    /**
     * 依据用户信息查找商铺信息
     * 
     * @param id 用户ID
     * @return 关联的商铺信息
     */
    public Qualification selectMerchantInfo(Integer userId) {
        QueryWrapper<Qualification> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("applicant_id", userId);
        queryWrapper.orderByDesc("created_at");
        Qualification qualification = qualificationMapper.selectOne(queryWrapper);
        return qualification;
    }

}
