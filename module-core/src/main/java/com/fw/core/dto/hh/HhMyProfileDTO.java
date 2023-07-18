package com.fw.core.dto.hh;

import com.fw.core.dto.CommonDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HhMyProfileDTO extends CommonDTO {
    // 내 정보 관리
    // 기본정보/이메일 서명 , 프로필 설정 , 이용 내역

    // 헤드헌터 - headhunter
    private String approved;                    // 관리자승인여부
    private String career;                      // 헤드헌터경력
    private String careerDesc;                  // 주요경력사항
    private String formatDesc;                  // 주요경력사항(/br 처리)
    private String greeting;                    // 인사말
    private String major;                       // 전공
    private String position;                    // 직급
    private String school;                      // 최종학교
    private String sfCeoName;                   // 서치펌 대표자명
    private String sfHomepageUrl;               // 서치펌 홈페이지 url
    private String sfName;                      // 서치펌명
    private String sfPhone;                     // 서치펌 연락처
    private String sfRegNumber;                 // 서치펌 사업자등록번호
    private String sfWorkerListFileId;          // 서치펌종사자 명부 file id
    private String signatureBasic;              // 기본용 이메일 서명
    private String signatureCd;                 // 용도
    private String signatureProposal;           // 제안용 이메일 서명
    private String memberId;                    // 회원 일련번호
    private String wefirmId;                    // 위펌 일련번호
    private String referralCode;                // 추천코드
    private String delFlag;                     // 삭제 여부
    private String createSeq;                   // 생성자 번호
    private String createdAt;                   // 생성일시
    private String updateSeq;                   // 수정자 번호
    private String updatedAt;                   // 수정일시
    private String showFlag;                    // 노출 여부
    private String phone;                       // 핸드폰번호
    private String email;                       // 이메일
    private String progressCnt;                 // 진행중 공고 수
    private String joinDate;                    // 가입일
    private String activityPeriod;              // 활동기간
    private String recentLoginTime;             // 최종 로그인 일시
    private String recentLoginDate;             // 최종 로그인 날짜
    private String profilePictureFileId;        // 프로필 사진 일련번호
    private String loginId;                     // 회원아이디
    private String name;                        // 회원이름
    private String wefirmName;                  // 소속위펌
    private String password;                    // 비밀번호
    private String recentPassword;              // 현재비밀번호 (비밀번호 변경 시)

    // 헤드헌터 분야 - hh_position_field
    private String fieldCd;
    private String fieldNm;
    private String type;

    private String[] checkFieldArr;             // 전문분야 array
    private String photoChangeFlag;             // 사진변경여부

    private String di;                          // 본인인증 di
    private String preDi;                       // 이전 di
    
    private String resultFlag;
    private String selectPeriod;
    private String startDt;
    private String endDt;
    private String dateSearchType;
    
    // 헤드헌터 이용내역
    
    private String logSeq;
    private String useType;
    private String useTypeNm;
    private String accessIp;
    private String createDt;
    private String userAgent;
    private String processTime;
}
