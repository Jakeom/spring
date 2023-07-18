package com.fw.core.mapper.db1.hh;

import com.fw.core.dto.hh.HhPositionDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HhPositionMapper {

	int selectHhPositionCount(HhPositionDTO hhPositionDTO);

	List<HhPositionDTO> selectHhPositionList(HhPositionDTO hhPositionDTO);

	List<HhPositionDTO> selectRegPositionList(HhPositionDTO hhPositionDTO);

	void insertPosition(HhPositionDTO hhPositionDTO);

	void insertPositionCompanyInfo(HhPositionDTO hhPositionDTO);

	HhPositionDTO selectPositionCompanyInfo(HhPositionDTO hhPositionDTO);

	void insertPositionParticipant(HhPositionDTO hhPositionDTO);

	List<HhPositionDTO> selectWeFirmCoworkerList(HhPositionDTO hhPositionDTO);

	HhPositionDTO selectPosition(HhPositionDTO hhPositionDTO);

	List<HhPositionDTO> selectPositionApplicant(HhPositionDTO hhPositionDTO);

	void updatePositionDeadline(HhPositionDTO hhPositionDTO);

	List<HhPositionDTO> selectPositionCoworkerList(HhPositionDTO hhPositionDTO);

	HhPositionDTO selectPositionStatus(HhPositionDTO hhPositionDTO);

	void updatePositionEnd(HhPositionDTO hhPositionDTO);

	void updateCoworkerEnd(HhPositionDTO hhPositionDTO);

	void deleteContactList(HhPositionDTO hhPositionDTO);

	void updateProposalStatus(HhPositionDTO hhPositionDTO);

	void updateProgressStep(HhPositionDTO hhPositionDTO);

	void updateProgressStatus(HhPositionDTO hhPositionDTO);

	List<HhPositionDTO> selectPositionApplicantBySpecial(HhPositionDTO hhPositionDTO);

	void updatePositionApplicant(HhPositionDTO hhPositionDTO);

	List<HhPositionDTO> selectCompanyHrManagerList(HhPositionDTO hhPositionDTO);

	void insertRecommendEmail(HhPositionDTO hhPositionDTO);

	HhPositionDTO selectRecommendEmailDetail(HhPositionDTO hhPositionDTO);

	void updatePositionDelete(HhPositionDTO hhPositionDTO);

	boolean selectRecommendDuplicate(HhPositionDTO hhPositionDTO);
	List<HhPositionDTO> selectPositionBanWordList(HhPositionDTO hhPositionDTO);

	void updateShowHist(HhPositionDTO hhPositionDTO);

	void updateRemainFail(HhPositionDTO hhPositionDTO);

	List<HhPositionDTO> selectRemainNotFail(HhPositionDTO hhPositionDTO);
}
