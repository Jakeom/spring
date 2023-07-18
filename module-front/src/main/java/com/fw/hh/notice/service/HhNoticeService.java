package com.fw.hh.notice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fw.core.common.service.CommonFileService;
import com.fw.core.dto.hh.HhNoticeDTO;
import com.fw.core.mapper.db1.hh.HhNoticeMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class HhNoticeService {

	private final HhNoticeMapper hhNoticeMapper;
	private final CommonFileService commonFileService;

	public int selectNoticeListCntForPaging(HhNoticeDTO hhNoticeDTO) {
		return hhNoticeMapper.selectNoticeListCntForPaging(hhNoticeDTO);
	}

	public List<HhNoticeDTO> selectNoticeListPaging(HhNoticeDTO hhNoticeDTO) {
		return hhNoticeMapper.selectNoticeListPaging(hhNoticeDTO);
	}

	public HhNoticeDTO selectNoticeInfo(HhNoticeDTO hhNoticeDTO) {
		HhNoticeDTO f = hhNoticeMapper.selectNoticeInfo(hhNoticeDTO);
		f.setCommonFileList(commonFileService.selectFileDetailList(f.getFileSeq()));
		return f;
	}

	public List<HhNoticeDTO> selectNoticeList(HhNoticeDTO hhNoticeDTO) {
		return hhNoticeMapper.selectNoticeList(hhNoticeDTO);
	}

	public void updateNoticeHit(HhNoticeDTO hhNoticeDTO) {
		hhNoticeMapper.updateNoticeHit(hhNoticeDTO);
	}
}