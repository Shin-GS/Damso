package com.damso.user.controller.api;

import com.damso.common.response.SuccessResponse;
import com.damso.core.code.SuccessCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeApi {
    @GetMapping("/api/health-check")
    public SuccessResponse<Object> healthCheck() {
        return SuccessResponse.of(SuccessCode.SUCCESS);
    }
}
