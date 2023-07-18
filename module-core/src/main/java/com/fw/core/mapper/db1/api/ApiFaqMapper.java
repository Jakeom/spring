package com.fw.core.mapper.db1.api;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.fw.core.dto.api.ApiFaqDTO;

@Mapper
public interface ApiFaqMapper {
	/** FAQ 리스트 */
	List<ApiFaqDTO> selectFaqList(ApiFaqDTO apiFaqDTO);

}