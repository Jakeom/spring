package com.fw.core.util;

import com.fw.core.dto.fo.MsgDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class SmsUtil {

    private static String ACCOUNT;
    private static String DEFAULT_FROM;
    private static String API_HOST;
    private static String SEND_API;

    @Value("${sms-api.account}")
    public void setACCOUNT(String ACCOUNT) {
        SmsUtil.ACCOUNT = ACCOUNT;
    }

    @Value("${sms-api.from}")
    public void setDefaultFrom(String defaultFrom) {
        DEFAULT_FROM = defaultFrom;
    }

    @Value("${sms-api.host}")
    public void setApiHost(String apiHost) {
        API_HOST = apiHost;
    }

    @Value("${sms-api.send-api}")
    public void setSendApi(String sendApi) {
        SEND_API = sendApi;
    }

    public static void sendMessage(MsgDTO msgDTO){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("account", ACCOUNT);
        jsonObject.put("type", msgDTO.getType());
        jsonObject.put("from", StringUtils.isNotBlank(msgDTO.getFrom()) ? msgDTO.getFrom() : DEFAULT_FROM);
        jsonObject.put("to", msgDTO.getTo());
        jsonObject.put("refKey", UUID.randomUUID().toString().replace("-", ""));

        JSONObject sendMap = new JSONObject();
        if(StringUtils.equals("LMS", msgDTO.getType())) {
            sendMap.put("subject", msgDTO.getSubject());
            sendMap.put("message", msgDTO.getMessage());
            jsonObject.put("lms", sendMap);
        } else {
            sendMap.put("message", msgDTO.getMessage());
            jsonObject.put("sms", sendMap);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        /*RestTemplateUtil.post(API_HOST + SEND_API, headers, jsonObject);*/
    }

}
