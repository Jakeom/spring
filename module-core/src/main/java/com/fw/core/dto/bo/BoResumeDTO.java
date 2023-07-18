package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * resume DTO
 * @author YJW
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoResumeDTO extends CommonDTO {

    private String id;                          // 이력서 일련번호
    private String createdAt;                   // 생성일시
    private String createdAtFormat;                   // 생성일시
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
    private String resumeName;                  // 이력서 이름
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
    private String resumeUrl;                   // 이력서 호출할 Webview URL
    private String minorGrades;

    // member 테이블
    private String createMemberDtype;                       // AP, HH 구분

    //position 테이블
    private String title;

    //resume_academic_background 테이블 쿼리 후 as
    private String lastAcademic; // 최종학력 이름
    
    //resume_career 테이블 쿼리후 as
    private String lastCareer; // 최종경력 회사이름

    //검색조건
    private String nameOption;
    private String phoneOption;
    private String emailOption;

    private String startDate;
    private String endDate;
    private String graduationYmNo;

    private String expireat;					// 열람만료일
    private String profilepicturefileid;		// 프로필 사진 ID
    private String school;						// 최종학력
    private String companyname;					// 회사이름
    private String acceptStatus;
    private String parsingErrorId;

    //이력서 저장
    //resume 테이블
    private String resumeTitle;                 // 이력서 제목

    //hh_resume_reading_history
    private String expireFlag;                  // 이력서 열람 만료여부
    private String expireAt;                    // 이력서 열람 만료기간

    //custom column
    private String age;                         // 나이
    private String year;                        // 년도

    //학력테이블
    private String degreeCd;                    // 학위분류코드
    private String entranceStatusCd;            // 입학상태코드
    private String entranceYm;                  // 입학년도
    private String grades;                      // 학점
    private String graduationStatusCd;          // 졸업상태코드
    private String graduationYm;                // 졸업년월
    private String inOverseas;                  // 해외여부
    private String schoolName;                  // 학교명
    private String locationCd;                  // 소재지코드
    private String schoolCd;                    // 학교분류코드
    private String resumeId;                    // 이력서 일련번호

    //전공테이블
    private String majorClassCd;                // 전공구분코드 참조
    private String majorName;                   // 전공명
    private String academicBackgroundId;        // 학력관리 일련번호

    //경력테이블
    private String annualSalary;                // 연봉
    private String category;                    // 업종
    private String companyName;                 // 회사명
    private String departmentName;              // 소속부서명
    private String dutyCd;                      // 직책코드참조
    private String dutyInput;                   // 직책 직접입력
    private String entranceYmCareer;            // 입사년월
    private String holdOffice;                  // 재직중여부
    private String positionCd;                  // 직급코드참조
    private String positionInput;               // 직급 직접입력
    private String resignationYm;               // 퇴사년월
    private String task;                        // 담당업무
    private String salary;                      // 최종 연봉
    private String certFlag;                    // 인증여부

    //핵심역량 테이블
    private String spec1;                       // 역량1
    private String spec2;                       // 역량2
    private String spec3;                       // 역량3
    private String spec4;                       // 역량4
    private String spec5;                       // 역량5
    private String specType;                    // 역량 코드
    private String spec;                        // 역량

    //희망 근무지 테이블
    private String desiredLocationCd;           // 희망근무지

    //어학 테이블
    private String conversationCd;              // 회화수준구분코드
    private String languageCd;                  // 어학코드 참조(영어,일어,중국어,직접입력)
    private String languageCertificationCd;     // 어학구분코드 참조(회화, 공인시험)
    private String languageInput;               // 어학직접입력
    private String obtainYm;                    // 취득년월
    private String testInput;                   // 시험직접입력
    private String testScore;                   // 시험점수
    private String testTypeCd;                  // 시험종류코드참조

    //자격증 테이블
    private String agency;                      // 발행처
    private String licenseName;                 // 자격증명
    private String obtainYmLicense;             // 취득년월

    //수상내역 테이블
    private String awardAgency;                 // 수여기관
    private String awardYm;                     // 수상년월
    private String awardName;                   // 수상명

    //대외활동 테이블
    private String content;                     // 대외활동

    //포트폴리오 테이블
    private String attachType;                  // 첨부유형
    private String url;                         // url
    private String attachFileId;                // 파일 일련번호

    //영문이력서 테이블
    private String enAttachType;                // 첨부유형
    private String enUrl;                       // url
    private String enAttachFileId;              // 파일 일련번호

    //맞춤 채용공고 알림
    private String positionAlertSeq;            // seq
    private String industry;                    // 직무
    private String regSeq;                      // 생성번호
    private String regDate;                     // 생성일시

    //출력을 위한 컬럼
    private String profileSrc;
    private String email;
    private String phone;
    private String tbName;
    private String minorClassCd;
    private String minorName;
    private String majorGrades;
    private String filterCd;
    private String lastSalary;
    private String desiredHire;

    private MultipartFile[] resumeImageFiles;
    private MultipartFile[] enResumeFile;
    private MultipartFile[] portFolioFiles;

    private List<BoResumeDTO> portfolioList;
    private List<BoResumeDTO> careerList;
    private List<BoResumeDTO> academicBackgroundList;
    private List<BoResumeDTO> academicBackgroundMajorList;
    private List<BoResumeDTO> specList;

    //평의를 위한
    private String sqlType;
    private String photoChangeFlag;
    private String fileChangeFlag;
    private String enFileChangeFlag;
    private String totalCareers;

    private List<String> deleteAcademicList;
    private List<String> deleteCareerList;
    private List<String> deletePortfolioList;

    // 공통코드 변환값
    private String desiredLocationNm;   // 희망근무지
    private String specNm;              // 핵심역량
    private String joinDateNm;          // 입사가능시기
    private String militaryServiceNm;   // 병역여부
    private String holdOfficeNm;        // 재직여부
    private String graduationStatusNm;  // 졸업상태
    private String desiredHireNm;       // 고용형태

    //custom
    private String resignationYmCareer;
    private String finalSchoolLocationCd;
    private String finalSchool;
    private String finalCareer;
    private String currentCareer;
    private String totalCareerSum;

    //회원가입 사용
    private String loginId;
    private String agreeMarketing;
    private String dtype;
    private boolean temp;
    private String password;
    private String roleId;
    private String referralCode;

    private String joinMemberId;

    // Datatable 대응
    private int draw;
    private int start;
    private int length;
    private int recordsTotal;

}
