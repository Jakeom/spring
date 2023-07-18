package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;

import lombok.Data;

@Data
public class BoCommonCdDTO extends CommonDTO {

	private BoAdminSessionDTO adminSession;

	private String groupCd;
	private String cd;
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
}