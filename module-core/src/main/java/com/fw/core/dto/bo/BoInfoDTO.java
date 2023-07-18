package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class BoInfoDTO extends CommonDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 메인노출 순서 */
	private String mainDisplaySeq;
	/** 노출종류 */
	private String infoType;
	/** 컨텐츠 */
	private String content;
	/** 파일 순서 */
	private String fileSeq;
	/** 사용 여부 */
	private String useFlag;
	/** 삭제 여부 */
	private String delFlag;
	/** 생성 번호 */
	private String regSeq;
	/** 생성 일시 */
	private String regDate;

	// 검색 조건
	private String searchStart;
	private String searchEnd;
	private String infoTypeCd;

}
