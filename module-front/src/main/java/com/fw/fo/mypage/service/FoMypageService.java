package com.fw.fo.mypage.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fw.core.dto.fo.FoLoginLogDTO;
import com.fw.core.mapper.db1.fo.FoLoginLogMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FoMypageService {

	private final FoLoginLogMapper foLoginLogMapper;
	/** Front 메인 로그인 기록 cnt */
	public int selectLoginLogListCntPaging(FoLoginLogDTO foLoginLogDTO) {
		return foLoginLogMapper.selectLoginLogListCntPaging(foLoginLogDTO);
	}

	/** Front 메인 로그인 기록 */
	public List<FoLoginLogDTO> selectLoginLogListPaging(FoLoginLogDTO foLoginLogDTO) {
		return foLoginLogMapper.selectLoginLogListPaging(foLoginLogDTO);
	}

}