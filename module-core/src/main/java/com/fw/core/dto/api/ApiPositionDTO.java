package com.fw.core.dto.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fw.core.dto.CommonDTO;
import lombok.*;

import java.util.List;

/**
 * 포지션 DTO
 * @author YJW
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiPositionDTO extends CommonDTO {

    private String positionId;              // 포지션 일련번호
    private String id;                      // 포지션 일련번호
    private String memberId;                // HH 회원 일련번호
    private String hrCompanyId;             // 내 고객사 일련번호
    private String createdAt;               // 생성일시
    private String updatedAt;               // 수정일시
    private String deleted;                 // 삭제여부 (종료공고 삭제기능 요건)
    private String endDate;                 // 마감일
    private String etc;	                    // 기타사항
    private String feeInfo;                 // 계약 수수료율
    private String industry;                // 직무명
    private String isCowork;                // cowork 여부
    private String isPrivate;               // 선택공개여부
    private String jopDescription;          // AP용 JD
    private String pmComment;               // PM코멘트
    private String status;                  // 포지션상태코드 참조(1:진행 2:마감 3:중단)
    private String statusCd;                // 포지션 상태코드
    private String stopReason;              // 포지션종료사유코드 참조(1:채용완료 2:채용사의 계획 변경 3:기타)
    private String title;                   // 제목
    private String warrantyTerm;            // 보증기간
    private String keywords;                // 기업키워드
    private String email;                   // 헤드헌터 이메일(member table)
    private String name;                    // 헤드헌터 이름(member table)
    private String url;                     // 프로필 사진 이미지 url
    private String sfName;                  // 소속 서치펌명
    private String endAddCnt;               // 마감 연장 횟수
    private String companyName;             // 기업명
    private String companyScale;            // 기업규모
    private String companyStatus;           // 기업상태코드
    private String positionCount;           // 해당 헤드헌터 진행중인 공고 수
    private String photoUrl;                // 헤드헌터 사진
    private String endDday;                 // 공고 마감 D-Day
    private String scrapFlag;               // 스크랩여부
    private String applyFlag;               // 지원여부
    private String appliedCnt;              // 지원자수 (헤더헌터 검색 시)
    private String DTYPE;                   // AP/HH 구분
    private String appliedAt;               // 지원일자
    private String manager;                 // 담당자명(hh)
    private String hhPositionType;          // 포지션구분(PM/CO/단독)
    private String contactCnt;              // 컨택리스트 수
    private String birth;                   // 생년월일
    private String gender;                  // 성별
    private String genderCd;                // 성별코드
    private String phone;                   // 연락처
    private String totalCareer;             // 총 경력 개월수
    private String profilePictureFileId;    // 프로필사진 파일 일련번호
    private String regPath;                 // 등록경로 (내 인재/직접지원/수동입력)
    private String regPathCd;               // 포지션 지원경로 코드
    private String finalSchool;             // 최종학력
    private String finalCompany;            // 최종회사
    private String age;                     // 나이
    private String progressCnt;             // 헤드헌터 진행중인 공고수 (포지션 상세)
    private String hhName;                  // 헤드헌터 이름
    private String receiptPath;             // 지원형태
    private String receiptPathCd;           // 포지션 지원형태 코드
    private String processStatus;           // 진행상태
    private String processStatusCd;         // 포지션 지원 진행상태 코드
    private String progressStepCd;          // 진행상태
    private String proposalStatusCd;        // 제안상태
    private String positionType;            // 공고분류
    private String headhunterId;            // 헤드헌터 일련번호
    private String resumeId;                // 이력서 일련번호
    private String pictureFileId;           // 이력서 사진 일련번호
    private String companySeq;              // 고객사 일련번호
    private String reviewFlag;              // 합격후기 작성여부
    private String userSeq;                 // 회원일련번호 - 채팅웹뷰
    private String dtype;                   // 회원구분 - 채팅웹뷰
    private String extendYn;                // 연장가능여부
    private String publicCd;                // 회사명 공개여부

    @JsonIgnore
    private boolean isScrap;                // 스크랩 존재 여부
    @JsonIgnore
    private boolean isRead;                 // 열람기록 존재 여부

    /* request body */
    private String userType;                // 회원 일련번호로 AP/HH 구분
    private String searchApply;             // 지원한 리스트 검색용(Y일때 지원한 리스트 조회 & 회원 일련번호 필수)
    private String searchScrap;             // 스크랩된 리스트 검색용(Y일때 스크랩된 리스트 조회 & 회원 일련번호 필수)
    private String searchContact;           // Contact한 리스트 검색용(Y일때 Contact한 리스트 조회 & 회원 일련번호 필수)
    private String searchHh;                // Y일때 헤드헌터 내 포지션 관리 조회
    private List<String> searchList;        // 검색어 리스트
}
