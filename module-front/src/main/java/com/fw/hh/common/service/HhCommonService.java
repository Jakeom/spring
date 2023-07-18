package com.fw.hh.common.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.fw.core.common.service.CommonFileService;
import com.fw.core.dto.hh.HhCommonDTO;
import com.fw.core.dto.hh.HhHeadhunterDTO;
import com.fw.core.mapper.db1.hh.HhCommonMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class HhCommonService {

	private final HhCommonMapper hhCommonMapper;
	private final CommonFileService commonFileService;

	public HhCommonDTO selectInfo(HhCommonDTO hhCommonDTO) {
		return hhCommonMapper.selectInfo(hhCommonDTO);
	}

	public List<HhCommonDTO> selectBannerList(HhCommonDTO hhCommonDTO) {
		List<HhCommonDTO> list = hhCommonMapper.selectBannerList(hhCommonDTO);
		for (HhCommonDTO f : list) {
			String fileSeq = f.getFileSeq();
			if (StringUtils.isNotBlank(fileSeq)) {
				f.setCommonFileList(commonFileService.selectFileDetailList(fileSeq));
			}
		}
		return list;
	}

	public List<HhCommonDTO> selectMainDisplayList(HhCommonDTO hhCommonDTO) {
		List<HhCommonDTO> list = hhCommonMapper.selectMainDisplayList(hhCommonDTO);
		for (HhCommonDTO f : list) {
			String fileSeq = f.getFileSeq();
			if (StringUtils.isNotBlank(fileSeq)) {
				f.setCommonFileList(commonFileService.selectFileDetailList(fileSeq));
			}
		}
		return list;
	}

	public HhHeadhunterDTO selectHeadhunterInfo(HhHeadhunterDTO hhHeadhunterDTO) {

		HhHeadhunterDTO f = hhCommonMapper.selectHeadhunterInfo(hhHeadhunterDTO);
		f.setCommonFileList(commonFileService.selectFileDetailList(f.getLogoFileId()));
		return f;

	}

}