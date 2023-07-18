package com.fw.core.mapper.db1.bo;

import com.fw.core.dto.bo.BoPaymentDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * BO Payment 인터페이스
 *
 * @author 가잘
 */
@Mapper
public interface BoPaymentMapper {

	/**
	 * 공지 리스트 취득
	 */
	List<BoPaymentDTO> selectPaymentList(BoPaymentDTO boPaymentDTO);

	/**
	 * 결제 검색
	 */
	List<BoPaymentDTO> searchPaymentList(BoPaymentDTO boPaymentDTO);

	/**
	 * 결제 상태 확인
	 */
	List<BoPaymentDTO> searchStatus(BoPaymentDTO boPaymentDTO);

	void updatePayment(BoPaymentDTO boPaymentDTO);
	void updatePoint(BoPaymentDTO boPaymentDTO);

	void insertPointUseHistory(BoPaymentDTO boPaymentDTO);

	public BoPaymentDTO selectPurchasePointInfo(BoPaymentDTO boPaymentDTO);

}
