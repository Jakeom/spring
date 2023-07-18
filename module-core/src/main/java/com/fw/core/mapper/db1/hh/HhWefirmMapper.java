package com.fw.core.mapper.db1.hh;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.fw.core.dto.hh.HhWefirmDTO;

@Mapper
public interface HhWefirmMapper {

	HhWefirmDTO selectHeadhunter(HhWefirmDTO hhWefirmDTO);

	int selectWefirmRegisterCnt(HhWefirmDTO hhWefirmDTO);

	void insertWefirm(HhWefirmDTO hhWefirmDTO);

	List<HhWefirmDTO> selectWefirmJoinRequestList(HhWefirmDTO hhWefirmDTO);

	void deleteCancelWefirmJoin(HhWefirmDTO hhWefirmDTO);

	void insertWefirmJoinRequest(HhWefirmDTO hhWefirmDTO);

	int selectWefirmListCnt(HhWefirmDTO hhWefirmDTO);

	int selectWefirmJoinStatusCnt(HhWefirmDTO hhWefirmDTO);

	int selectWefirmUrlDuplicateCnt(HhWefirmDTO hhWefirmDTO);

	List<HhWefirmDTO> selectWefirmList(HhWefirmDTO hhWefirmDTO);

	HhWefirmDTO selectWefirmDetail(HhWefirmDTO hhWefirmDTO);

	void insertWefirmRequestHistory(HhWefirmDTO hhWefirmDTO);

	List<HhWefirmDTO> selectWefirmAdminList(HhWefirmDTO hhWefirmDTO);

	int selectWefirmHhCnt(HhWefirmDTO hhWefirmDTO);

}
