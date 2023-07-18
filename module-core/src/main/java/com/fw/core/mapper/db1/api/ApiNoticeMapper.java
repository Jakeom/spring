package com.fw.core.mapper.db1.api;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.fw.core.dto.api.ApiNoticeDTO;

/**
 * 
 * @author wsh
 */
@Mapper
public interface ApiNoticeMapper {

	/**
	 * 공지 리스트 취득
	 */
	List<ApiNoticeDTO> selectNoticeList(ApiNoticeDTO apiNoticeDTO);

}
