package com.fw.bo.system.screen.service;

import java.util.List;

import com.fw.core.common.service.CommonFileService;
import com.fw.core.dto.FileDTO;
import org.springframework.stereotype.Service;

import com.fw.core.dto.bo.BoBannerDTO;
import com.fw.core.mapper.db1.bo.BoBannerMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScreenBannerService {

	private final BoBannerMapper boBannerMapper;
	private final CommonFileService commonFileService;

	public List<BoBannerDTO> selectBannerList(BoBannerDTO boBannerDTO) {
		return boBannerMapper.selectBannerList(boBannerDTO);
	}

	public BoBannerDTO selectBannerDetail(BoBannerDTO boBannerDTO) {
		return boBannerMapper.selectBannerDetail(boBannerDTO);
	}

	public BoBannerDTO selectBannerModify(BoBannerDTO boBannerDTO) {
		return boBannerMapper.selectBannerModify(boBannerDTO);
	}

	public void insertBanner(BoBannerDTO boBannerDTO) {
		boBannerMapper.insertBanner(boBannerDTO);
	}

	public void updateBanner(BoBannerDTO boBannerDTO) throws Exception {
		if(boBannerDTO.getFileSeq() != null && !boBannerDTO.getFileSeq().equals("")) {
			boBannerMapper.updateBanner(boBannerDTO);
		} else {
			Integer profileFileId = commonFileService.fileUpload(boBannerDTO.getBannerFiles(), "BANNER");
			if(profileFileId != null){
				boBannerDTO.setFileSeq(String.valueOf(profileFileId));
			}
			boBannerMapper.updateBanner(boBannerDTO);
		}
	}
	public void updateFile(FileDTO fileDTO) {
		commonFileService.updateFile(fileDTO);
	}

	public void deleteBanner(BoBannerDTO boBannerDTO) {
		boBannerMapper.deleteBanner(boBannerDTO);
	}

	public List<FileDTO> selectBannerFileList(BoBannerDTO boBannerDTO) {
		BoBannerDTO bo = boBannerMapper.selectBannerDetail(boBannerDTO);
		List<FileDTO> list = commonFileService.selectFileDetailList(bo.getFileSeq());
		return list;
	}
}
