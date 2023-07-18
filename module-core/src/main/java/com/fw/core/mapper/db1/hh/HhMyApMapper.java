package com.fw.core.mapper.db1.hh;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.fw.core.dto.hh.HhMyapHistoryGroupDTO;
import com.fw.core.dto.hh.HhMyapListDTO;
import com.fw.core.dto.hh.HhPointDTO;
import com.fw.core.dto.hh.HhPointUseHistoryDTO;
import com.fw.core.dto.hh.HhRewardDTO;
import com.fw.core.dto.hh.HhRewardHistoryDTO;

@Mapper
public interface HhMyApMapper {

	int selectHhMyapListCount(HhMyapListDTO hhMyapListDTO);

	List<HhMyapListDTO> selectHhMyapList(HhMyapListDTO hhMyapListDTO);

	int insertMyapgroup(HhMyapHistoryGroupDTO hhMyapHistoryGroupDTO);

	List<HhMyapHistoryGroupDTO> selectHhMyapHistoryGroupList(HhMyapHistoryGroupDTO hhMyapHistoryGroupDTO);

	int updateHhMyapListGorup(HhMyapListDTO hhMyapListDTO);

	int updateHhMyapListRegistPath(HhMyapListDTO hhMyapListDTO);

	int updateHhMyapListRegisterPathCd(HhMyapListDTO hhMyapListDTO);

	int updateHhMyapListResumeOpend(HhMyapListDTO hhMyapListDTO);

	HhRewardDTO selectReward(HhRewardDTO foSessionDTO);

	int insertReward(HhRewardDTO hhRewardDTO);

	int updateReward(HhRewardDTO hhRewardDTO);

	int insertRewardHistory(HhRewardHistoryDTO hhRewardHistoryDTO);

	HhPointDTO selectPoint(HhPointDTO foSessionDTO);

	int insertPoint(HhPointDTO hhPointDTO);

	int updatePoint(HhPointDTO hhPointDTO);

	int insertPointUseHistory(HhPointUseHistoryDTO hhPointUseHistoryDTO);

	List<HhMyapListDTO> selectHhMyapExpiredList();

	List<HhMyapListDTO> selectMyApListPaging(HhMyapListDTO hhMyapListDTO); // 메인 내 인재 리스트 취득

}
