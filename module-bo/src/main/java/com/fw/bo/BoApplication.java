package com.fw.bo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * BO Application
 * @author sjpaik
 */
@ServletComponentScan
@SpringBootApplication(scanBasePackages = {"com.fw.core", "com.fw.bo"})
public class BoApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(BoApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(BoApplication.class);
    }

}