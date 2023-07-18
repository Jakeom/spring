package com.fw.core.dto.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fw.core.dto.CommonDTO;
import lombok.*;

/**
 * scrap_position DTO
 * @author YJW
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiScrapPositionDTO extends CommonDTO {

    private String id;                  // 스크랩 일련번호
    private String applicantId;         // 회원 테이블 PK
    private String positionId;          // 포지션 일련번호 
    private String createdAt;           // 생성일시
    private String updatedAt;           // 수정일시
    /* hr_company */
    private String companyName;         // 기업이름
    private String companyScale;        // 기엽규모
    private String companyStatus;       // 기업상태코드
    /* position */
    private String title;               // 공고제목
    private String endDate;             // 공고마감일
    private String status;              // 포지션 상태코드(DOING:진행/END:마감/CLOSE:중단)
    private String scrapFlag;           // 스크랩유무
    private String applyFlag;           // 지원유무
    private String jopDescription;      // AP용 JD
    private String pmComment;           // PM코멘트
    /* request body */
    private String memberId;            // 회원 일련번호
    // 공통코드
    private String statusCd;            // 포지션 상태코드
}
