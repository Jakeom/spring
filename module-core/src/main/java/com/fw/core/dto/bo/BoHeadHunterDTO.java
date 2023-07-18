package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;
import lombok.*;

/**
 * 헤드헌터 DTO
 * @author YJW
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoHeadHunterDTO extends CommonDTO {

    private String memberId;                  // 회원 테이블 PK
    private String approved;		           // 관리자승인여부
    private String career;                     // 헤드헌터경력
    private String careerDesc;                 // 주요경력사항
    private String greeting;                   // 인사말
    private String major;                      // 전공
    private String position;         	       // 직급
    private String referralCode;       		   // 추천코드
    private String school;       		       // 최종학교
    private String sfCeoName;          	       // 서치펌 대표자명
    private String sfHomepageUrl;              // 서치펌 홈페이지 URL
    private String sfName;         	           // 서치펌명
    private String sfPhone;          	       // 서치펌 연락처
    private String sfRegNumber;                // 서치펌 사업자등록번호
    private String sfWorkerListFileId;         // 서치펌 종사자 명부 file id
    private String signatureBasic;             // 기본용 이메일 서명
    private String signatureCd;                // 용도
    private String signatureProposal;          // 제안용 이메일 서명
    private String wefirmId;      		       // we펌 일련번호

}
