package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;
import lombok.*;

/**
 * resume_skill DTO
 * @author YJW
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoResumeSkillDTO extends CommonDTO {

    private String resumeId;        // 이력서 일련번호
    private String id;              // 보유기술 일련번호
    private String content;         // 보유기술내용
    private String name;            // 보유기술명

}
