package com.fw.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@EnableBatchProcessing // 배치 기능 활성화
@SpringBootApplication(scanBasePackages = {"com.fw.core", "com.fw.batch"})
public class BatchApplication extends SpringBootServletInitializer {
	public static void main(String[] args) {
		SpringApplication.run(BatchApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(BatchApplication.class);
	}

}
