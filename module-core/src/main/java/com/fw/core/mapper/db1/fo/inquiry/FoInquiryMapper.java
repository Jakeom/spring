package com.fw.core.mapper.db1.fo.inquiry;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.fw.core.dto.fo.inquiry.FoInquiryDTO;

@Mapper
public interface FoInquiryMapper {

	// 등록
	public void insertInquiry(FoInquiryDTO foInquiryDTO);

	// 페이징 카운트
	public int selectInquiryListCntForPaging(FoInquiryDTO foInquiryDTO);

	// 리스트
	public List<FoInquiryDTO> selectInquiryListPaging(FoInquiryDTO foInquiryDTO);

	public FoInquiryDTO selectInquiryDetail(FoInquiryDTO foInquiryDTO);
}
