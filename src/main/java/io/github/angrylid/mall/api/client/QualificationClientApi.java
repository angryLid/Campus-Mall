package io.github.angrylid.mall.api.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.angrylid.mall.dto.CustomResponse;
import io.github.angrylid.mall.generated.entity.Qualification;
import io.github.angrylid.mall.jwt.annotation.TokenRequired;
import io.github.angrylid.mall.service.QualificationService;

/**
 * 客户提交和修改经营资质
 */
@RestController
@RequestMapping("/client/qualification")
public class QualificationClientApi {

    @Autowired
    private QualificationService qualificationService;

    @TokenRequired
    @GetMapping("/")
    public CustomResponse<Qualification> getMine(@RequestAttribute("id") Integer id) {
        Qualification qualification = qualificationService.selectOne(id);
        return CustomResponse.success(qualification);
    }

    @PutMapping
    public String putMine(@RequestAttribute("id") Integer id) {

        return "";
    }
}
