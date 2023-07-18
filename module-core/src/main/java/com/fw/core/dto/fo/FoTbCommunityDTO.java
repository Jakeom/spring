package com.fw.core.dto.fo;

import com.fw.core.dto.CommonDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FoTbCommunityDTO extends CommonDTO {

    private String communitySeq;        // 커뮤니티 순서
    private String communityType;      // 커뮤니티 종류
    private String communityTypeNm;      // 커뮤니티 종류
    private String dtype;               // AP, HH 구분
    private String title;               // 제목
    private String content;             // 내용
    private String secretFlag;         // 비밀글 여부
    private String companySeq;         // 기업 순서
    private String companyName;         // 기업명
    private String hit;                 // 조회수
    private String recommend;           // 추천수
    private String fileSeq;            // 파일 순서
    private String delFlag;            // 삭제 여부
    private String createSeq;          // 생성 번호
    private String createDt;           // 생성 일시
    private String updateSeq;          // 수정 번호
    private String updateDt;           // 수정 일시

    private String difDate;            // 현재시간 - createDt
    //member table - createSeq, memberId Join
    private String name;               // 회원 이름

    //tb_community_comment table - communitySeq 서브쿼리
    private String commentCnt;
    private String communityCommentSeq;
    private String communityRecommendSeq;

    // webView
    private String cdNm;
    private String cd;
    private String memberId;
    private String myBoardFlag;
    private String maskingName;
}
