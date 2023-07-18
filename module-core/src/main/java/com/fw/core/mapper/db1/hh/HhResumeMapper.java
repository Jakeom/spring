package com.fw.core.mapper.db1.hh;

import com.fw.core.dto.hh.HhResumeDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HhResumeMapper {

	int selectResumeRejectList(HhResumeDTO hhResumeDTO);
	void insertTempResume(HhResumeDTO hhResumeDTO);

}
