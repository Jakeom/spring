package com.fw.hh.faq.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fw.core.dto.hh.HhFaqDTO;
import com.fw.core.mapper.db1.hh.HhFaqMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class HhFaqService {

	private final HhFaqMapper hhFaqMapper;

	// 카테고리 리스트 취득
	public List<HhFaqDTO> selectFaqCateList(HhFaqDTO hhFaqDTO) {
		return hhFaqMapper.selectFaqCateList(hhFaqDTO);
	}

	// faq 리스트 취득
	public List<HhFaqDTO> selectFaqList(HhFaqDTO hhFaqDTO) {
		return hhFaqMapper.selectFaqList(hhFaqDTO);
	}

	// faq 리스트 취득
	public List<HhFaqDTO> selectMainFaqList(HhFaqDTO hhFaqDTO) {
		return hhFaqMapper.selectMainFaqList(hhFaqDTO);
	}

}