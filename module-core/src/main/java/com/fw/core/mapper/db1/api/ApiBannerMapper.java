package com.fw.core.mapper.db1.api;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.fw.core.dto.api.ApiBannerDTO;

@Mapper
public interface ApiBannerMapper {

	/** 배너 리스트 */
	List<ApiBannerDTO> selectBannerList(ApiBannerDTO apiBannerDTO);

}