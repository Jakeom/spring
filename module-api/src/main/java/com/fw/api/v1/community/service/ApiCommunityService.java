package com.fw.api.v1.community.service;

import com.fw.core.dto.api.ApiCommonCdDTO;
import com.fw.core.dto.api.ApiCommunityDTO;
import com.fw.core.mapper.db1.api.ApiCommunityMapper;
import com.fw.core.mapper.db1.api.ApiMemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.List;

/**
 * @author WSH
 * @since 2022.11
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ApiCommunityService {

	private final ApiCommunityMapper apiCommunityMapper;
	private final ApiMemberMapper apiMemberMapper;

	/** 커뮤니티 카테고리 목록 조회 */
	public List<ApiCommonCdDTO> selectCommunityCategoryList(ApiCommonCdDTO apiCommonCdDTO) {
		// AP/HH 구분
		String userType = apiMemberMapper.selectUsertype(apiCommonCdDTO.getMemberId());
		apiCommonCdDTO.setUserType(userType);
		return apiCommunityMapper.selectCommunityCategoryList(apiCommonCdDTO);
	}

	/** 게시글 리스트 조회 */
	public List<ApiCommunityDTO> selectCommunityContentList(ApiCommunityDTO apiCommunityDTO) {
		List<ApiCommunityDTO> list = apiCommunityMapper.selectCommunityContentList(apiCommunityDTO);
		if(StringUtils.equals(apiCommunityDTO.getDtype(),"AP")) { // 후보자 게시글 리스트
			for(ApiCommunityDTO m : list) {
				if(StringUtils.equals(apiCommunityDTO.getMemberId(),m.getCreateSeq())) { // 내 글 여부
					m.setMyBoardFlag("Y");
				} else {
					m.setMyBoardFlag("N");
				}

				// 등록자 마스킹
				String name = m.getName();
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

				if(StringUtils.equals(m.getSecretFlag(),"Y")) { // 비밀글
					if(!StringUtils.equals(m.getCreateSeq(),apiCommunityDTO.getMemberId())) { // 내가 등록한 글이 아닐경우 글 전체 마스킹
						m.setContent("*****");
						m.setTitle("*****");
						m.setName("***");
						m.setCompanyName("*****");
					}
				}
			}
		}
		return list;
	}

	/** 게시글 리스트 카운트 */
	public int selectCommunityContentCnt(ApiCommunityDTO apiCommunityDTO) {
		return apiCommunityMapper.selectCommunityContentCnt(apiCommunityDTO);
	}
}
