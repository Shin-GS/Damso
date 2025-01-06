package com.damso.user.controller.home;

import com.damso.core.response.success.SuccessCode;
import com.damso.core.response.success.SuccessResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeApi {
    @GetMapping("/api/health-check")
    public SuccessResponse healthCheck() {
        return SuccessResponse.of(SuccessCode.SUCCESS);
    }
}
