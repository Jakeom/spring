package com.fw.bo.system.homepage.service;

import com.fw.core.common.service.CommonFileService;
import com.fw.core.dto.FileDTO;
import com.fw.core.dto.bo.BoParsingErrorAcceptDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.fw.core.dto.bo.BoNoticeDTO;
import com.fw.core.mapper.db1.bo.BoNoticeMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class HomepageNoticeService {

	private final BoNoticeMapper boNoticeMapper;
	private final CommonFileService commonFileService;
	/**
	 * 공지사항 관리 리스트 취득
	 */
	public List<BoNoticeDTO> selectNoticeList(BoNoticeDTO boNoticeDTO) {
		return boNoticeMapper.selectNoticeList(boNoticeDTO);
	}
	/**
	 * 	 공지사항 상세 취득
	 */
	public BoNoticeDTO selectNoticeModify(BoNoticeDTO boNoticeDTO) {
		return boNoticeMapper.selectNoticeModify(boNoticeDTO);
	}
	/**
	 * 	 공지사항 상세 조회 취득
	 */
	public BoNoticeDTO selectNoticeDetail(BoNoticeDTO boNoticeDTO) {
		return boNoticeMapper.selectNoticeDetail(boNoticeDTO);
	}
	/**
	 * 공지사항 관리 검색
	 */
	public List<BoNoticeDTO> saerchNoticeList(BoNoticeDTO boNoticeDTO) {
		return boNoticeMapper.searchNoticeList(boNoticeDTO);
	}
	/**
	 * 공지사항 등록
	 */
	@Transactional
	public void insertNotice(BoNoticeDTO boNoticeDTO) throws Exception {
		Integer profileFileId = commonFileService.fileUpload(boNoticeDTO.getNoticeFiles(), "NOTICE");
		if(profileFileId != null){
			boNoticeDTO.setFileSeq(String.valueOf(profileFileId));
		}
		boNoticeMapper.insertNotice(boNoticeDTO);
	}

	public List<FileDTO> selectNoticeFileList(BoNoticeDTO boNoticeDTO) {
		boNoticeDTO = boNoticeMapper.selectNoticeDetail(boNoticeDTO);
		List<FileDTO> list = commonFileService.selectFileDetailList(boNoticeDTO.getFileSeq());
		return list;
	}
	/**
	 * 공지사항 수정
	 */
	public void updateNotice(BoNoticeDTO boNoticeDTO) throws Exception {
		if(boNoticeDTO.getFileSeq() != null && !boNoticeDTO.getFileSeq().equals("")) {
			commonFileService.fileUpdate(boNoticeDTO.getNoticeFiles(), "RESUME_PROFILE", boNoticeDTO.getFileSeq());
		} else {
			Integer profileFileId = commonFileService.fileUpload(boNoticeDTO.getNoticeFiles(), "NOTICE");
			if(profileFileId != null){
				boNoticeDTO.setFileSeq(String.valueOf(profileFileId));
			}
		}
		boNoticeMapper.updateNotice(boNoticeDTO);
	}
	/**
	 * 공지사항 삭제
	 */
	public void deleteNotice(BoNoticeDTO boNoticeDTO) {
		commonFileService.updateFileDetail(boNoticeDTO.getFileSeq());
		boNoticeMapper.deleteNotice(boNoticeDTO);
	}

	public void updateFile(FileDTO fileDTO) {
		commonFileService.updateFile(fileDTO);
	}

	public List<BoNoticeDTO> selectNoticeDisplayList(BoNoticeDTO boNoticeDTO) {
		return boNoticeMapper.selectNoticeDisplayList(boNoticeDTO);
	}

	public void deleteDisplayOrder(BoNoticeDTO boNoticeDTO) {
		boNoticeMapper.deleteDisplayOrder(boNoticeDTO);
	}
	public void updateDisplayOrder(BoNoticeDTO boNoticeDTO) {

		List<String> list = boNoticeDTO.getNoticeIds();

		for(int i = 0 ; i < list.size() ; i++){
			BoNoticeDTO v = new BoNoticeDTO();
			v.setNoticeSeq(list.get(i));
			v.setAdminSession(boNoticeDTO.getAdminSession());
			v.setDisplayOrder(boNoticeDTO.getDisplayPositions().get(i));
			boNoticeMapper.updateDisplayOrder(v);
		}

	}
}
