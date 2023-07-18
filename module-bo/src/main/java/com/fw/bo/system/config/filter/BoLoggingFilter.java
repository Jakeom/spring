package com.fw.bo.system.config.filter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fw.core.persistence.db1.domain.TbAdminLog;
import com.fw.core.persistence.db1.dto.TbAdminLogRequestDto;
import com.fw.core.persistence.db1.repository.TbAdminLogRepository;
import com.fw.core.util.IpUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
/*
@Slf4j
@RequiredArgsConstructor
@Component
@WebFilter(urlPatterns = "/bo/*")
public class BoLoggingFilter extends OncePerRequestFilter {

	private final TbAdminLogRepository adminLogRepository;
	private final Environment environment;
	private final ObjectMapper mapper;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
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

		log.info("Request Body (요청본문) --- \n {}", request.getParameterMap() == null ? mapper.readTree(wrappingRequest.getContentAsByteArray()) : "");

		log.info("{}", request.getRequestURI());
		if(!StringUtils.equals("/bo/management/complaint/wefirm/headhunter/excelDownload", request.getRequestURI())&&!StringUtils.equals("/bo/service/status/day/downloadExcel", request.getRequestURI())&&!StringUtils.equals("/bo/service/status/month/downloadExcel", request.getRequestURI())){
			log.info("Response Body (응답본문) --- \n : {}",
					(StringUtils.contains(wrappingResponse.getContentType(), "application/") ? mapper.readTree(wrappingResponse.getContentAsByteArray())
							: "text/plain"));


			// Exception 확인
			Exception ex = (Exception) request.getAttribute("error");

			// API 수행시간
			long endTime = System.currentTimeMillis();
			long processTime = endTime - startTime;

			// 헤더 추출
			Map<String, Object> headerMap = new HashMap<>();
			Enumeration<String> headerNames = request.getHeaderNames();
			while (headerNames.hasMoreElements()) {
				String headerName = headerNames.nextElement();
				headerMap.put(headerName, request.getHeader(headerName));
			}

			TbAdminLog adminLog = TbAdminLogRequestDto.builder().createDt(LocalDateTime.now()).accessIp(IpUtil.getClientIp())
					.host(request.getRemoteHost()).requestHeader(headerMap.toString()).result(new String(wrappingResponse.getContentAsByteArray()))
					.parameter(parameter.toString()).queryString(queryString).parameter(new String(wrappingRequest.getContentAsByteArray()))
					.errorLog(ex == null ? null : ExceptionUtils.getStackTrace(ex)).processTime(processTime)
					.profile(Arrays.toString(environment.getActiveProfiles())).url(request.getRequestURL().toString()).build().toEntity();
			adminLogRepository.save(adminLog);
		}

		wrappingResponse.copyBodyToResponse();
	}

}
*/