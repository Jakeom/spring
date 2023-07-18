package com.fw.core.util;

import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
@Component
public class RestTemplateUtil {

    private static RestTemplate restTemplate;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        RestTemplateUtil.restTemplate = restTemplate;
    }

    // GET 방식 호출
    public static Map<String, Object> get(String url, HttpHeaders headers){
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Map<String, Object>> respString = restTemplate.exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<Map<String, Object>>() {});

        log.info("Send url - {}", url);
        log.info("Response Status - {}", respString.getStatusCode());
        log.info("Response Body - {}", respString.getBody());

        return respString.getBody();
    }

    // POST 방식 호출
    public static Map<String, Object> post(String url, HttpHeaders headers, Map<String, String> paramMap){
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.setAll(paramMap);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(multiValueMap, headers);
        ResponseEntity<Map<String, Object>> respString = restTemplate.exchange(url, HttpMethod.POST, entity, new ParameterizedTypeReference<Map<String, Object>>() {});

        log.info("Send url - {}", url);
        log.info("Response Status - {}", respString.getStatusCode());
        log.info("Response Body - {}", respString.getBody());

        return respString.getBody();
    }

    // POST JSON 호출
    public static Map<String, Object> post(String url, HttpHeaders headers, JSONObject jsonObject){
        HttpEntity<String> entity = new HttpEntity<>(jsonObject.toString(), headers);
        ResponseEntity<Map<String, Object>> respString = restTemplate.exchange(url, HttpMethod.POST, entity, new ParameterizedTypeReference<Map<String, Object>>() {});

        log.info("Send url - {}", url);
        log.info("Response Status - {}", respString.getStatusCode());
        log.info("Response Body - {}", respString.getBody());

        return respString.getBody();
    }

}
