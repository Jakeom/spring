package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;
import lombok.*;

/**
 * resume_academic_background_major DTO
 * @author YJW
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoResumeAcademicBackgroundMajorDTO extends CommonDTO {

    private String Key;                             // 일련번호
    private String academicBackgroundId;            // 학력관리 일련번호
    private String majorClassCd;                    // 전공구분코드 참조
    private String majorName;                       // 전공명

}
