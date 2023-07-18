package com.fw.core.mapper.db1.api;

import com.fw.core.dto.api.ApiCommonCdDTO;
import com.fw.core.dto.api.ApiCommunityDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Api COMMUNITY
 * 
 * @author WSH
 */
@Mapper
public interface ApiCommunityMapper {

	/**
	 * 커뮤니티 카테고리 목록 조회
	 */
	List<ApiCommonCdDTO> selectCommunityCategoryList(ApiCommonCdDTO apiCommonCdDTO);

	/**
	 * 게시글 리스트 조회
	 */
	List<ApiCommunityDTO> selectCommunityContentList(ApiCommunityDTO apiCommunityDTO);

	/**
	 * 게시글 리스트 카운트
	 */
	int selectCommunityContentCnt(ApiCommunityDTO apiCommunityDTO);
}
