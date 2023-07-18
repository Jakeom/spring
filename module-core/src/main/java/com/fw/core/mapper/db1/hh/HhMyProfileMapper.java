package com.fw.core.mapper.db1.hh;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.fw.core.dto.hh.HhMyProfileDTO;

@Mapper
public interface HhMyProfileMapper {
	HhMyProfileDTO selectMyProfileInfo(HhMyProfileDTO hhMyProfileDTO);

	List<HhMyProfileDTO> selectHhPositionFieldList(HhMyProfileDTO hhMyProfileDTO);

	void updateProfileSetting(HhMyProfileDTO hhMyProfileDTO);

	void updateMember(HhMyProfileDTO hhMyProfileDTO);

	void deletePositionField(HhMyProfileDTO hhMyProfileDTO);

	void insertPositionField(HhMyProfileDTO hhMyProfileDTO);

	void updateSignature(HhMyProfileDTO hhMyProfileDTO);

	List<HhMyProfileDTO> selectMyLogList(HhMyProfileDTO hhMyProfileDTO);

	int selectMyLogListCnt(HhMyProfileDTO hhMyProfileDTO);

	void insertHist(HhMyProfileDTO hhMyProfileDTO);
}
