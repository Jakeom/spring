package com.fw.core.mapper.db1.fo;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.fw.core.dto.fo.FoInterestHhDTO;

@Mapper
public interface FoInterestHhMapper {

	/** Front 관심 헤드헌터 리스트 */
	List<FoInterestHhDTO> selectInterestHhList(FoInterestHhDTO foInterestHhDTO);

	/** Front 관심 헤드헌터 상세 */
	FoInterestHhDTO attentionHeadhunterDetail(FoInterestHhDTO foInterestHhDTO);

	/** Front 관심 헤드헌터 관심해제 */
	void attentionHeadhunterDelete(FoInterestHhDTO foInterestHhDTO);
}
