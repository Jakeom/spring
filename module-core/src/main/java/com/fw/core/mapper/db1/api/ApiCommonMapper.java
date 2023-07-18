package com.fw.core.mapper.db1.api;

import com.fw.core.dto.api.ApiCommonCdDTO;
import com.fw.core.dto.bo.BoBannerDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author yjw
 */
@Mapper
public interface ApiCommonMapper {

	/**
	 * 배너리스트 조회
	 */
	List<BoBannerDTO> selectBannerList(BoBannerDTO boBannerDTO);

	/**
	 * 공통코드 리스트 조회
	 */
	List<ApiCommonCdDTO> selectCommonCd(ApiCommonCdDTO apiCommonCdDTO);

}
