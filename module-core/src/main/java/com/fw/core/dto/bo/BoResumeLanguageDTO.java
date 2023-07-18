package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;
import lombok.*;

/**
 * resume_language DTO
 * @author YJW
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoResumeLanguageDTO extends CommonDTO {

    private String resumeId;                    // 이력서 일련번호
    private String id;                          // 어학 일련번호
    private String conversationCd;              // 회화수준구분코드
    private String languageCd;                  // 어학코드 참조(영어, 일어, 중국어, 직접입력)
    private String languageCertificationCd;     // 어학구분코드 참조(회화, 공인시험)
    private String languageInput;               // 어학직접입력
    private String obtainYm;                    // 취득년월
    private String testInput;                   // 시험직접입력
    private String testScore;                   // 시험점수
    private String testTypeCd;                  // 시험종류코드 참조

}
