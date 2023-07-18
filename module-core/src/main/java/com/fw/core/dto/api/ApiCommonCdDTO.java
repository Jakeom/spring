package com.fw.core.dto.api;

import com.fw.core.dto.CommonDTO;

import lombok.Data;

@Data
public class ApiCommonCdDTO extends CommonDTO {

	private String groupCd;
	private String cd;
	private String name;
	private String groupNm;
	private String upperCd;
	private String cdNm;
	private String cdOrder;
	private String cdDesc;
	private String cdLevel;
	private String useFlag;
	private String delFlag;
	private String createSeq;
	private String createDt;
	private String updateSeq;
	private String updateDt;
	private String cdReplace1;
	private String cdReplace2;

	// parameter
	private String memberId;	// 회원 일련번호
	private String userType;	// 회원종류(AP/HH)
}