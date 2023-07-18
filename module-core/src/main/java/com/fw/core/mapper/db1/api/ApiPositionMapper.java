package com.fw.core.mapper.db1.api;

import com.fw.core.dto.api.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Api POSITION
 * 
 * @author YJW
 */
@Mapper
public interface ApiPositionMapper {

	/**
	 * 포지션(채용공고) 리스트 조회
	 */
	List<ApiPositionDTO> selectPositionList (ApiPositionDTO apiPositionDTO);

	/**
	 * 헤드헌터 포지션(채용공고) 리스트 조회
	 */
	List<ApiPositionDTO> selectHhPositionList(ApiPositionDTO apiPositionDTO);

	/**
	 * 포지션(채용공고) 리스트 조회 (내가 지원한 리스트)
	 */
	List<ApiPositionDTO> selectApplyPositionList(ApiPositionDTO apiPositionDTO);

	/**
	 * 포지션(채용공고) 리스트 조회 (내가 스크랩한 리스트)
	 */
	List<ApiPositionDTO> selectScrapPositionList(ApiPositionDTO apiPositionDTO);

	/**
	 * 포지션(채용공고) 리스트 조회 (Contact한 리스트)
	 */
	List<ApiPositionDTO> selectContactPositionList(ApiPositionDTO apiPositionDTO);

	List<ApiPositionDTO> selectPositionDetail(ApiPositionDTO apiPositionDTO);
	/**
	 * 포지션(채용공고) 리스트 COUNT
	 */
	int selectPositionCnt(ApiPositionDTO apiPositionDTO);
	int selectHhPositionCnt(ApiPositionDTO apiPositionDTO);
	int selectApplyPositionCnt(ApiPositionDTO apiPositionDTO);
	int selectScrapPositionCnt(ApiPositionDTO apiPositionDTO);
	int selectContactPositionCnt(ApiPositionDTO apiPositionDTO);

	/**
	 * 포지션 상세 조회
	 */
	ApiPositionDTO selectPositionInfo(ApiPositionDTO apiPositionDTO);

	/**
	 * 포지션(채용공고) 수정 (마감일연장)
	 */
	void updatePosition(ApiPositionDTO apiPositionDTO);

	/**
	 * 포지션(채용공고) 스크랩 여부 확인
	 */
	boolean selectIsScrap(ApiScrapPositionDTO apiScrapPositionDTO);

	/**
	 * 포지션(채용공고) 스크랩
	 */
	void insertScrap(ApiScrapPositionDTO apiScrapPositionDTO);

	/**
	 * 포지션(채용공고) 스크랩 취소
	 */
	void updateScrap(ApiScrapPositionDTO apiScrapPositionDTO);

	/**
	 * 포지션(채용공고) 열람여부 확인
	 */
	boolean selectIsRead(ApiPositionReadingHistoryDTO apiPositionReadingHistoryDTO);

	/**
	 * 포지션(채용공고) 열람이력 등록
	 */
	void insertPositionHistory(ApiPositionReadingHistoryDTO apiPositionReadingHistoryDTO);

	/**
	 * 포지션(채용공고) 열람처리일 경우 -> 최근 열람 시간 갱신
	 */
	void updatePositionHistory(ApiPositionReadingHistoryDTO apiPositionReadingHistoryDTO);

	/**
	 * 포지션(채용공고) 열람 이력 조회 / COUNT
	 */
	List<ApiPositionReadingHistoryDTO> selectPositionReadingHistory(ApiPositionReadingHistoryDTO apiPositionReadingHistoryDTO);
	int selectPositionReadingHistoryCnt(ApiPositionReadingHistoryDTO apiPositionReadingHistoryDTO);

	/**
	 * 포지션(채용공고) 지원
	 */
	void insertPositionApply(ApiPositionApplicantDTO apiPositionApplicantDTO);

	/**
	 * 포지션(채용공고) 지원자 조회
	 */
	List<ApiPositionApplicantDTO> selectPositionApplyList(ApiPositionApplicantDTO apiPositionApplicantDTO);

	/**
	 * 포지션(채용공고) 채용현황 조회 (headhunter)
	 */
	ApiPositionStatusDTO selectPositionStatus(ApiPositionStatusDTO apiPositionStatusDTO);

	/**
	 * 이력서 수정권한 여부 설정
	 */
	void updateModifyFlag(ApiPositionApplicantDTO apiPositionApplicantDTO);

	ApiPositionApplicantDTO selectPositionHh(ApiPositionApplicantDTO apiPositionApplicantDTO);

	boolean selectPassReviewExists(ApiPositionDTO apiPositionDTO);

	/**
	 * 마감 연장 횟수 조회
	 */
	Integer selectEndAddCnt(ApiPositionDTO apiPositionDTO);
}
