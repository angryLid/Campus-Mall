package io.github.angrylid.mall.api.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;

import io.github.angrylid.mall.api.annotation.ClientController;
import io.github.angrylid.mall.dto.CustomResponse;
import io.github.angrylid.mall.dto.request.QualificationDto;
import io.github.angrylid.mall.generated.entity.Qualification;
import io.github.angrylid.mall.jwt.annotation.TokenRequired;
import io.github.angrylid.mall.service.QualificationService;

/**
 * 客户提交和修改经营资质
 */
@ClientController("/qualification")
public class QualificationClientApi {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private QualificationService qualificationService;

    /**
     * 查看已经提交的申请记录
     * 
     * @param id 申请人ID
     * @return
     */
    @TokenRequired
    @GetMapping("/")
    public CustomResponse<Qualification> getMine(@RequestAttribute("id") Integer id) {
        Qualification qualification = qualificationService.selectOne(id);
        return CustomResponse.success(qualification);
    }

    /**
     * 提交一条认证请求
     * 
     * @param dto 材料表单
     * @param id  申请人ID
     * @return
     */
    @TokenRequired
    @PostMapping(value = "/", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public CustomResponse<String> upload(@ModelAttribute QualificationDto dto,
            @RequestAttribute("id") Integer id) {
        logger.error("image0: {}", dto.getImage0());

        try {
            qualificationService.insertOne(dto, id);
        } catch (Exception ex) {
            return CustomResponse.dbException(ex.getMessage());
        }

        return CustomResponse.success(dto.getEnterpriseName());
    }

    /**
     * 获取用户店铺信息
     * 
     * @param id
     * @return
     */
    @TokenRequired
    @GetMapping("/info")
    public CustomResponse<Qualification> getMerchantInfo(@RequestAttribute("id") Integer id) {
        Qualification qualification = qualificationService.selectMerchantInfo(id);
        return CustomResponse.success(qualification);
    }
}
