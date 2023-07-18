package com.fw.core.dto.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fw.core.dto.CommonDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * 정보관리 DTO
 * 
 * @author shwoo
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiInfoDTO extends CommonDTO {
		
	private String mainDisplaySeq;		    // 메인노출 순서
	private String infoType;		        // 노출종류
	private String infoTypeCd;				// 노출종류코드
	private String content;		            // 컨텐츠
	private String fileSeq;		            // 파일 순서
	private String useFlag;		            // 사용 여부
	private String delFlag;		            // 삭제 여부
	private String regSeq;		            // 생성 번호
	private String regDate;		            // 생성 일시
}