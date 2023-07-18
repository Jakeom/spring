package com.fw.core.mapper.db1.bo;

import com.fw.core.dto.bo.BoTicketUseHistoryDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * BO Ticket 인터페이스
 *
 * @author 가잘
 */
@Mapper
public interface BoTicketMapper {

	/**
	 * 공지 리스트 취득
	 */
	List<BoTicketUseHistoryDTO> selectTicketList(BoTicketUseHistoryDTO boTicketUseHistoryDTO);

	/**
	 * 결제 검색
	 */
	List<BoTicketUseHistoryDTO> searchTicketList(BoTicketUseHistoryDTO boTicketUseHistoryDTO);

	/**
	 * 결제 검색
	 */
	List<BoTicketUseHistoryDTO> searchStatus(BoTicketUseHistoryDTO boTicketUseHistoryDTO);




}
