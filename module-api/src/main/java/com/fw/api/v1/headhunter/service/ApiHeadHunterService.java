package com.fw.api.v1.headhunter.service;

import com.fw.core.common.service.CommonFileService;
import com.fw.core.dto.api.ApiMemberDTO;
import com.fw.core.mapper.db1.api.ApiHeadHunterMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wsh
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ApiHeadHunterService {

	private final ApiHeadHunterMapper headHunterMapper;
	private final CommonFileService commonFileService;

	/** 내 인재 리스트 조회 */
	public List<ApiMemberDTO> selectApList(ApiMemberDTO apiMemberDTO) {

		List<ApiMemberDTO> list = headHunterMapper.selectApList(apiMemberDTO);
		for(ApiMemberDTO m : list){
			String expireFlag = m.getExpireFlag();
			String name = m.getName();
			if(StringUtils.equals(expireFlag,"EXPIRED")) { // CASE1 열람만료 - 이름 마스킹처리
				String firstNm = name.substring(0,1);
				int LastNmStart = name.indexOf(firstNm);
				String LastNm = name.substring(LastNmStart+1,name.length());
				String makers = "";
				for (int i=0; i<LastNm.length(); i++) {
					makers += "*";
				}
				LastNm = LastNm.replace(LastNm,makers);
				String maksingName = firstNm + LastNm;
				m.setName(maksingName);
			}

			if(StringUtils.equals(expireFlag,"AP_NOOPEN")) { // CASE2 후보자 비공개 - 이름 비공개 처리
				m.setName("비공개");
			}

			if(StringUtils.isNotBlank(m.getPictureFileId())) { // 이미지파일
				String fileSeq = m.getPictureFileId();
				m.setCommonFileList(commonFileService.selectFileDetailList(fileSeq));
			}
		}
		return list;
	}

	/** 내 인재 리스트 카운트 */
	public int selectApListCnt(ApiMemberDTO apiMemberDTO) {
		return headHunterMapper.selectApListCnt(apiMemberDTO);
	}
}
