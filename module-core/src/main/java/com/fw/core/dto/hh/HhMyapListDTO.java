package com.fw.core.dto.hh;

import java.util.List;

import com.fw.core.dto.CommonDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HhMyapListDTO extends CommonDTO {
    private String isMyap;  //  내 인재 구분
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
    private String mainFinalSchool;             // 최종학력
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
    private String mainLocation;
    
    private String lastLoginAt;
    private String specs;

    private String apAlertSeq;
    
    // 인재관리 검색조건
    //private String name;// 이름 검색필드
    //private String finalCompany; // 최종회사 검색필드
    //private String registerPathCd; // 등록방식코드
    private String careerSt; // 경력시작
	private String careerEn; // 경력종료
	private String ageSt; // 연령시작
	private String ageEn; // 연령종료
    private String salarySt;//희망연봉 최소
    private String salaryEn;//희망연봉 최대
    private String edu1;    // 학력
    private String edu2;    // 학력2
    private String[] edu2Arr; // 학력2 검색 배열
    private String language; // 외국어
    private String license; // 자격증
    private String company; // 출신회사
    private String workplace; // 희망근무지
    private String[] workplaceArr; // 희망근무지 배열
    private String degreeCd; // 학력
    private String companyName; // 출신회사

    private String phoneType; // ANDROID or IOS
    private String pushToken; // push 토큰
    private String startFlag; // 시작 방법
    private String inAnhour; // 한시간 이내조건



    private String keyword; // 키워드 검색필드
    private String[] keywordArr;
    private String coreAbility;
    private String resumeRestricted;


    //체크 필드
    private String[] checkFieldArr;
    private String checkedFields;

}
