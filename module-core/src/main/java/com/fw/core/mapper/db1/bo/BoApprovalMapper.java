package com.fw.core.mapper.db1.bo;

import com.fw.core.dto.bo.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * BO Payment 인터페이스
 *
 * @author 가잘
 */
@Mapper
public interface BoApprovalMapper {
	/**
	 * 헤드한터 리스트
	 */
	List<BoHhApprovalRequestDTO> selectHeadhunterRequestList(BoHhApprovalRequestDTO boHhApprovalRequestDTO);
	/**
	 * 헤드한터 검색
	 */
	List<BoHhApprovalRequestDTO> saerchHeadhunterRequestList(BoHhApprovalRequestDTO boHhApprovalRequestDTO);
	/**
	 * 헤드한터 상태 확인
	 */
	List<BoHhApprovalRequestDTO> searchHeadhunterStatus(BoHhApprovalRequestDTO boHhApprovalRequestDTO);
	/**
	 * 헤드한터 상태 거절
	 */
	void updateRejectRequest(BoHhApprovalRequestDTO boHhApprovalRequestDTO);
	/**
	 * 헤드한터 상태 승인
	 */
	void updateAcceptRequest(BoHhApprovalRequestDTO boHhApprovalRequestDTO);

	/**
	 * 서치펌 상태 확인
	 */
	List<BoSfChangeRequestDTO> searchSFStatus(BoSfChangeRequestDTO boSfChangeRequestDTO);
	/**
	 * 서치펌 검색
	 */
	List<BoSfChangeRequestDTO> saerchSFRequestList(BoSfChangeRequestDTO boSfChangeRequestDTO);
	/**
	 * 서치펌 리스트
	 */
	List<BoSfChangeRequestDTO> selectSFRequestList(BoSfChangeRequestDTO boSfChangeRequestDTO);
	/**
	 * 서치펌 상채 승인
	 */
	void updateSFAcceptRequest(BoSfChangeRequestDTO boSfChangeRequestDTO);
	/**
	 * 서치펌 상태 거절
	 */
	void updateSFRejectRequest(BoSfChangeRequestDTO boSfChangeRequestDTO);

	/**
	 * 위펌 리스트
	 */
	List<BoWefirmRequestHistoryDTO> selectWefirmRequestList(BoWefirmRequestHistoryDTO boWefirmRequestHistoryDTO);
	/**
	 * 위펌 검색
	 */
	List<BoWefirmRequestHistoryDTO> saerchWefirmRequestList(BoWefirmRequestHistoryDTO boWefirmRequestHistoryDTO);
	/**
	 * 위펌 상태 확인
	 */
	List<BoWefirmRequestHistoryDTO> searchWefirmStatus(BoWefirmRequestHistoryDTO boWefirmRequestHistoryDTO);
	/**
	 * 위펌 상태 거절
	 */
	void updateWefirmRejectRequest(BoWefirmRequestHistoryDTO boWefirmRequestHistoryDTO);
	/**
	 * 위펌 상태 승인
	 */
	void updateWefirmAcceptRequest(BoWefirmRequestHistoryDTO boWefirmRequestHistoryDTO);

	void updateWefirmApprovalHh(BoWefirmRequestHistoryDTO boWefirmRequestHistoryDTO);

	void updateWefirmApproval(BoWefirmRequestHistoryDTO boWefirmRequestHistoryDTO);

	void updateWefirmRejectHh(BoWefirmRequestHistoryDTO boWefirmRequestHistoryDTO);
	void updateWefirmReject(BoWefirmRequestHistoryDTO boWefirmRequestHistoryDTO);

	void updateHhApproved(BoHhApprovalRequestDTO boHhApprovalRequestDTO);
	/**
	 * wefirm_request_history 조회 by id
	 */
	BoWefirmRequestHistoryDTO selectWefirmRequestHistory(BoWefirmRequestHistoryDTO boWefirmRequestHistoryDTO);
	/**
	 * 위펌 관리자 조회
	 */
	List<BoWefirmRequestHistoryDTO> selectWefirmAdminList(BoWefirmRequestHistoryDTO boWefirmRequestHistoryDTO);

    BoHhApprovalRequestDTO selecthhRequestDetail(BoHhApprovalRequestDTO boHhApprovalRequestDTO);

    BoHhApprovalRequestDTO searchHeadhunter(BoHhApprovalRequestDTO boHhApprovalRequestDTO);

    BoSfChangeRequestDTO selectSearchFirmDetail(BoSfChangeRequestDTO boSfChangeRequestDTO);

    BoSfChangeRequestDTO selectSFRequest(BoSfChangeRequestDTO boSfChangeRequestDTO);

	BoWefirmRequestHistoryDTO selectWefirmDetail(BoWefirmRequestHistoryDTO boWefirmRequestHistoryDTO);
}
