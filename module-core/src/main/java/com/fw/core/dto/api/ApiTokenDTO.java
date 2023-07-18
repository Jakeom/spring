package com.fw.core.dto.api;
import com.fw.core.dto.CommonDTO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * token DTO
 *
 * @author WSH
 */
@Getter
@Setter
public class ApiTokenDTO extends CommonDTO implements Serializable {

    private static final long serialVersionUID = 1L;

	private String id;         	  	// 토큰 일련번호
	private String createdAt;     	// 생성일시
    private String updatedAt;     	// 수정일시
    private String auth;  	  		// 인증값(메일주소, 휴대폰번호)
    private String expiredAt;		// 코드만료시간
    private String isConfirmed;		// 확인여부
    private String isUsed;			// 사용여부
    private String mediaType; 	  	// SMS, EMAIL 구분
    private String token; 	  		// 인증코드
    private String useType; 	  	// 사용 구분

    private String delFlag;
    private String serviceCode;
    private String serviceMessage;
}
