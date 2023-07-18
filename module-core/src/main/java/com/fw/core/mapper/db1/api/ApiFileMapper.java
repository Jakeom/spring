package com.fw.core.mapper.db1.api;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.fw.core.dto.api.ApiFileDTO;

@Mapper
public interface ApiFileMapper {
	/** File insert */
	void insertFile(ApiFileDTO apiFileDTO);

	/** DetailFile insert */
	void insertDetail(ApiFileDTO apiFileDTO);

	/** 상세파일 delflag update */
	void updateDetail(ApiFileDTO apiFileDTO);

	/** list */
	List<ApiFileDTO> selectDetailList(ApiFileDTO apiFileDTO);

	/** for download */
	ApiFileDTO selectDetail(ApiFileDTO apiFileDTO);

	/** deleteDetail */
	ApiFileDTO deleteDetail(ApiFileDTO apiFileDTO);
}