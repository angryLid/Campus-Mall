package io.github.angrylid.mall.api.admin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.angrylid.mall.dto.QualificationDto;

/**
 * 管理后台审核商家资质
 */
@RestController
@RequestMapping("/admin/qualification")
public class QualificationApi {

    @GetMapping("/")
    public String getAll() {
        return "";
    }

    @PutMapping("/{id}")
    public String putOne(@PathVariable("id") Integer id,
            @RequestBody QualificationDto businessQualificationDto) {
        return "";
    }

}
