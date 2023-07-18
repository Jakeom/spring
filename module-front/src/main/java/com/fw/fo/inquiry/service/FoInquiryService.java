package com.fw.fo.inquiry.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fw.core.common.service.CommonFileService;
import com.fw.core.dto.fo.inquiry.FoInquiryDTO;
import com.fw.core.mapper.db1.fo.inquiry.FoInquiryMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FoInquiryService {

	private final FoInquiryMapper foInquiryMapper;
	private final CommonFileService commonFileService;

	// 등록
	public void insertInquiry(FoInquiryDTO foInquiryDTO) throws Exception {
		Integer fileSeq = commonFileService.fileUpload(foInquiryDTO.getFiles(), "QNA");
		foInquiryDTO.setFileSeq(String.valueOf(fileSeq));
		foInquiryMapper.insertInquiry(foInquiryDTO);
	}

	// 페이징 카운트
	public int selectInquiryListCntForPaging(FoInquiryDTO foInquiryDTO) {
		return foInquiryMapper.selectInquiryListCntForPaging(foInquiryDTO);
	}

	// 리스트
	public List<FoInquiryDTO> selectInquiryListPaging(FoInquiryDTO foInquiryDTO) {
		return foInquiryMapper.selectInquiryListPaging(foInquiryDTO);
	}

	public FoInquiryDTO selectInquiryDetail(FoInquiryDTO foInquiryDTO) {
		FoInquiryDTO rtnDto = foInquiryMapper.selectInquiryDetail(foInquiryDTO);
		return rtnDto;
	}
}