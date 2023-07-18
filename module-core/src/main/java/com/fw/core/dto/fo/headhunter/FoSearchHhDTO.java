package com.fw.core.dto.fo.headhunter;

import com.fw.core.dto.CommonDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FoSearchHhDTO extends CommonDTO {

    //headhunter테이블
    private String approved;                    //관리자승인여부
    private String career;                      //헤드헌터경력
    private String careerDesc;                  //주요경력사항
    private String greeting;                    //인사말
    private String major;                       //전공
    private String position;                    //직급
    private String school;                      //최종학교
    private String sfCeoName;                   //서치펌 대표자명
    private String sfHomepageUrl;               //서치펌 홈페이지 url
    private String sfName;                      //서치펌명
    private String sfPhone;                     //서치펌 연락처
    private String sfRegNumber;                 //서치펌 사업자등록번호
    private String sfWorkerListFileId;          //서치펌종사자 명부 file id
    private String signatureBasic;              //기본용 이메일 서명
    private String signatureCd;                 //용도
    private String signatureProposal;           //제안용 이메일 서명
    private String memberId;                    //회원 일련번호
    private String wefirmId;                    //위펌 일련번호
    private String referralCode;                //추천코드
    private String delFlag;                     //삭제 여부
    private String createSeq;                   //생성자 번호
    private String createdAt;                   //생성일시
    private String updateSeq;                   //수정자 번호
    private String updatedAt;                   //수정일시
    private String showFlag;                    //노출 여부
    private String profilePictureFileId;
    private int readCnt;
    private int blackCnt;

    //hh_position_field테이블
    private String id;                          //분야 일련번호
    private String fieldCd;                     //분야 코드 참조
    private String fieldCdNm;                     //분야 코드 참조
    private String type;                        //분야구분(전문,진행)

    //member테이블
    private String phone;
    private String name;
    private String email;


    //리스트출력을 위한 컬럼
    private String postingCnt;            // 헤드헌터의 진행중 공고 갯수
    private String profileSrc;            // 헤드헌터 프로필 사진
    private String searchVal;
    private String searchType;
    private String recentPosting;
    private String interestHh;

    //검색을 위한
    private String[] checkFieldArr;
    private String checkedFields;
    private String recentLogin;

}
