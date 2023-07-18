package com.fw.core.mapper.db1.hh;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.fw.core.dto.hh.HhWefirmDTO;

@Mapper
public interface HhWefirmManageMapper {

	List<HhWefirmDTO> selectHeadhunterList(HhWefirmDTO hhWefirmDTO);

	HhWefirmDTO selectWefirmDetail(HhWefirmDTO hhWefirmDTO);

	void insertWefirmAdmin(HhWefirmDTO hhWefirmDTO);

	void deleteWefirmAdmin(HhWefirmDTO hhWefirmDTO);

	void updateWefirm(HhWefirmDTO hhWefirmDTO);

	void insertWefirmRequestHistory(HhWefirmDTO hhWefirmDTO);

	boolean selectWefirmRequestDuplicateCheck(HhWefirmDTO hhWefirmDTO);

	int selectHeadhunterListCnt(HhWefirmDTO hhWefirmDTO);

	void deleteHeadhunter(HhWefirmDTO hhWefirmDTO);

	List<HhWefirmDTO> selectNoWefirmHeadhunterList(HhWefirmDTO hhWefirmDTO);

	int selectNoWefirmHeadhunterListCnt(HhWefirmDTO hhWefirmDTO);

	void approvalHeadhunter(HhWefirmDTO hhWefirmDTO);

	void updateHeadhunter(HhWefirmDTO hhWefirmDTO);
}
