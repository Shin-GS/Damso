package com.damso.admin.controller.home;

import com.damso.core.code.SuccessCode;
import com.damso.common.response.SuccessResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeApi {
    @GetMapping("/api/health-check")
    public SuccessResponse healthCheck() {
        return SuccessResponse.of(SuccessCode.SUCCESS);
    }
}
