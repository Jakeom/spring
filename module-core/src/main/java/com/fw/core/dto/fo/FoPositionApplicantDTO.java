package com.fw.core.dto.fo;

import com.fw.core.dto.CommonDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FoPositionApplicantDTO extends CommonDTO {

    //position 테이블
    private String id;                          // 포지션 지원자 일련번호
    private String createdAt;                   // 생성일시
    private String updatedAt;                   // 수정일시
    private String changeAt;                    // 진행단계 변경일
    private String hhTempStatus;                // CO 이력서결과 임시저장
    private String hrCheckedAt;                 // hr 추천이력서 hr확인 시간
    private String hrTempStatus;                // hr 서류전형 합격 통보 임시저장 상태코드 참조
    private String joinDate;                    // 입사예정일
    private String joinMailSubmitDate;          // 입사안내 메일발송
    private String passedStatus;                // 합격사태코드참조
    private String phoneOpened;                  // 연락처PM공개여부
    private String pmSubmitted;                 // pm제출여부
    private String processStatus;               // 전형상태 코드참조
    private String progressStep;                // 진행단계 코드 참조
    private String proposalAt;                  // 최초 제안 일시
    private String proposalStatus;              // 컨택상태
    private String proposedEamil;               // 이메일 제안 여부
    private String proposedSms;                 // sms제안여부
    private String receiptPath;                 // 접수방법 코드 참조
    private String receiptStatus;               // 접수상태 코드 참조
    private String regPath;                     // 1.내인재 2.직접지원 3.수동입력
    private String rejectReason;                // 제안거절사유 수동입력
    private String rejectReasonCd;              // 제안거절사유 코드
    private String resultNoticed;               // co결과 통보 유무
    private String updateRequest;               // 새로운 수정요청 체크
    private String apMemberId;                  // 지원자 ap 일련번호
    private String hhMemberId;                  // 지원자를 등록한 hhid
    private String positionId;                  // 포지션 일련번호
    private String resumeId;                    // 이력서 일련번호
    private String submitResumeFileId;          // 제출용 파일 일련번호
    private String delFlag;                     // 삭제여부
    private String isPrivate;                   // 공개여부
    private String title;                       // 제목
    private String publicCd;

    //position_applicant 테이블
    private String resumeModifyFlag;     // 이력서수정동의여부

    //member 테이블
    private String name;                 // 이름
    private String email;                // 이메일
    private String profilePictureFileId; // 프로필 사진

    //headhunter 테이블
    private String referralCode;		  // 추천코드
    private String approved;		      // 관리자승인여부
    private String sfName;		          // 서치펌명
    private String sfPhone;		          // 서치펌 연락처
    private String sfHomepageUrl;		  // 서치펌 홈페이지 URL
    private String sfWorkerListFileId;    // 서치펌 종사자 명부 FILE ID
    private String sfCeoName;		      // 서치펌 대표자명

    //hr_company 테이블
    private String companyScale;		  // 기업규모
    private String companyName;		      // 기업명
    private String address;		          // 기업 주소
    private String phone;		          // 기업 전화번호

    //리스트출력을 위한 컬럼
    private String dDay;                  // 남은공고일
    private String postingCnt;            // 헤드헌터의 진행중 공고 갯수
    private String profileSrc;            // 헤드헌터 프로필 사진
    private String postingId;             // 포지션 아이디
    private int totalProposalCnt;           // 제안받은 포지션
    private int totalPassCnt;           // 최종합격
    private String selectPeriod;
    private String scrapId;
    private String processStatusNm;
    private String blackCnt;
    private String blackId;
    private String selectCondition;

    private String acceptance;             // 제안수락여부
    private String applicantId;

    private String representation;              // 대표이력서 여부
    private String mode;
}
