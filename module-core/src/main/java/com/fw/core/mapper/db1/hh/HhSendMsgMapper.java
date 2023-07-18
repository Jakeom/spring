package com.fw.core.mapper.db1.hh;

import com.fw.core.dto.hh.HhSendMsgDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HhSendMsgMapper {

	void insertHhSendMsg(HhSendMsgDTO hhSendMsgDTO);
	void insertHhSendMsgTarget(HhSendMsgDTO hhSendMsgDTO);
	void insertHhSendMsgFile(HhSendMsgDTO hhSendMsgDTO);

}
