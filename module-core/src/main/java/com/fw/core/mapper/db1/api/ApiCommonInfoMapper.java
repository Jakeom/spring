package com.fw.core.mapper.db1.api;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.fw.core.dto.api.ApiInfoDTO;

/**
 * 
 * @author wsh
 */
@Mapper
public interface ApiCommonInfoMapper {

	/**
	 * 약관 조회
	 */
	List<ApiInfoDTO> selectCommonInfo(ApiInfoDTO apiInfoDTO);

}
