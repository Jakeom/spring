package com.fw.core.dto.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fw.core.dto.CommonDTO;
import lombok.*;

/**
 * 회원 DTO
 * @author YJW
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiMemberDTO extends CommonDTO {
    
	private String DTYPE;		            // AP, HH 구분
    private String dtype;
    private String id;                	    // 회원 일련번호
    private String createdAt;               // 생성일시
    private String updatedAt;               // 수정일시
    private String agreeMarketing;          // 마케팅 정보수신 제공동의
    private String agreeMarketingAt;        // 마케팅 정보수신 제공여부 변경날짜
    private String agreeAd;            	    // 광고성 정보수신 제공동의
    private String agreeAdAt;            	// 광고성 정보수신 제공동의 변경날짜
    private String deleted;         	    // 탈퇴여부
    private String email;       		    // 이메일
    private boolean isTemp;       		    // 임시회원여부
    private String loginId;          	    // 로그인 ID
    private String name;            	    // 이름
    private String password;         	    // 비밀번호
    private String phone;          	        // 휴대폰 번호
    private String privacyExpire;           // 개인정보유효기간 선택년도
    private String privacyExpireDate;       // 개인정보유효기간
    private String profilePictureFileId;    // 프로필사진 file id
    private String deletedAt;               // 탈퇴일시
    private String lastPasswordChange;      // 최종비밀번호변경일
    private String pushToken;      		    // FCM 푸시 토큰
    private String useAppPush;    	        // 앱 푸시 사용여부
    private String isStop;		            // 이용정지여부
    private String delFlag;		            // 삭제여부
    private String simpleAuthCd;            // 간편 인증 코드 (KAKAO,FACEBOOK,GOOGLE,NAVER,APPLE)
    private String simpleAuthVal;           // 간편 인증 값
    private String hhResuFlag;				// 헤드헌터 이력서 접수 알림여부
    private String hhMsgFlag;				// 헤드헌터 메시지 알림여부
    private String hhApMsgFlag;				// 헤드헌터 후보자 메시지 알림여부
    private String hhCommFlag;				// 헤드헌터 커뮤니티 알림여부
    private String hhApFlag;				// 헤드헌터 맞춤인재 알림여부
    private String apAlarmFlag;				// 후보자 메시지 알림여부
    private String apCommFlag;				// 후보자 커뮤니티 알림여부
    private String apPosiFlag;				// 후보자 맞춤채용공고 알림여부
    private String schoolName; 				// 학교이름
    private String expireAt;				// 열람만료일
    private String totalCareer;				// 총경력
    private String companyName;				// 최종회사
    private String lastChangePassword;      // 최종비밀번호 변경일
    private String passwordFailCount;       // 비밀번호 틀린횟수
    private String regSeq;                  // 등록자 일련번호
    private String roleId;                  // 지원자 역할
    private String di;                      // 본인인증 di
    private String registerPathCd;
    private String phoneType;
    private String pictureFileId;           // 이력서 사진
    private String registerPath;            // 내 인재 등록경로
    private String opened;
    private String firstOpenChanged;
    private String schoolLocation;
    private String isExpired;

    //headhunter 테이블
    private String referralCode;		    // 추천코드
    private String approved;		        // 관리자승인여부
    private String sfName;		            // 서치펌명
    private String sfPhone;		            // 서치펌 연락처
    private String sfHomepageUrl;		    // 서치펌 홈페이지 URL
    private String sfWorkerListFileId;		// 서치펌 종사자 명부 FILE ID
    private String sfCeoName;		        // 서치펌 대표자명
    private String greeting;                // 인사말

    // hh_position_field
    @JsonIgnore
    private String fieldCd;                 // 분야 코드
    private String type;                    // 분야 구분
    private String[] specialField;          // 전문 분야 (배열)

    // 내 인재리스트
    private String finalCompany;            // 최종회사
    private String finalSchool;             // 최종학교
    private String gender;                  // 성별
    private String expireFlag;              // 열람만료여부
    private String genderCd;                // 성별코드
    private String myApFlag;                // 내 인재 여부

    //wefirm 테이블
    private String wefirmName;		        // 위펌명

    //applicant
    private String resumeRestricted;        //이력서 열람제한 여부
    private String findingJob;              //구직 의사 여부
    private String birth;                   //생년월일
    private String isPrivateAgreement;      //이력서 개인정보 수집 동의 여부
    private String hhReferralCode;          //헤드헌터 추천코드
    private String contactExceptHoliday;    // 연락 가능 주말/휴일 제외 여부
    private String contactableTime;         //연락 가능 시간
    private String memberId;                //회원 일련번호
    private String resumeId;                //이력서 일련번호

    // tb_api_login_log
    private String recentLoginTime;         // 최근 로그인 시간

    //reward
    private String balance;		            // 리워드 총량

    //검색조건
    private String startDate;
    private String endDate;
    private String nameOption;
    private String phoneOption;
    private String emailOption;
    private String idOption;
    private String userType;		          

    // pw-reset
    private String recentPassword;           // 현재비밀번호

    //login parameter
    private String autoLogin;                //자동로그인을 위한 토큰

    //list parameter(인재검색)
    private String careerSt;
    private String careerEn;
    private String ageSt;
    private String ageEn;
    private String salarySt;
    private String salaryEn;
    private String hp1;
    private String[] hp2;
    private String[] loc;
    private String language;
    private String license;
    private String keyword;

    //autoLogin
    private String simpleAuthSeq;				// 간편 인증 순서
    private String regFlag;					    // 생성 번호
    private String regDate;					    // 생성 일시

    //autoLogin api parameter
    private String phoneVal;					// 자동인증용 기기 고유값

    //internal list parameter 
    @JsonIgnore
    private String _minAge;
    @JsonIgnore
    private String _maxAge;

    // chatting parameter
    private String userSeq;
    
   /* public String get_minAge() {
        if (minAge==null) return DateUtil.getAgeDateStr(0);
        return DateUtil.getAgeDateStr(Integer.parseInt(minAge));
    }
    public String get_maxAge() {
        if (maxAge==null) return DateUtil.getAgeDateStr(0);
        return DateUtil.getAgeDateStr(Integer.parseInt(maxAge));
    }*/
}
