package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;
import lombok.*;

/**
 * resume_career DTO
 * @author YJW
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoResumeCareerDTO extends CommonDTO {

    private String resumeId;                // 이력서 일련번호
    private String id;                      // 경력관리 일련번호
    private String annualSalary;            // 연봉
    private String category;                // 업종(기업검색api 값)
    private String companyName;             // 회사명
    private String departmentName;          // 소속부서명
    private String dutyCd;                  // 직책코드참조
    private String dutyInput;  	            // 직책 직접입력
    private String entranceYm; 	            // 입사년월
    private String holdOffice;              // 재직중여부
    private String positionCd;              // 직급코드참조
    private String positionInput;           // 직급 직접입력
    private String resignationYm;           // 퇴사년월
    private String task;                    // 담당업무

}
