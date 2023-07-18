package com.fw.core.mapper.db1.hh;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.fw.core.dto.hh.HhFaqDTO;

@Mapper
public interface HhFaqMapper {

	// 카테고리 리스트 취득
	public List<HhFaqDTO> selectFaqCateList(HhFaqDTO hhFaqDTO);

	// 카테고리 리스트 취득
	public List<HhFaqDTO> selectFaqList(HhFaqDTO hhFaqDTO);

	// 카테고리 리스트 취득
	public List<HhFaqDTO> selectMainFaqList(HhFaqDTO hhFaqDTO);

}
