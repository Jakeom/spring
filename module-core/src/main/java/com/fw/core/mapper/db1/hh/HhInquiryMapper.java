package com.fw.core.mapper.db1.hh;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.fw.core.dto.hh.HhInquiryDTO;

@Mapper
public interface HhInquiryMapper {

	// 등록
	public void insertInquiry(HhInquiryDTO hhInquiryDTO);

	// 페이징 카운트
	public int selectInquiryListCntForPaging(HhInquiryDTO hhInquiryDTO);

	// 리스트
	public List<HhInquiryDTO> selectInquiryListPaging(HhInquiryDTO hhInquiryDTO);

	public HhInquiryDTO selectInquiryDetail(HhInquiryDTO hhInquiryDTO);
}
