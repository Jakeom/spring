package com.fw.core.dto.hh;

import com.fw.core.dto.CommonDTO;
import lombok.*;

/**
 * payment DTO
 * @author YJW
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HhPaymentDTO extends CommonDTO {

    private String id;                      // 결제내역 일련번호
    private String cancelledPrice;          // 총 취소금액
    private String createdAt;
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
    private String memberId;                // HH 회원 일련번호
    private String pointProductId;          // 포인트 상품 일련번호
    private String requestedAt;             // 결제 요청시간
    private String bankCode;                // 은행코드
    private String bankName;                // 은행명
    private String virtualAccount;          // 가상계좌
    private String depositor;               // 입금자명
    private String expireDate;              // 입금기한
    private String revokedAt;               // 결제 취소시간
    private String delFlag;

    //point 테이블
    private String updatedAt;     	// 수정일시
    private Integer paidPoint;		// 잔여 결제 포인트 수량
    private Integer freePoint;		// 잔여 무상지급 포인트 수량
    private Integer balance;	    // 잔여 포인트(총량)
    private String resumeId;        // 이력서 일련번호
    private Integer paidIncrease;   // 결제포인트 증감
    private Integer freeIncrease;   // 무상지급 포인트 증감
    private String type;
    private int amount;
    private String registerPathCd;

    // 검색조건
    private String startDt;
    private String endDt;
    private String useType;
    private String cancelPoint;
    private String dateSearchType;
}
