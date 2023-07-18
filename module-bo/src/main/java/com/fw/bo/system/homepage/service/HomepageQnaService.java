package com.fw.bo.system.homepage.service;

import java.util.List;

import com.fw.core.common.service.CommonFileService;
import com.fw.core.dto.FileDTO;
import com.fw.core.dto.fo.resume.FoResumeDTO;
import org.springframework.stereotype.Service;

import com.fw.core.dto.bo.BoQnaDTO;
import com.fw.core.mapper.db1.bo.BoQnaMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HomepageQnaService {

	private final BoQnaMapper boQnaMapper;
	private final CommonFileService commonFileService;
	/**
	 * 1:1 문의관리 리스트
	 */
	public List<BoQnaDTO> selectQnaList(BoQnaDTO boQnaDTO) {
		return boQnaMapper.selectQnaList(boQnaDTO);
	}
	/**
	 * 위펌 상세 취득
	 */
	public BoQnaDTO selectQnaModify(BoQnaDTO boQnaDTO) {
		BoQnaDTO b = boQnaMapper.selectQnaModify(boQnaDTO);
		b.setCommonFileList(commonFileService.selectFileDetailList(b.getAttachFileId()));
		return b;
	}
	/**
	 * 상세 조회 취득
	 */
	public BoQnaDTO selectQnaDetail(BoQnaDTO boQnaDTO) {
		BoQnaDTO b = boQnaMapper.selectQnaDetail(boQnaDTO);
		b.setCommonFileList(commonFileService.selectFileDetailList(b.getAttachFileId()));
		return b;
	}

	/**
	 * 1:1 문의관리 검색
	 */
	public List<BoQnaDTO> saerchQnaList(BoQnaDTO boQnaDTO) {
		return boQnaMapper.searchQnaList(boQnaDTO);
	}
	/**
	 * 1:1 문의관리 수정
	 */
	public void updateQna(BoQnaDTO boQnaDTO) throws Exception {

		if (boQnaDTO.getAttachFileId()!=null ) {
			commonFileService.fileUpdate(boQnaDTO.getQnaFiles(), "QNA_IMG" , boQnaDTO.getAttachFileId());
		}
		else{
			Integer attachFileId = commonFileService.fileUpload(boQnaDTO.getQnaFiles(), "QNA");
			if(attachFileId != null){
				boQnaDTO.setAttachFileId(String.valueOf(attachFileId));
			}
		}

		boQnaMapper.updateQna(boQnaDTO);
	}

	public void updateFile(FileDTO fileDTO) {
		commonFileService.updateFile(fileDTO);
	}
}
