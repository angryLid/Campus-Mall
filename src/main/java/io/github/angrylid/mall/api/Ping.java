package io.github.angrylid.mall.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "测试连通性")
@RestController
@RequestMapping(path = "/ping")
public class Ping {
    @ApiOperation("测试连通")
    @GetMapping("/")
    public String ping() {
        return "<h1>Ping</h1>";
    }
}
