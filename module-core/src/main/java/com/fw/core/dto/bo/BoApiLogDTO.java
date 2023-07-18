package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * API 로그 DTO
 *
 * @author YJW
 */
@Getter
@Setter
public class BoApiLogDTO extends CommonDTO {

    private String apiLogSeq;       // API 로그 SEQ
    private String profile;         // 프로필
    private String host;            // 호스트
    private String url;             // url
    private String requestHeader;   // 요청헤더
    private String parameter;       // 파라미터
    private String result;          // 결과
    private String accessIp;        // 접근 IP
    private String processTime;     // 처리시간
    private String createDt;        // 생성일시
    private String queryString;     // 쿼리스트링
    private String requestBody;     // 응답문
    private String errorLog;        // 에러로그
    private int count;              // 카운트 (group by)
}
