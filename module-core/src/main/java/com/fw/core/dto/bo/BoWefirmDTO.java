package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;
import lombok.*;

import java.io.Serializable;

/**
 * Wefirm DTO
 * @author YJW
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoWefirmDTO extends CommonDTO implements Serializable {

    private String id;                  // we펌 일련번호
    private String createdAt;		    // 생성일시
    private String updatedAt;           // 수정일시
    private String ceo_member_id;       // we펌 대표자 일련번호
    private String logoFileId;        // 파일 일련번호
    private String name;                // we펌명
    private String description;         // we펌설명
    private String ceoName;       	    // 대표자명
    private String websiteUrl;       	// 웹사이트 URL
    private String wefirmUrl;           // we펌 URL
    private String establishYear;       // 설립년도
    private String address;             // 소재지 주소
    private String approveStatusCd;     // we펌 승인여부
    private String closed;              // 폐쇄여부
    private String completedAt;		            // 처리일시
    private String endDate;
    private String title;
    private String status;
    private String joinHeadhunter;
    private String joinPosition;
    private String positionCd;

    //headhunter DTO
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
    private String wefirmId;

    //wefirm_join_request table
    private String wjrCreatedAt;
    private String resumeCnt;
    private String positionCnt;

    //Board_content Table
    private String bcId;
    private String bcCreatedAt;
    private String bcSubject;
    private String hitCnt;
    private String memberName;

    //검색
    private String searchStart;
    private String searchEnd;
    private String memberEmail;
    private String memberPhone;

}
