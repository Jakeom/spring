package com.fw.core.dto.m;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fw.core.dto.CommonDTO;
import com.fw.core.dto.hh.HhPositionDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class MCompanyDTO extends CommonDTO {

    //position 테이블
    private String id;                   // 포지션 일련번호
    private String createdAt;            // 생성일시
    private String updatedAt;            // 수정일시
    private String deleted;              // 삭제여부(종료공고 삭제기능 요건-미사용)
    private String endDate;              // 마감일
    private String etc;                  // 기타사항
    private String feeInfo;              // 계약 수수료율
    private String industry;             // 직무명
    private String companyIndustry;
    private String isCowork;             // cowork 여부
    private String isPrivate;            // 선택공개여부
    private String jobDescription;       // AJ용 JD
    private String pmComment;            // PM코멘트
    private String status;               // 포지션상태코드 참조(1.진행 2.마감 3.중단)
    private String stopReason;           // 포지션종료사유코드 참조(1.채용완료 2.채용사의 계획변경 3.기타)
    private String title;                // 제목
    private String warrantyTerm;         // 보증기간
    private String hrCompanyId;          // 내 고객사 일련번호
    private String memberId;             // 회원 일련번호
    private String keywords;             // 기업키워드
    private String jopDescription;       // AP용 JD
    private String delFlag;              // 삭제 여부
    private String endAddCnt;            // 마감 연장 횟수
    private String team;                 // 팀구성
    private String salary;               // 연봉 수준 및 연봉 조정 여부
    private String targetCompany;        // 타깃 회사
    private String directPositionFlag;   // 직접채용 여부
    private String etcComment;           // 기타코멘트
    
    private String progressCnt;     	 // 진행중인 공고 수
    private String closedCnt;       	 // 마감된 공고 수
    private String applyCnt;        	 // 오늘 신규 지원자 수
    private String uncollectedCnt;  	 // 미열람 이력서 갯수

    //member 테이블
    private String name;                 // 이름
    private String email;                // 이메일
    private String profilePictureFileId; // 프로필 사진

    //headhunter 테이블
    private String referralCode;		  // 추천코드
    private String approved;		      // 관리자승인여부
    private String sfName;		          // 서치펌명
    private String sfPhone;		          // 서치펌 연락처
    private String sfHomepageUrl;		  // 서치펌 홈페이지 URL
    private String sfWorkerListFileId;    // 서치펌 종사자 명부 FILE ID
    private String sfCeoName;		      // 서치펌 대표자명

    //hr_company 테이블
    private String companyId;             // 기업 아이디
    private String companyScale;		  // 기업규모
    private String companyName;		      // 기업명

    //position_reading_history
    private String scrapId;               // 스크랩 아이디
    private String positionId;            // 포지션 아이디

    //리스트출력을 위한 컬럼
    private String dDay;                  // 남은공고일
    private String postingCnt;            // 헤드헌터의 진행중 공고 갯수
    private String profileSrc;            // 헤드헌터 프로필 사진

    private String searchVal;
    private String scrap;
    private String manager;
    private String hhPositionType;
    private String contactCnt;
    private String resumeCnt;
    private String resumeId;
    private String directCnt;
    private String interestHh;
    private String coCnt;
    
    private List<String> statusList;
    private List<String> positionIdList;
    private List<HhPositionDTO> contactInfoList;
    private List<HhPositionDTO> coworkerList;
    private List<HhPositionDTO> idList;
    private List<MCompanyDTO> list;

    private String phone;
    private String workAddress;
    private String address;
    private String ceo;
    private String closed;
    private String companyStatus;
    private String establishDate;
    private String licenseNumber;
    private String location;
    private String marketListing;
    private String introUrl;
    private String introFileId;
    private String year;
    private String age;
    private String proposalAt;
    private String proposalStatus;
    private String proposalStatusNm;
    private String publicCd;
    private String positionReason;
    private String loginId;
    private String mode;
    private String passedStatusNm;
    private String progressStep;
    private String processStatusNm;
    private String receiptPathNm;
    private String passedStatus;
    private String processStatus;
    private String receiptPath;
    private String regPathNm;
    private String regPath;
    private String progress;
    private String changeAt;
    private String type;
    private String subject;
    private String content;
    private String proposedEamil;
    private String viewMode;
    private String phoneOpened;
    private String pmSubmitStatusNm;
    private String submitResumeFileId;
    private String password;
    private String recommendId;
    private String receiptStatus;
    private String pmSubmitted;
    private String apMemberId;
    private String hhMemberId;
    private String finalSchool;
    private String totalCareers;

    private MultipartFile[] licenceFile;
    private MultipartFile[] companyInfoFiles;

    @JsonIgnore
    public int getDays(){
        return Integer.parseInt(this.dDay);
    }

    @JsonIgnore
    public int getCoCount(){
        return Integer.parseInt(this.coCnt);
    }

}
