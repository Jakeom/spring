package com.fw.hh.inquiry.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fw.core.common.service.CommonFileService;
import com.fw.core.dto.hh.HhInquiryDTO;
import com.fw.core.mapper.db1.hh.HhInquiryMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class HhInquiryService {

	private final HhInquiryMapper hhInquiryMapper;
	private final CommonFileService commonFileService;

	// 등록
	public void insertInquiry(HhInquiryDTO hhInquiryDTO) throws Exception {
		Integer fileSeq = commonFileService.fileUpload(hhInquiryDTO.getFiles(), "QNA");
		hhInquiryDTO.setFileSeq(String.valueOf(fileSeq));
		hhInquiryMapper.insertInquiry(hhInquiryDTO);
	}

	// 페이징 카운트
	public int selectInquiryListCntForPaging(HhInquiryDTO hhInquiryDTO) {
		return hhInquiryMapper.selectInquiryListCntForPaging(hhInquiryDTO);
	}

	// 리스트
	public List<HhInquiryDTO> selectInquiryListPaging(HhInquiryDTO hhInquiryDTO) {
		return hhInquiryMapper.selectInquiryListPaging(hhInquiryDTO);
	}

	public HhInquiryDTO selectInquiryDetail(HhInquiryDTO hhInquiryDTO) {
		HhInquiryDTO rtnDto = hhInquiryMapper.selectInquiryDetail(hhInquiryDTO);
		return rtnDto;
	}
}