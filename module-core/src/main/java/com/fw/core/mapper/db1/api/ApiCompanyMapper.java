package com.fw.core.mapper.db1.api;

import org.apache.ibatis.annotations.Mapper;
import com.fw.core.dto.api.ApiCompanyDTO;

/**
 * Api COMMUNITY
 * 
 * @author WSH
 */
@Mapper
public interface ApiCompanyMapper {

    /**
	 * 게시글 리스트 조회
	 */
	ApiCompanyDTO selectCompanyDetail(ApiCompanyDTO apiCompanyDTO);
    
}
