package com.fw.fo.mypage.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fw.core.common.service.CommonFileService;
import com.fw.core.dto.fo.FoPositionDTO;
import com.fw.core.mapper.db1.fo.FoPositionMapper;
import com.fw.core.mapper.db1.fo.searchHh.FoSearchHhMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.fw.core.dto.fo.FoInterestHhDTO;
import com.fw.core.mapper.db1.fo.FoInterestHhMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FoAttentionHeadHunterService {

	private final FoInterestHhMapper foInterestHhMapper;
	private final FoPositionMapper foPositionMapper;
	private final CommonFileService commonFileService;

	/** Front 관심 헤드헌터 리스트 */
	public List<FoInterestHhDTO> selectInterestHhList(FoInterestHhDTO foInterestHhDTO) {
		List<FoInterestHhDTO> list = foInterestHhMapper.selectInterestHhList(foInterestHhDTO);
		List<FoInterestHhDTO> returnList = new ArrayList<>();
		for(FoInterestHhDTO f:list){
			String fileSeq = f.getProfilePictureFileId();
			if(!Objects.isNull(fileSeq)){
				if(StringUtils.isNotBlank(fileSeq)){
					f.setCommonFileList(commonFileService.selectFileDetailList(fileSeq));
				}
			}
			FoPositionDTO fo = new FoPositionDTO();
			fo.setRowSize(3);
			fo.setMemberId(f.getHeadhunterId());
			fo.setFrontSession(foInterestHhDTO.getFrontSession());
			f.setPositionDTOList(foPositionMapper.selectPositionListPaging(fo));
			returnList.add(f);
		}
		return returnList;
	}

	/** Front 관심 헤드헌터 상세 */
	public FoInterestHhDTO attentionHeadhunterDetail(FoInterestHhDTO foInterestHhDTO) {
		return foInterestHhMapper.attentionHeadhunterDetail(foInterestHhDTO);
	}

	/** Front 관심 헤드헌터 관심해제 */
	public void attentionHeadhunterDelete(FoInterestHhDTO foInterestHhDTO) {
		foInterestHhMapper.attentionHeadhunterDelete(foInterestHhDTO);
	}

}