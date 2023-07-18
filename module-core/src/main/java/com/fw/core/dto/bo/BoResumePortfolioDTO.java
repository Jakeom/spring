package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;
import lombok.*;

/**
 * resume_portfolio DTO
 * @author YJW
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoResumePortfolioDTO extends CommonDTO {

    private String resumeId;            // 이력서 일련번호
    private String id;                  // 포트폴리오 일련번호
    private String attachType;          // 첨부 유형
    private String url;                 // URL
    private String attachFileId;        // 파일 일련번호

}
