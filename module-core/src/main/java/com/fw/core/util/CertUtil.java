package com.fw.core.util;

import com.icert.comm.secu.IcertSecuManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class CertUtil {

    private static String CP_ID;
    private static String URL_CODE;

    @Value("${cert.cp-id}")
    public void setCpId(String cpId) {
        CertUtil.CP_ID = cpId;
    }

    @Value("${cert.url-code}")
    public void setUrlCode(String urlCode) {
        CertUtil.URL_CODE = urlCode;
    }

    /**
     * 본인인증을 위한 Cert키 생성
     * @return String
     */
    public static String getCert(){
        String now = DateUtil.getDtStrNow("yyyyMMddHHmmss");
        String randomStr = RandomUtil.createRandomText(6);
        String certMet = "M";
        String extendVar = "0000000000000000";
        String trCert = CP_ID + "/" + URL_CODE + "/" + randomStr + "/" + now + "/" + certMet + "////////" + extendVar;

        IcertSecuManager seed = new IcertSecuManager();                                      // 암호화 모듈 선언
        String encTrCert = seed.getEnc(trCert, "");                                       // 1차 암호화
        String hmacMsg = seed.getMsg(encTrCert);                                             // 위변조 검증값 생성
        trCert = seed.getEnc(encTrCert + "/" + hmacMsg + "/" + extendVar, "");          // 2차 암호화
        return trCert;
    }

    /**
     * 본인인증 결과 Parsing
     * @return
     */
    public static Map<String, Object> getCertResult(String recCert, String certNum){
        Map<String, Object> resMap = new HashMap<>();

        IcertSecuManager seed = new IcertSecuManager();                                      // 복호화 모듈 선언
        String decCert = seed.getDec(recCert, certNum);                                      // 복호화
        int inf1 = decCert.indexOf("/", 0);
        int inf2 = decCert.indexOf("/", inf1 + 1);

        String encPara  = decCert.substring(0, inf1);                                        //암호화된 통합 파라미터
        String encMsg1  = decCert.substring(inf1 + 1, inf2);                                 //암호화된 통합 파라미터의 Hash값
        String encMsg2 = seed.getMsg(encPara);

        String msgChk = "N";
        if(StringUtils.equals(encMsg1, encMsg2)){
            msgChk = "Y";
            String decCert2nd = seed.getDec(encPara, certNum);
            int info1  = decCert2nd.indexOf("/", 0);
            int info2  = decCert2nd.indexOf("/", info1 + 1);
            int info3  = decCert2nd.indexOf("/", info2 + 1);
            int info4  = decCert2nd.indexOf("/", info3 + 1);
            int info5  = decCert2nd.indexOf("/", info4 + 1);
            int info6  = decCert2nd.indexOf("/", info5 + 1);
            int info7  = decCert2nd.indexOf("/", info6 + 1);
            int info8  = decCert2nd.indexOf("/", info7 + 1);
            int info9  = decCert2nd.indexOf("/", info8 + 1);
            int info10 = decCert2nd.indexOf("/", info9 + 1);
            int info11 = decCert2nd.indexOf("/", info10 + 1);
            int info12 = decCert2nd.indexOf("/", info11 + 1);
            int info13 = decCert2nd.indexOf("/", info12 + 1);
            int info14 = decCert2nd.indexOf("/", info13 + 1);
            int info15 = decCert2nd.indexOf("/", info14 + 1);
            int info16 = decCert2nd.indexOf("/", info15 + 1);
            int info17 = decCert2nd.indexOf("/", info16 + 1);
            int info18 = decCert2nd.indexOf("/", info17 + 1);

            String cNum		    = decCert2nd.substring(0, info1);
            String date		    = decCert2nd.substring(info1 + 1, info2);
            String ci			= decCert2nd.substring(info2 + 1, info3);
            String phoneNo		= decCert2nd.substring(info3 + 1, info4);
            String phoneCorp	= decCert2nd.substring(info4 + 1, info5);
            String birthDay	    = decCert2nd.substring(info5 + 1, info6);
            String gender		= decCert2nd.substring(info6 + 1, info7);
            String nation		= decCert2nd.substring(info7 + 1, info8);
            String name		    = decCert2nd.substring(info8 + 1, info9);
            String result		= decCert2nd.substring(info9 + 1, info10);
            String certMet		= decCert2nd.substring(info10 + 1, info11);
            String ip			= decCert2nd.substring(info11 + 1, info12);
            String plusInfo	    = decCert2nd.substring(info16 + 1, info17);
            String di       	= decCert2nd.substring(info17 + 1, info18);

            String decCi  = seed.getDec(ci, certNum);
            String decDi  = seed.getDec(di, certNum);

            resMap.put("cNum", cNum);
            resMap.put("date", date);
            resMap.put("ci", ci);
            resMap.put("phoneN", phoneNo);
            resMap.put("phoneCo", phoneCorp);
            resMap.put("birthDa", birthDay);
            resMap.put("gende", gender);
            resMap.put("natio", nation);
            resMap.put("name", name);
            resMap.put("resul", result);
            resMap.put("certMe", certMet);
            resMap.put("ip", ip);
            resMap.put("plusInf", plusInfo);
            resMap.put("di", di);
        }
        resMap.put("result", msgChk);
        return resMap;
    }

}
