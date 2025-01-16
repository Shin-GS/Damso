package com.damso.core.response.exception;

import com.damso.core.response.error.ErrorCode;
import com.damso.core.response.error.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@Order(1) // 우선순위 설정: HTMX 요청을 우선 처리
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalHxControllerExceptionHandler {
    @ExceptionHandler(Exception.class)
    public String handleException(HttpServletRequest request,
                                  Exception e,
                                  Model model) throws Exception {
        if ("true".equals(request.getHeader("HX-Request"))) {
            model.addAttribute("message", e.getMessage());

            String fragment = " :: error";
            return "fragments/components/toast" + fragment;
        }

        throw e;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected String handleMethodArgumentNotValidException(HttpServletRequest request,
                                                           MethodArgumentNotValidException e,
                                                           Model model) throws MethodArgumentNotValidException {
        if (!isHxRequest(request)) {
            throw e;
        }

        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.INVALID_INPUT_VALUE, e.getBindingResult());
        model.addAttribute("message", errorResponse.getFirstMessage());

        String fragment = " :: error";
        return "fragments/components/toast" + fragment;
    }

    @ExceptionHandler(BusinessException.class)
    public String handleBusinessException(HttpServletRequest request,
                                          BusinessException e,
                                          Model model) {
        if (isHxRequest(request)) {
            throw e;
        }

        model.addAttribute("message", e.getMessage());

        String fragment = " :: error";
        return "fragments/components/toast" + fragment;
    }

    private boolean isHxRequest(HttpServletRequest request) {
        return "true".equals(request.getHeader("HX-Request"));
    }
}
