package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;
import lombok.*;

/**
 * resume_external_activity DTO
 * @author YJW
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoResumeExternalActivityDTO extends CommonDTO {

    private String resumeId;         // 이력서 일련번호
    private String id;               // 대외활동 일련번호
    private String content;          // 대외활동

}
