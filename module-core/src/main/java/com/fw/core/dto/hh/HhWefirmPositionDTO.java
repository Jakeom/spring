package com.fw.core.dto.hh;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fw.core.dto.CommonDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class HhWefirmPositionDTO extends CommonDTO {

    //position 테이블
    private String id;                   // 포지션 일련번호
    private String createdAt;            // 생성일시
    private String updatedAt;            // 수정일시
    private String deleted;              // 삭제여부(종료공고 삭제기능 요건-미사용)
    private String endDate;              // 마감일
    private String etc;                  // 기타사항
    private String feeInfo;              // 계약 수수료율
    private String industry;             // 직무명
    private String companyIndustry;
    private String isCowork;             // cowork 여부
    private String isPrivate;            // 선택공개여부
    private String jobDescription;       // AJ용 JD
    private String pmComment;            // PM코멘트
    private String status;               // 포지션상태코드 참조(1.진행 2.마감 3.중단)
    private String stopReason;           // 포지션종료사유코드 참조(1.채용완료 2.채용사의 계획변경 3.기타)
    private String title;                // 제목
    private String warrantyTerm;         // 보증기간
    private String hrCompanyId;          // 내 고객사 일련번호
    private String memberId;             // 회원 일련번호
    private String keywords;             // 기업키워드
    private String jopDescription;       // AP용 JD
    private String delFlag;              // 삭제 여부
    private String endAddCnt;            // 마감 연장 횟수
    private String team;                 // 팀구성
    private String salary;               // 연봉 수준 및 연봉 조정 여부
    private String targetCompany;        // 타깃 회사
    private String directPositionFlag;   // 직접채용 여부
    private String etcComment;           // 기타코멘트
    private String participantCnt;
    private String companyName;
    private String name;

}
