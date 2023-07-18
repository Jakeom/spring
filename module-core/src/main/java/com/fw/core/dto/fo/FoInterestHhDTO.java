package com.fw.core.dto.fo;

import com.fw.core.dto.CommonDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FoInterestHhDTO extends CommonDTO {
    
	private String id;		    		// 관심 일련번호
    private String applicantId;         // AP 회원 일련번호
    private String headhunterId;        // HH 회원 일련번호
    private String createdAt;           // 생성일시
    private String updatedAt;          	// 수정일시
    private String delFlag;				// 삭제여부
    private String name;				// 이름
    private String company;				// 회사이름
    private String phone;				// 전화번호
    private String email;				// 이메일
    private String fieldCd;				// 전문분야
    private String fieldCdNm;				// 전문분야
    private String greeting;			// 인사말
    private String progressCnt;         // 헤드헌터 진행중인 공고수 (포지션 상세)
    private List<FoPositionDTO> positionDTOList;
    private String profilePictureFileId;
}
