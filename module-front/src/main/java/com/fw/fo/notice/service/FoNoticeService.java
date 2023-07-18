package com.fw.fo.notice.service;

import java.util.List;

import com.fw.core.common.service.CommonFileService;
import org.springframework.stereotype.Service;

import com.fw.core.dto.fo.notice.FoNoticeDTO;
import com.fw.core.mapper.db1.fo.notice.FoNoticeMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FoNoticeService {

	private final FoNoticeMapper foNoticeMapper;
	private final CommonFileService commonFileService;

	public int selectNoticeListCntForPaging(FoNoticeDTO foNoticeDTO) {
		return foNoticeMapper.selectNoticeListCntForPaging(foNoticeDTO);
	}

	public List<FoNoticeDTO> selectNoticeListPaging(FoNoticeDTO foNoticeDTO) {
		return foNoticeMapper.selectNoticeListPaging(foNoticeDTO);
	}

	public FoNoticeDTO selectNoticeInfo(FoNoticeDTO foNoticeDTO) {
		FoNoticeDTO f = foNoticeMapper.selectNoticeInfo(foNoticeDTO);
		f.setCommonFileList(commonFileService.selectFileDetailList(f.getFileSeq()));
		return f;
	}

	public List<FoNoticeDTO> selectNoticeList(FoNoticeDTO foNoticeDTO) {
		return foNoticeMapper.selectNoticeList(foNoticeDTO);
	}

	public void updateNoticeHit(FoNoticeDTO foNoticeDTO) {
		foNoticeMapper.updateNoticeHit(foNoticeDTO);
	}
}