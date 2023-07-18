package com.fw.core.mapper.db1.hh;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.fw.core.dto.hh.HhPickApDTO;

@Mapper
public interface HhPickApMapper {

	/** 나를 pick한 후보자 리스트 */
	List<HhPickApDTO> selectPickApList(HhPickApDTO hhPickApDTO);

	/** 나를 pick한 후보자 상세 */
	HhPickApDTO selectHrCompanyDetail(HhPickApDTO hhPickApDTO);

}
