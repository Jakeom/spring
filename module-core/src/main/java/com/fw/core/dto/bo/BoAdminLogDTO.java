package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * 관리자 로그 DTO
 *
 * @author YJW
 */
@Getter
@Setter
public class BoAdminLogDTO extends CommonDTO {

    private String adminLogSeq;     // 관리자로그seq
    private String profile;         // 프로필
    private String host;            // 호스트
    private String url;             // url
    private String requestHeader;   // 요청문
    private String errorLog;        // 에러로그
    private String parameter;       // 파라미터
    private String result;          // 결과
    private String accessIp;        // ip
    private String processTime;     // 실행시간
    private String createDt;        // 생성시간
    private String queryString;     // 쿼리스트링
    private String requestBody;     // 응답문
    private int count;              // group by 총합
}
