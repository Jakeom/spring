package com.fw.fo.token.service;

import com.fw.core.dto.MailDTO;
import com.fw.core.dto.fo.FoTokenDTO;
import com.fw.core.mapper.db1.fo.FoTokenMapper;
import com.fw.core.util.DateUtil;
import com.fw.core.util.MailUtil;
import com.fw.core.util.RandomUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class FoTokenService {

    private final FoTokenMapper foTokenMapper;

    public void insertToken(FoTokenDTO foTokenDTO){
        String token = RandomUtil.createRandomText(6);     // 난수생성
        foTokenDTO.setToken(token);
        foTokenMapper.insertToken(foTokenDTO);

        if(StringUtils.equals("SMS", foTokenDTO.getMediaType())){

        } else if (StringUtils.equals("EMAIL", foTokenDTO.getMediaType())){
            Map<String, Object> map = new HashMap<>();
            map.put("certCode", token);
            MailUtil.sendTemplateEmail(MailDTO.builder().fromEmail("resume9@resume9.co.kr")
                                                .receiveEmail(Arrays.asList(foTokenDTO.getAuth()))
                                                .template("certCode")
                                                .templateData(map).build());
        }

        log.info("Verify Code : {}", token);
    }

    public FoTokenDTO selectToken(FoTokenDTO foTokenDTO){
        FoTokenDTO resDTO = foTokenMapper.selectToken(foTokenDTO);

        String serviceCode = "N";
        String serviceMessage = null;

        // 인증코드 검증은 4가지 경우가 있음 - 정상, 잘못된 인증코드, 인증 시간 만료, 이미 사용한 코드
        if(!Objects.isNull(resDTO)) {
            if (DateUtil.isDtAfter(resDTO.getExpiredAt(), DateUtil.getDtStrNow("yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss")) {
                if(StringUtils.equals("1", resDTO.getIsUsed())){
                    serviceMessage = "이미 사용한 코드입니다.";
                } else {
                    this.updateToken(foTokenDTO);
                    serviceCode = "Y";
                    serviceMessage = "인증이 완료되었습니다.";
                }
            } else {
                serviceMessage = "인증시간이 만료되었습니다.";
            }
        } else {
            resDTO = new FoTokenDTO();
            serviceMessage = "인증코드가 잘못 되었습니다.";
        }

        resDTO.setServiceCode(serviceCode);
        resDTO.setServiceMessage(serviceMessage);

        return resDTO;
    }

    public void updateToken(FoTokenDTO foTokenDTO){
        foTokenMapper.updateToken(foTokenDTO);
    }

}