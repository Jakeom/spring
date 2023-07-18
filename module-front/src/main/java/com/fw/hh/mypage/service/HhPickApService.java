package com.fw.hh.mypage.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.fw.core.dto.hh.HhPickApDTO;
import com.fw.core.mapper.db1.hh.HhPickApMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class HhPickApService {
	private final HhPickApMapper hhPickApMapper;

	/** 나를 pick한 후보자 */
	public List<HhPickApDTO> selectPickApList(HhPickApDTO hhPickApDTO) {

		List<HhPickApDTO> list = new ArrayList<>();

		list = hhPickApMapper.selectPickApList(hhPickApDTO);

		for (HhPickApDTO m : list) {
			String myApFlag = m.getMyApFlag();
			String name = m.getName();
			if (StringUtils.equals(myApFlag, "N")) {

				// 이름 마스킹처리
				String firstNm = name.substring(0, 1);
				int LastNmStart = name.indexOf(firstNm);
				String LastNm = name.substring(LastNmStart + 1, name.length());
				String makers = "";
				for (int i = 0; i < LastNm.length(); i++) {
					makers += "*";
				}
				LastNm = LastNm.replace(LastNm, makers);

				String maksingName = firstNm + LastNm; // 마스킹된 이름

				m.setName(maksingName);
				m.setResumeUrl("/m/resume/resume-detail-hh?resumeId=" + m.getId() + "&memberId=" + hhPickApDTO.getFrontSession().getId());
				log.info(m.getResumeUrl());
			} else {
				m.setResumeUrl("/m/resume/resume-detail-ap?resumeId=" + m.getId());
			}
		}
		return list;
	}
}