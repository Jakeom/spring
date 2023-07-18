package com.fw.core.mapper.db1.bo;

import java.util.List;

import com.fw.core.dto.bo.BoPositionBanWordDTO;
import com.fw.core.dto.bo.BoPositionDTO;
import org.apache.ibatis.annotations.Mapper;

import com.fw.core.dto.bo.BoRecruitDTO;

/**
 * BO Recruit 인터페이스
 *
 * @author 가잘
 */
@Mapper
public interface BoRecruitMapper {

	/**
	 * 채용 리스트 취득
	 */
	List<BoRecruitDTO> selectRecruitList(BoRecruitDTO boRecruitDTO);

	List<BoRecruitDTO> selectParticipantName(BoRecruitDTO boRecruitDTO);

	/**
	 * 채용 상세조회 취득
	 */
	BoRecruitDTO selectRecruitDetail(BoRecruitDTO boRecruitDTO);

	/**
	 * 채용 검색
	 */
	List<BoRecruitDTO> searchRecruitList(BoRecruitDTO boRecruitDTO);

	/**
	 * 채용 상태 확인
	 * */
	List<BoRecruitDTO> selectCoworkRecruitList(BoRecruitDTO boRecruitDTO);

	List<BoPositionBanWordDTO> selectPositionBanWord(BoPositionBanWordDTO boPositionBanWordDTO);

	int searchRecruitListCnt(BoRecruitDTO boRecruitDTO);

	void insertPositionBanWord(BoPositionBanWordDTO boPositionBanWordDTO);

	void updatePositionBanWord(BoPositionBanWordDTO boPositionBanWordDTO);

	/**
	 * 채용 수정
	 */
	void updateRecruit(BoRecruitDTO boRecruitDTO);
}
