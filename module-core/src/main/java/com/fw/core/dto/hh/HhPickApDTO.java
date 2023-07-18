package com.fw.core.dto.hh;

import com.fw.core.dto.CommonDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HhPickApDTO extends CommonDTO {
    
	private String id;                	    // 히스토리 일련번호
    private String resumeId;                // 이력서 일련번호
    private String memberId;                // 회원 일련번호
    private String email;       		    // 이메일
    private boolean isTemp;       		    // 임시회원여부
    private String loginId;          	    // 로그인 ID
    private String name;            	    // 이름
    private String birth;                   // 생년월일
    private String birthNm;                 // 년도+나이+성별
    private String gender;                  // 성별
    private String age;                     // 나이 
    private String phone;          	        // 휴대폰 번호
    private String profilePictureFileId;    // 프로필사진 file id
    private String registerPathCd;		    // 등록방식코드
    private String registerPathNm;		    // 등록방식명
    private String turnorverCnt;		    // 이직횟수
    private String updateAt;                // 업데이트일 
    private String expireAt;                // 열람만료일
    private String isExpired;               // 연람만료 여부
    private String groupId;                 // 그룹코드
    private String groupNm;                 // 그룹명
    private String finalSchool;             // 최종학력
    private String finalCompany;            // 최종회사
    private String longestCompany;                     // 최장회사
    private String totalCareer;             // 경력
    private String totalCareerNm;             // 경력
    private String url; //  이미지 URL
    private String turnoverCnt;                // 이직 횟수
    private String groupIdPop;
    private String opened;  //                  // 공개여부           0 공개 , 1 비공개 
    private String firstOpenChanged;            // 첫번째 공개 여부    0이면 처음 공개
    private String dDay;
    private String birthYear;
    private String locationCd;
    private String regDate;
    private String myApFlag;
    
    private String resumeUrl;
}
