package com.fw.core.mapper.db1.fo;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.fw.core.dto.PushHistDTO;

@Mapper
public interface FoChatMapper {
	List<PushHistDTO> selectAlarmList(PushHistDTO pushHistDTO);

	void updateAlarmReceive(PushHistDTO pushHistDTO);

	PushHistDTO selectAlarmCnt(PushHistDTO pushHistDTO);
	int selectAlarmCntIncludeChat(PushHistDTO pushHistDTO);
}
