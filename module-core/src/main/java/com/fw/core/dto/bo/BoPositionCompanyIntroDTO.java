package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;
import lombok.*;

/**
 * position_company_intro DTO
 * @author YJW
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoPositionCompanyIntroDTO extends CommonDTO {

    private String id;                  // 기업소개 일련번호
    private String positionId;          // 포지션 일련번호	
    private String createdAt;           // 생성일시
    private String updatedAt;           // 수정일시
    private String introUrl;            // 기업소개 URL
    private String introFileId;         // 파일 일련번호

}
