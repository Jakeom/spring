package com.fw.core.dto.hh;

import com.fw.core.dto.CommonDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HhCompanyListDTO extends CommonDTO {
    
	private String id;                	    // 회원 일련번호
    private String email;       		    // 이메일
    private boolean isTemp;       		    // 임시회원여부
    private String loginId;          	    // 로그인 ID
    private String name;            	    // 이름
    private String phone;          	        // 휴대폰 번호
    private String profilePictureFileId;    // 프로필사진 file id
    private String registerPathCd;		    // 등록방식코드
    private String registerPathNm;		    // 등록방식명
    private String turnorverCnt;		    // 이직횟수
    private String updateAt;                // 업데이트일 
    private String expireAt;                // 열람만료일
    private String groupId;                 // 그룹코드
    private String groupNm;                 // 그룹명
    private String finalSchool;             // 최종학력
    private String finalCompany;            // 최종회사

    // 인재관리 검색조건
    //private String name;// 이름 검색필드
    //private String finalCompany; // 최종회사 검색필드
    private String careerSt; // 경력시작
	private String careerEn; // 경력종료
	private String ageSt; // 연령시작
	private String ageEn; // 연령종료
    private String keyword; // 키워드 검색필드

    //체크 필드
    private String[] checkFieldArr;
    private String checkedFields;

}
