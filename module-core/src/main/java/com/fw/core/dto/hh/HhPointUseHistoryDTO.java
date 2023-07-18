package com.fw.core.dto.hh;

import com.fw.core.dto.CommonDTO;

import lombok.*;

/**
 * point_use_history DTO
 *
 * @author WSH
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HhPointUseHistoryDTO extends CommonDTO {

	private String id;         	  	 // 사용내역 일련번호
	private String createdAt;     	 // 생성일시
    private String updatedAt;     	 // 수정일시
    private String paymentId;		 // 결제내역 일련번호
    private String pointId;			 // 포인트 일련번호
    private String memberId;		 // HH 회원 일련번호
    private String freeBalance;		 // 무상지급 포인트 잔여수량
    private String freeIncrease;	 // 무상지급 포인트 증감
    private String paidIncrease;	 // 결제 포인트 증감
    private String paidBalance;		 // 결제 포인트 잔여수량
    private String reasonCd;		 // 지급, 충전, 사용, 환불, 취소
    private String backofficeAdminId;// 관리자 일련번호
    private String adminReason;		 // 관리자 지급,회수 사유

    private String balance;         // js 간편화를 위해 freeBalance balance로 alias
    private String adminName;       // 관리자 이름
    private String memberType;
    private String reasonNm;

    private String pointIncrease;   // 포인트 증감
    private String type;

    //payment 테이블
    private String cancelledPrice;          // 총 취소금액
    private String method;                  // 결제 수단 alias
    private String methodName;              // 결제 수단 명칭
    private String orderId;                 // 주문번호
    private String paymentData;             // PG사 결제 raw 데이터
    private String pg;                      // 걸제 PG alias
    private String pgName;                  // 결제 PG 명칭
    private String price;                   // 결제가격
    private String productName;             // 상품명
    private String purchasedAt;             // 결제 승인시간
    private String remainPrice;             // 총 금액 - 최소 금액
    private String receiptId;               // 영수증ID
    private String status;                  // 결제상태
    private String unit;                    // 결제 단위(krw, usd)
    private String pointProductId;          // 포인트 상품 일련번호
    private String requestedAt;             // 결제 요청시간
    private String bankCode;                // 은행코드
    private String bankName;                // 은행명
    private String virtualAccount;          // 가상계좌
    private String depositor;               // 입금자명
    private String expireDate;              // 입금기한
    private String revokedAt;               // 결제 취소시간
    private String delFlag;
    private String mode;

    // 검색조건
    private String startDt;
    private String endDt;
    private String useType;
    private String cancelPoint;
    private String dateSearchType;
    private String selectPeriod;
}
