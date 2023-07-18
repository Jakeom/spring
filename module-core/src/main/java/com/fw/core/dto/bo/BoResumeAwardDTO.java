package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;
import lombok.*;

/**
 * resume_award DTO
 * @author YJW
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoResumeAwardDTO extends CommonDTO {

    private String resumeId;          // 이력서 일련번호
    private String id;                // 수상내역 일련번호
    private String agency;            // 수여기관
    private String awardYm;           // 수상년월
    private String name;              // 수상명

}
