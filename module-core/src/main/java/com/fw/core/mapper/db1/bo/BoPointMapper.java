package com.fw.core.mapper.db1.bo;

import com.fw.core.dto.bo.BoPointUseHistoryDTO;
import com.fw.core.dto.bo.BoRewardHistoryDTO;
import com.fw.core.dto.bo.BoRewardMallDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * BO Reward 인터페이스
 *
 * @author 가잘
 */
@Mapper
public interface BoPointMapper {

	/**
	 * 포인트 리스트 취득
	 */
	List<BoPointUseHistoryDTO> selectPointList(BoPointUseHistoryDTO boPointUseHistoryDTO);

	BoPointUseHistoryDTO selectPointMemberDetail(BoPointUseHistoryDTO boPointUseHistoryDTO);

	/**
	 *  포인트 검색
	 */
	List<BoPointUseHistoryDTO> searchPointList(BoPointUseHistoryDTO boPointUseHistoryDTO);

	/**
	 *  포인트 상태 검색
	 */
	List<BoPointUseHistoryDTO> searchStatus(BoPointUseHistoryDTO boPointUseHistoryDTO);


	/**
	 *  상품 리스트
	 */
	List<BoRewardMallDTO> selectRewardMallList(BoRewardMallDTO boRewardMallDTO);
	/**
	 *  상품 등록
	 */
	void insertRewardMall(BoRewardMallDTO boRewardMallDTO);
	/**
	 * 상품 상세
	 */
    BoRewardMallDTO selectRewardDetail(BoRewardMallDTO boRewardMallDTO);
	/**
	 * 상품 정보 수정
	 */
	void updateReward(BoRewardMallDTO boRewardMallDTO);
	/**
	 * 상품 정보 삭제
	 */
	void deleteReward(BoRewardMallDTO boRewardMallDTO);
	void rewardSendUpdate(BoRewardHistoryDTO boRewardHistoryDTO);

	void insertPointUseHistory(BoPointUseHistoryDTO boPointUseHistoryDTO);
	void updatePointBalance(BoPointUseHistoryDTO boPointUseHistoryDTO);

	void insertPoint(BoPointUseHistoryDTO boPointUseHistoryDTO);
	/**
	 * 상품 검색
	 */
	List<BoRewardHistoryDTO> searchRewardMall(BoRewardMallDTO boRewardMallDTO);
	/**
	 * 리워드 상세 (신청현황)
	 */
	List<BoRewardHistoryDTO> selectOrderDetail(BoRewardHistoryDTO boRewardHistoryDTO);

	List<BoPointUseHistoryDTO> selectPointDetail(BoPointUseHistoryDTO boPointHistoryDTO);

	int selectHavePointCnt(BoPointUseHistoryDTO boPointUseHistoryDTO);
}
