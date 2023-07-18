package com.fw.core.dto.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fw.core.dto.CommonDTO;
import lombok.*;

/**
 * resume DTO
 * @author YJW
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResumeDTO extends CommonDTO {

    private String id;                          // 이력서 일련번호
    private String createdAt;                   // 생성일시
    private String updatedAt;                   // 수정일시
    private String address;                     // 주소
    private String birth;                       // 생년월일
    private String bylaws;                      // 회사내규에 따름 여부
    private String careerDescription;           // 경력기술서
    private String coreAbility;	                // 핵심역량
    private String deleted;                     // 삭제여부
    private String deletedAt;                   // 삭제일자
    private String desiredPosition;             // 희망직급
    private String desiredSalary;               // 희망연봉
    private String disability;                  // 장애여부        
    private String disabilityRatingCd;          // 장애등급코드참조
    private String disabilityRatingReason;      // 장애등급사유
    private String employmentStatusCd;          // 취업상태코드참조
    private String existTemp;                   // 임시저장데이터 존재여부
    private String finalPosition;               // 최종직급(직급코드참조)
    private String genderCd;                    // 성별코드참조
    private String isVeterans;                  // 보훈대상여부
    private String joinDateCd;                  // 입사가능시기 코드참조
    private String militaryServiceCd;           // 병역코드참조
    private String name;                        // 이름
    private String opened;                      // 공개여부
    private String representation;              // 대표이력서 여부
    private String selfIntroduction;            // 자기소개서
    private String totalCareer;                 // 총 경력 개월수
    private String veteransReason;              // 보훈대상사유
    private String createMemberId;              // 최초생성 회원 일련번호
    private String memberId;                    // 회원 테이블 PK
    private String parentResumeId;              // 임시저장 이력서인 경우 부모 이력서 id
    private String pictureFileId;               // 통합 파일 관리 일련번호
    private String createPath;                  // 등록내용
    private String firstOpenChanged;            // 최초 공개/비공개 변경 여부
    private String delFlag;            			// 삭제여부
    private String resumeId;                    // 이력서 일련번호
    private String finalSchool;                 // 최종학교
    private String finalCompany;                // 최종회사
    private String resumeName;                  // 이력서이름
    private String birthYear;                   // 출생년도
    private String age;                         // 나이

    // member 테이블
    private String DTYPE;                       // AP, HH 구분

    //검색조건
    private String nameOption;
    private String phoneOption;
    private String emailOption;
    private String startDate;
    private String endDate;
    private String expireat;					// 열람만료일
    private String profilepicturefileid;		// 프로필 사진 ID
    private String school;						// 최종학력
    private String companyname;					// 회사이름
    private String title;            			// 이력서 명
}
