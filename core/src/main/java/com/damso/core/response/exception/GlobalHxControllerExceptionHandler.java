package com.damso.core.response.exception;

import com.damso.core.response.error.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Slf4j
@Order(1) // 우선순위 설정: HTMX 요청을 우선 처리
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalHxControllerExceptionHandler {
    private final TemplateEngine templateEngine;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleHtmxExceptions(HttpServletRequest request, Exception e) throws Exception {
        boolean isHtmx = "true".equals(request.getHeader("HX-Request"));
        if (!isHtmx) {
            throw e; // HTMX 요청이 아니면 다른 핸들러로 위임
        }

        String requestPath = request.getRequestURI();

        // 요청 경로별 분기 처리
        if (requestPath.matches("^/hx/stories/\\d+/edit/.*$")) {
            return renderThymeleafTemplate("fragments/storyNotFound", "스토리를 찾을 수 없습니다.", requestPath);
        }

        // 기본 HTMX 오류 처리
        return renderThymeleafTemplate("fragments/genericError", "알 수 없는 오류가 발생했습니다.", requestPath);
    }

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ErrorResponse> handleBusinessException(BusinessException e) {
        log.error("handle BusinessException: ", e);
        ErrorResponse errorResponse = StringUtils.hasText(e.getMessage()) ? ErrorResponse.of(e.getCode(), e.getMessage()) : ErrorResponse.of(e.getCode());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
        return new ResponseEntity<>(errorResponse, headers, HttpStatus.valueOf(errorResponse.getStatus()));
    }

    // 공통 Thymeleaf 템플릿 렌더링
    private ResponseEntity<String> renderThymeleafTemplate(String templateName, String message, String path) {
        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("path", path);

        String renderedHtml = templateEngine.process(templateName, context);
        return ResponseEntity.status(500).body(renderedHtml);
    }
}
