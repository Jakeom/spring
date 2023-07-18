package com.fw.core.mapper.db1.bo;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.fw.core.dto.bo.BoBannerDTO;

@Mapper
public interface BoBannerMapper {

	/** 배너관리 리스트 */
	List<BoBannerDTO> selectBannerList(BoBannerDTO boBannerDTO);

	/** 배너관리 상세 */
	BoBannerDTO selectBannerDetail(BoBannerDTO boBannerDTO);

	/** 배너관리 Modify */
	BoBannerDTO selectBannerModify(BoBannerDTO boBannerDTO);

	/** 배너관리 등록 */
	void insertBanner(BoBannerDTO boBannerDTO);

	/** 배너관리 수정 */
	void updateBanner(BoBannerDTO boBannerDTO);

	/** 배너관리 삭제 */
	void deleteBanner(BoBannerDTO boBannerDTO);
}
