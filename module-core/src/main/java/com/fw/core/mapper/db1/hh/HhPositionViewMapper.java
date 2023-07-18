package com.fw.core.mapper.db1.hh;

import org.apache.ibatis.annotations.Mapper;

import com.fw.core.dto.hh.HhCompanyDTO;

@Mapper
public interface HhPositionViewMapper {

	HhCompanyDTO selectCompanyDetail(HhCompanyDTO hhCompanyDTO);
	HhCompanyDTO selectPositionDetail(HhCompanyDTO hhCompanyDTO);
	HhCompanyDTO selectPositionDetailByPositionId(HhCompanyDTO hhCompanyDTO);
	HhCompanyDTO selectCompanyDetailByPositionId(HhCompanyDTO hhCompanyDTO);

}
