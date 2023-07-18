package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;
import lombok.*;

/**
 * resume_license DTO
 * @author YJW
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoResumeLicenseDTO extends CommonDTO {

    private String resumeId;          // 이력서 일련번호
    private String id;                // 자격증 일련번호
    private String agency;            // 발행처
    private String name;              // 자격증명
    private String obtainYm;          // 취득년월

}
