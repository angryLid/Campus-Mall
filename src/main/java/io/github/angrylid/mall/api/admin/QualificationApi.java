package io.github.angrylid.mall.api.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.angrylid.mall.dto.CustomResponse;
import io.github.angrylid.mall.generated.entity.Qualification;
import io.github.angrylid.mall.service.QualificationService;

/**
 * 管理后台审核商家资质
 */
@RestController
@RequestMapping("/admin/qualification")
public class QualificationApi {

    @Autowired
    private QualificationService qualificationService;

    /**
     * 获取所有等待审核的申请
     * 
     * @return
     */
    @GetMapping("/")
    public CustomResponse<List<Qualification>> getAll() {
        List<Qualification> qualifications = qualificationService.selectAllWaiting();
        return CustomResponse.success(qualifications);
    }

    /**
     * 通过或者驳回一条申请
     * 
     * @param id         申请ID
     * @param option     操作,通过或驳回
     * @param commentary 驳回理由
     * @return
     */
    @PutMapping("/{id}")
    public CustomResponse<Boolean> putOne(@PathVariable("id") Integer id,
            @RequestParam("option") String option,
            @RequestParam("commentary") String commentary) {
        Boolean result = qualificationService.updateOne(id, commentary, option);
        return CustomResponse.success(result);
    }

}
