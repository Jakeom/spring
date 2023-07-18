package com.fw.core.dto.bo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties({"id","applicantId","positionId","createdAt","updatedAt"})
public class BoScrapPositionDTO extends CommonDTO {

    private String id;                 // 스크랩 일련번호
    private String applicantId;        // 회원 테이블 PK
    private String positionId;         // 포지션 일련번호
    private String createdAt;          // 생성일시
    private String updatedAt;          // 수정일시
    private String companyName;        // 기업이름
    private String companyScale;       // 기엽규모
    private String title;              // 공고제목
    private String endDate;            // 공고마감일

}
