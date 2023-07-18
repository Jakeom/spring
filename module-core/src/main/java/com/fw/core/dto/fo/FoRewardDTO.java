package com.fw.core.dto.fo;

import com.fw.core.dto.CommonDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FoRewardDTO extends CommonDTO {

    //reward
    private String balance;		            // 리워드 총량
    private String id;
    private String createdAt;               // 생성일시
    private String updatedAt;               //수정일시
    private String applicantId;             //회원일련번호
    private String delFlag;                 //삭제여부

    //reward_goods 테이블
    private String point;
    private String goodNm;                  //상품명
    private String goodComp;                //사용처
    private String goodDetail;              //상세설명
    private String createSeq;               //생성번호
    private String createDt;                //생성일시
    private String updateSeq;               //수정번호
    private String updateDt;                //수정일시
    private String fileSeq;                 //사진

    //reward_history테이블
    private String goodsCd;                 //리워드교환상품
    private String increase;                //리워드 증감
    private String reasonCd;                //증감코드
    private String reasonCdNm;                //증감코드
    private String trId;                    //거래아이디
    private String headhunterId;            //열람회원일련번호
    private String rewardId;                //리워드 일련번호
    private String adminReason;             //관리자 사유
    private String backofficeAdminId;       //관리자 번호
    private String rewardGoodsId;           //리워드상품 일련번호

    //검색을 위한
    private String selectPeriod;
    private String startDate;
    private String endDate;
    private String goodsNm;

}
