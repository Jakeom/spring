package com.fw.core.dto.bo;
import com.fw.core.dto.CommonDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * point_use_history DTO
 *
 * @author WSH
 */
@Getter
@Setter
public class BoPointUseHistoryDTO extends CommonDTO {

	private String id;         	  	 // 사용내역 일련번호
	private String createdAt;     	 // 생성일시
    private String updatedAt;     	 // 수정일시
    private String payment_id;		 // 결제내역 일련번호
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

    //검색 조건
    private String memberName;
    private String memberPhone;
    private String memberEmail;
    private String searchStart;
    private String searchEnd;
    private String startDate;
    private String endDate;
    private String freePayType;
    private String paidPayType;
    private String pointType;
    private String pointStartDt;
    private String pointEndDt;

    private String option;
}
