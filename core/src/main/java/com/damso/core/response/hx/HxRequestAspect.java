package com.damso.core.response.hx;

import com.damso.core.response.hx.response.FragmentResponse;
import com.damso.core.response.hx.response.FragmentResponseBuilder;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.List;
import java.util.Optional;

@Aspect
@Component
public class HxRequestAspect {
//    @Around("@annotation(HxPostMapping)")
//    public Object handleHxRequest(ProceedingJoinPoint joinPoint) throws Throwable {
//        // HxRequest 확인
//        HttpServletRequest request = Optional.ofNullable(RequestContextHolder.getRequestAttributes())
//                .filter(ServletRequestAttributes.class::isInstance)
//                .map(ServletRequestAttributes.class::cast)
//                .map(ServletRequestAttributes::getRequest)
//                .orElse(null);
//
//        // HxRequest 아닌 경우 원래 동작 수행
//        if (ObjectUtils.isEmpty(request) || !"true".equalsIgnoreCase(request.getHeader("HX-Request"))) {
//            return joinPoint.proceed();
//        }
//
//        // 원래 메서드 실행
//        Object result = joinPoint.proceed();
//
//        // 결과를 FragmentResponse 리스트로 변환
//        List<FragmentResponse> fragments = parseFragments(result);
//
//        // HTML 렌더링
////        return fragments.stream()
////                .map(this::renderFragment)
////                .reduce(new StringBuilder(), StringBuilder::append, StringBuilder::append)
////                .toString();
////
////        return fragments.stream()
////                .findFirst()
////                .map(this::renderFragment)
////                .orElse("");
//    }

    private List<FragmentResponse> parseFragments(Object result) {
        if (result instanceof String strResult) {
            return FragmentResponseBuilder.parseFromString(strResult);
        }

        return List.of();
    }


}
