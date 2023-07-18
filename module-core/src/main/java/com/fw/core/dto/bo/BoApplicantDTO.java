package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;
import lombok.*;

/**
 * Applicant DTO
 * @author YJW
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoApplicantDTO extends CommonDTO {

    private String memberId;                  // AP 회원 일련번호
    private String birth;		              // 생년월일(yyyyMMdd)
    private String findingJob;                // 구직의사
    private String genderCd;                  // 성별구분
    private String isPrivateAgreement;        // 이력서 개인정보 수집 동의여부
    private String resumeRestricted;          // 이력서 열람제한 여부
    private String contactExceptHoliday;      // 연락가능 주말/휴일 제외여부
    private String contactableTime;       	  // 연락가능시간
    private String hhReferralCode;       	  // 헤드헌터 추천코드
    private String resumeSettingAt;           // 마지막 이력서 관리 날짜

}
