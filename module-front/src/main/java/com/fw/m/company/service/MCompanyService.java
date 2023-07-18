package com.fw.m.company.service;

import java.util.List;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fw.core.common.service.CommonFileService;
import com.fw.core.dto.m.MCompanyDTO;
import com.fw.core.mapper.db1.m.MCompanyMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class MCompanyService {

	private final MCompanyMapper mCompanyMapper;
	private final CommonFileService commonFileService;
	private final BCryptPasswordEncoder pwEncoder;

	public List<MCompanyDTO> selectSuggetList(MCompanyDTO mCompanyDTO) {
		List<MCompanyDTO> list = mCompanyMapper.selectSuggetList(mCompanyDTO);
		for(MCompanyDTO m : list){
			String fileSeq = m.getSubmitResumeFileId();
			m.setCommonFileList(commonFileService.selectFileDetailList(fileSeq));
		}
		return list;
	}

	public MCompanyDTO selectPositionDetail(MCompanyDTO mCompanyDTO) {
		return mCompanyMapper.selectPositionDetail(mCompanyDTO);
	}

	public void updateSuggestStatus(MCompanyDTO mCompanyDTO) {
		for(MCompanyDTO m : mCompanyDTO.getList()) {
			mCompanyMapper.updateSuggestStatus(m);
		}
	}

	public boolean selectCheckPassword(MCompanyDTO mCompanyDTO) {
		MCompanyDTO m = mCompanyMapper.selectCheckPassword(mCompanyDTO);
		return pwEncoder.matches(mCompanyDTO.getPassword(), m.getPassword());
	}

}
