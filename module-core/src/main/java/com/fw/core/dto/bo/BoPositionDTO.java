package com.fw.core.dto.bo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties({"proposalAt","receiptPath","proposalStatus","startDate","endDt","memberId","hrCompanyId",
                       "updatedAt","deleted","etc","feeInfo","isPrivate","pmComment","stopReason",
                       "warrantyTerm"})
public class BoPositionDTO extends CommonDTO {

    private String id;                     // 포지션 일련번호
    private String memberId;               // HH 회원 일련번호
    private String hrCompanyId;            // 내 고객사 일련번호
    private String createdAt;              // 생성일시
    private String updatedAt;              // 수정일시
    private String deleted;                // 삭제여부 (종료공고 삭제기능 요건)
    private String endDate;                // 마감일
    private String etc;	                   // 기타사항
    private String feeInfo;                // 계약 수수료율
    private String industry;               // 직무명
    private String isCowork;               // cowork 여부
    private String isPrivate;              // 선택공개여부
    private String jopDescription;         // AP용 JD
    private String pmComment;              // PM코멘트
    private String status;                 // 포지션상태코드 참조(1:진행 2:마감 3:중단)
    private String stopReason;             // 포지션종료사유코드 참조(1:채용완료 2:채용사의 계획 변경 3:기타)
    private String title;                  // 제목
    private String warrantyTerm;           // 보증기간
    private String keywords;               // 기업키워드
    private String searchKeyword;          // 검색키워드
    private String email;                  // 헤드헌터 이메일(member table)
    private String name;                   // 헤드헌터 이름(member table)
    private String url;                    // 프로필 사진 이미지 url
    private String sfName;                 // 소속 서치펌명

    //position_applicant
    private String proposalAt;             // 최초 제안 일시
    private String receiptPath;            // 접수방법 코드 참조(본인지원, 등록대행, 제안승인, CO접수 등)
    private String positionProposalStatus;         // 전형상태코드참조(서류합격, 서류탈락, 면접학격, 최종합격 등)
    private String passedStatus;         // 최종합격, 조율실패, 입사포기, 중도퇴사 등
    private String processStatus;         // 서류합격, 서류탈락, 면접학격, 최종합격 등
    private String receiptStatus;         // 미열람, 이력서수정, 추천완료 등
    private String progressStep;         // 진행단계 코드 참조(컨택, 접수, 전형, 합격)
    private String poaCreatedAt;         // 생성날짜
    private String regPath;         // 생성날짜
    private String progressStepCode;         // 진행단계 코드 commonCd 적용
    private String receiptTabStatus;	// 접수탭 상태
    private String processResult;	    // 전형 결과

    //position_participant
    private String participantId; // 포지션 참가자 ID
    //검색조건
    private String startDate;
    private String endDt;
    
    //hr_company
    private String companyName;			// 기업명
    private String companyScale;		// 기업규모

    private List<String> searchList;        // 검색어 리스트
}
