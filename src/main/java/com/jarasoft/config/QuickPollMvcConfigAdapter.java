package com.jarasoft.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class QuickPollMvcConfigAdapter implements WebMvcConfigurer {
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> handlerMethodArgumentResolvers){
        PageableHandlerMethodArgumentResolver phmar = new PageableHandlerMethodArgumentResolver();
        phmar.setFallbackPageable(PageRequest.of(0, 5));
        handlerMethodArgumentResolvers.add(phmar);
    }
}
