package com.fw.core.dto.bo;

import java.util.List;

import com.fw.core.dto.CommonDTO;

import lombok.Getter;
import lombok.Setter;

/**
 * 그룹 DTO
 * 
 * @author shwoo
 */
@Getter
@Setter
public class BoGroupDTO extends CommonDTO {

	private String groupId;			/** 그룹 아이디 */
	private String groupNm;			/** 그룹명 */
	private String useFlag;			/** 사용여부 */
	private String createSeq;		/** 생성자 번호 */
	private String createDt;		/** 생성 일시 */
	private String updateSeq;		/** 수정자 번호 */
	private String updateDt;		/** 수정 일시 */
	private String delFlag;			/** 삭제여부 */
	private String writer;			/** 작성자 */
	private String corrector;		/** 수정자 */	
	
	private List<BoGroupDTO> GroupList;

}