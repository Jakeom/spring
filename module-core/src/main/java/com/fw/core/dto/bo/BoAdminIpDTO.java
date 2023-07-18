package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;

import lombok.Getter;
import lombok.Setter;

/**
 * 관리자 IP DTO
 * @author Ghazal
 */
@Getter
@Setter
public class BoAdminIpDTO extends CommonDTO {

	private String adminIpSeq;			// 사용자 IP 순번
	private String adminSeq; 			// 사용자 순번
	private String ip;					// 아이피
	private String delFlag;				// 삭제 여부
	private String createSeq;			// 생성자 순번
	private String createDt;			// 생성 일시

}
