package com.fw.bo.system.config;

import com.fw.bo.system.config.interceptor.BoMenuInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * BO WebMvc Config
 * @author sjpaik
 */
@Configuration
@RequiredArgsConstructor
public class BoWebMvcConfig implements WebMvcConfigurer {

    @Bean
    public BoMenuInterceptor boMenuInterceptor(){
        return new BoMenuInterceptor();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(boMenuInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/bo/login/**")
                .excludePathPatterns("/static/**");
    }

}
