package com.yapp.ios1.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * created by jg 2021/06/02
 */
@RestController
public class HealthController {

    // 로드 밸런서 용
    @ApiIgnore
    @GetMapping("/health")
    public String loadBalancer() {
        return "health";
    }
}
