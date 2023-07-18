package com.fw.api.system.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fw.core.persistence.db1.domain.TbApiLog;
import com.fw.core.persistence.db1.dto.TbApiLogRequestDto;
import com.fw.core.persistence.db1.repository.TbApiLogRepository;
import com.fw.core.util.IpUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApiLoggingFilter extends OncePerRequestFilter {

    private final TbApiLogRepository apiLogRepository;
    private final Environment environment;
    private final ObjectMapper mapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        ContentCachingRequestWrapper wrappingRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappingResponse = new ContentCachingResponseWrapper(response);

        mapper.registerModule(new JavaTimeModule());
        long startTime = System.currentTimeMillis();

        filterChain.doFilter(wrappingRequest, wrappingResponse);

        log.info("Request URL --- \n {}", request.getRequestURL().toString());

        String queryString = request.getQueryString();
        log.info("QueryString (쿼리스트링) --- \n {}", (StringUtils.isBlank(queryString) ? "" : queryString));

        JSONObject parameter = new JSONObject(request.getParameterMap());
        log.info("Parameter (파라미터) --- \n {}", parameter);

        log.info(
                "Request Body (요청본문) --- \n {}",
                mapper.readTree(wrappingRequest.getContentAsByteArray())
        );

        log.info(
                "Response Body (응답본문) --- \n : {}",
                mapper.readTree(wrappingResponse.getContentAsByteArray())
        );

        // Exception 확인
        Exception ex = (Exception) request.getAttribute("error");

        // API 수행시간
        long endTime = System.currentTimeMillis();
        long processTime = endTime - startTime;

        // 헤더 추출
        Map<String, Object> headerMap = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headerMap.put(headerName, request.getHeader(headerName));
        }

        TbApiLog apiLog = TbApiLogRequestDto.builder()
                .createDt(LocalDateTime.now())
                .accessIp(IpUtil.getClientIp())
                .host(request.getRemoteHost())
                .requestHeader(headerMap.toString())
                .result(new String(wrappingResponse.getContentAsByteArray()))
                .parameter(parameter.toString())
                .queryString(queryString)
                .parameter(new String(wrappingRequest.getContentAsByteArray()))
                .errorLog(ex == null ? null : ExceptionUtils.getStackTrace(ex))
                .processTime(processTime)
                .profile(Arrays.toString(environment.getActiveProfiles()))
                .url(request.getRequestURL().toString())
                .build().toEntity();
        apiLogRepository.save(apiLog);

        wrappingResponse.copyBodyToResponse();
    }

}
