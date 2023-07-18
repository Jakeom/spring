package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * 회원 DTO
 * @author YJW
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoMemberDTO extends CommonDTO {

	private String DTYPE;		            // AP, HH 구분
    private String id;                	    // 회원 일련번호
    private String createdAt;               // 생성일시
    private String updatedAt;               // 수정일시
    private String agreeMarketing;          // 마케팅 정보수신 제공동의
    private String agreeMarketingAt;        // 마케팅 정보수신 제공여부 변경날짜
    private String deleted;         	    // 탈퇴여부
    private String email;       		    // 이메일
    private String isTemp;       		    // 임시회원여부
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

    private String lastChangePassword;

    //headhunter 테이블
    private String referralCode;		    // 추천코드
    private String approved;		        // 관리자승인여부
    private String sfName;		            // 서치펌명
    private String sfPhone;		            // 서치펌 연락처
    private String sfHomepageUrl;		    // 서치펌 홈페이지 URL
    private String sfWorkerListFileId;		// 서치펌 종사자 명부 FILE ID
    private String sfCeoName;		        // 서치펌 대표자명

    //wefirm 테이블
    private String wefirmName;		        // 위펌명

    //applicant
    private String resumeRestricted;        //이력서 열람제한 여부
    private String findingJob;              //구직 의사 여부
    private String birth;                   //생년월일
    private String genderCd;                //성별 코드
    private String isPrivateAgreement;      //이력서 개인정보 수집 동의 여부
    private String hhReferralCode;          //헤드헌터 추천코드
    private String contactExceptHoliday;    // 연락 가능 주말/휴일 제외 여부
    private String contactableTime;         //연락 가능 시간
    private String memberId;                //회원 일련번호

    //reward
    private String balance;		            // 리워드 총량

    //검색조건
    private String startDate;
    private String endDate;
    private String searchSelect;
    private String inputSelect;

    private String nameOption;
    private String phoneOption;
    private String emailOption;
    private String idOption;
    private String referralCodeOption;
    private String memberTypeOption;

    private String userType;

    //tb_blacklist
    private String blacklistType; // 블랙리스트 여부

    // param
    private String userId; // 유저 일련번호 (고유SEQ)

    private List<String> idList; // list of IDs
    private List<String> nonMemberList;

    // Datatable 대응
    private int draw;
    private int start;
    private int length;
    private int recordsTotal;
    private int totalCnt;
    private int rows;

}
