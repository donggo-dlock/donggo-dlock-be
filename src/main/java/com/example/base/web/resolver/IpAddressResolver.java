package com.example.base.web.resolver;

import com.example.base.web.annotation.IpAddress;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


public class IpAddressResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasAnnotation = parameter.hasParameterAnnotation(IpAddress.class);
        boolean hasType = String.class.isAssignableFrom(parameter.getParameterType());
        return hasAnnotation && hasType;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

        String remoteAddr = "";

        if (request.getHeader("X-Forwarded-For") != null) {
            remoteAddr = request.getHeader("X-Forwarded-For").split(",")[0];
        } else {
            remoteAddr = request.getRemoteAddr();
        }

        return remoteAddr;
    }
}
