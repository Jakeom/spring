package com.fw.core.dto.fo;

import com.fw.core.dto.CommonDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class FoPositionDTO extends CommonDTO {

    //position 테이블
    private String id;                   // 포지션 일련번호
    private String createdAt;            // 생성일시
    private String updatedAt;            // 수정일시
    private String deleted;              // 삭제여부(종료공고 삭제기능 요건-미사용)
    private String endDate;              // 마감일
    private String etc;                  // 기타사항
    private String feeInfo;              // 계약 수수료율
    private String industry;             // 직무명
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
    private String publicCd;

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
    private String companySeq;            // 기업 아이디
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
    private String interestHh;
    private String coCnt;
    private String applicantId;
    private List<String> statusList;
    private List<String> positionIdList;
    private MultipartFile[] resumeFiles;
    private MultipartFile[] agreeFiles;

    private String agreeFileId;
    private String resumeFileId;

    public int getDays(){
        return Integer.parseInt(this.dDay);
    }

    public int getCoCount(){
        return Integer.parseInt(this.coCnt);
    }

    private String applicantYn;
    private String passCommunityCnt;
    private String positionType;
    private String mode;

}
