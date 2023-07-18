package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;
import lombok.*;

/**
 * resume_academic_background DTO
 * @author YJW
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoResumeAcademicBackgroundDTO extends CommonDTO {

    private String resumeId;                // 이력서 일련번호
    private String id;                      // 학력관리 일련번호
    private String degreeCd;                // 학위분류코드
    private String entranceStatusCd;        // 입학상태코드
    private String entranceYm;              // 입학년월 yyyymm
    private String grades;                  // 학점
    private String graduationStatusCd;      // 졸업상태코드
    private String graduationYm;            // 졸업년월 yyyymn
    private String inOverseas;              // 해외여부
    private String name;                    // 학교명
    private String schoolCd;                // 학교분류코드
    private String locationCd;              // 소재지코드

}
