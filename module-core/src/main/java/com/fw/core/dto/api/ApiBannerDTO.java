package com.fw.core.dto.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fw.core.dto.CommonDTO;

import lombok.Getter;
import lombok.Setter;

/**
 * 배너 DTO
 * 
 * @author shwoo
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiBannerDTO extends CommonDTO {

	/** 배너 seq */
	private String bannerSeq;
	/** 배너 종류 코드 */
	private String bannerTypeCd;
	/** 배너 종류 */
	private String bannerType;
	/** 배너 이름 */
	private String bannerNm;
	/** 내용 */
	private String content;
	/** 배너 순서 */
	private String bannerOrder;
	/** 사용여부 */
	private String useFlag;
	/** 삭제여부 */
	private String delFlag;
	/** 배너 시작일자 */
	private String bannerStartDt;
	/** 배너 종료일자 */
	private String bannerEndDt;
	/** 링크 url */
	private String linkUrl;
	/** 생성자 번호 */
	private String createSeq;
	/** 생성 일시 */
	private String createDt;
	/** 수정자 번호 */
	private String updateSeq;
	/** 수정 일시 */
	private String updateDt;
	/** url */
	private String url;

}