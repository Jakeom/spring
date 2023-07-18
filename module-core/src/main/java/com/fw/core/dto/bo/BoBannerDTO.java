package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 배너 DTO
 *
 * @author shwoo
 */
@Getter
@Setter
public class BoBannerDTO extends CommonDTO {

	private String bannerSeq;			/** 배너 seq */
	private String bannerTypeCd;		/** 배너 종류 CD */
	private String bannerType;		    /** 배너 종류 */
	private String bannerNm;			/** 배너 이름 */
	private String bannerOrder;			/** 배너 순서 */
	private String useFlag;				/** 사용여부 */
	private String delFlag;				/** 삭제여부 */
	private String bannerStartDt;		/** 배너 시작일자 */
	private String bannerEndDt;			/** 배너 종료일자 */
	private String linkUrl;				/** 링크 url */
	private String createSeq;			/** 생성자 번호 */
	private String createDt;			/** 생성 일시 */
	private String updateSeq;			/** 수정자 번호 */
	private String updateDt;			/** 수정 일시 */
	private String corrector;			/** 수정자 */
	private String writer;				/** 작성자 */
	private String fileSeq;				/** file Seq */

	/* file_mgr */
	private String originName;			// 원래 파일명
	private String url;					// s3 URL

	private List<BoBannerDTO> BannerList;

	/** 파일 리스트*/
	private MultipartFile[] bannerFiles;
}