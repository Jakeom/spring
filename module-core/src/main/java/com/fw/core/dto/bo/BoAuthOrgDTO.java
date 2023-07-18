package com.fw.core.dto.bo;

import java.util.List;

import com.fw.core.dto.CommonDTO;

import lombok.Getter;
import lombok.Setter;

/**
 * 조직 권한 관리 DTO
 * 
 * @author shwoo
 */
@Getter
@Setter
public class BoAuthOrgDTO extends CommonDTO {

	private String orgId; 					// 조직 ID
	private String menuCd; 					// 메뉴 코드
	private String programCd; 				// 프로그랩 코드
	private String createSeq; 				// 생성자 번호
	private String createDt; 				// 생성 일시

	private List<BoAuthOrgDTO> authList; 	// 권한 등록 리스트

}
