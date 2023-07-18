package com.fw.core.dto.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fw.core.dto.CommonDTO;
import lombok.*;

/**
 * position_applicant DTO
 * @author YJW
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiPositionApplicantDTO extends CommonDTO {

    private String id;                      // 포지션 지원자 일련번호
    private String applicantId;             // 지원 일련번호
    private String createdAt;               // 생성일시
    private String updatedAt;               // 수정일시
    private String changeAt;                // 진행단계 변경일
    private String hhTempStatus;            // CO 이력서 결과 임시저장 (CO가 AP에게 직접 통보해야함)
    private String hrCheckedAt;             // hr 추천 이력서 hr 확인 시간
    private String hrTempStatus;            // HR 서류전형 합격 통보 임시저장 상태코드 참조
    private String joinDate;	            // 입사예정일
    private String joinMailSubmitDate;      // 입사안내메일발송일
    private String passedStatus;            // 합격상태코드참조(최종합격, 조율실패, 입사포기, 중도퇴사 등)
    private String phoneOpened;             // 연락처PM공개여부
    private String pmSubmitted;             // PM제출여부
    private String processStatus;           // 전형상태코드참조(서류합격, 서류탈락, 면접학격, 최종합격 등)
    private String progressStep;            // 진행단계 코드 참조(컨택, 접수, 전형, 합격)
    private String proposalAt;              // 최초 제안 일시le id
    private String proposalStatus;          // 컨택상태(검토중, 제안, 탈락, 제안거부, 제안승인, 지원취소)
    private String proposedEmail;           // 이메일 제안 여부
    private String proposedSms;             // sms 제안 여부
    private String receiptPath;             // 접수방법 코드 참조(본인지원, 등록대행, 제안승인, CO접수 등)
    private String receiptStatus;           // 접수상태 코드 참조(미열람, 이력서수정, 추천완료 등)
    private String regPath;                 // 1:내인재 2:직접지원 3:수동입력
    private String rejectReason;            // 제안거절사유 수동입력
    private String rejectReasonCd;          // 제안거절사유 코드
    private String resultNoticed;           // CO 결과 통보 유무 (to AP)
    private String updateRequest;           // 새로운 수정요청 체크
    private String apMemberId;              // 지원자 AP 일련번호 (수동입력 시 null)
    private String hhMemberId;              // 지원자를 등록한 HH id (직접지원인 경우?)
    private String resume_id;               // 이력서 일련번호
    private String submitResumeFileId;      // 제출용 파일 일련번호
    private String resumeModifyFlag;        // 이력서 수정여부 파라미터
    private String proposedEamil;

    /* Request Parameter */
    private String positionId;              // 포지션 일련번호
    private String memberId;                // 헤드헌터 일련번호
    private String resumeId;                // 헤드헌터 일련번호

    /* hr_company */
    private String companyName;             // 기업이름
    private String companyScale;            // 기엽규모

    /* position */
    private String title;                   // 공고제목

    /* custom column */
    private String week;                    // 요일표시

    /* member table */
    private String DTYPE;		            // AP, HH 구분
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
    private String agreeAd;                 // 광고성 정보 수신 동의여부
    private String agreeAdAt;               // 공고성 정보 수신 동의여부 변경날짜

    /* applicant table */
    private String contactableTime;         // 연락 가능 시간
}
