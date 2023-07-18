package com.fw.hh.company.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fw.core.dto.hh.HhCompanyDTO;
import com.fw.core.mapper.db1.hh.HhCompanyMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class HhcompanyService {

	private final HhCompanyMapper hhCompanyMapper;

	/** 고객사 조회 */
	public List<HhCompanyDTO> selectHrCompanyList(HhCompanyDTO hhCompanyDTO) {
		return hhCompanyMapper.selectHrCompanyList(hhCompanyDTO);
	}

	/** 등록 */
	public void insertHrCompany(HhCompanyDTO hhCompanyDTO) {
		// 고객사 등록
		hhCompanyMapper.insertHrCompany(hhCompanyDTO);
		// 고객사 인사담당자 등록
		hhCompanyMapper.insertHrManager(hhCompanyDTO);
	}

	/** 고객사 삭제 */
	public void deleteHrCompany(HhCompanyDTO hhCompanyDTO) {
		for (String id : hhCompanyDTO.getIdArray()) {
			HhCompanyDTO h = new HhCompanyDTO();
			h.setId(id);
			// 고객사 삭제
			hhCompanyMapper.deleteHrCompany(h);
			// 고객사 인사담당자 삭제
			hhCompanyMapper.deleteHrManager(h);
		}
	}

	/** 고객사 상세 */
	public HhCompanyDTO selectHrCompanyDetail(HhCompanyDTO hhCompanyDTO) {
		return hhCompanyMapper.selectHrCompanyDetail(hhCompanyDTO);
	}

	/** 고객사 상세(수정가능) */
	public HhCompanyDTO selectHrDetailCompany(HhCompanyDTO hhCompanyDTO) {
		return hhCompanyMapper.selectHrDetailCompany(hhCompanyDTO);
	}

	/** 고객사 인사담당자 정보 상세 */
	public HhCompanyDTO selectHrManagerDetail(HhCompanyDTO hhCompanyDTO) {
		return hhCompanyMapper.selectHrManagerDetail(hhCompanyDTO);
	}

	/** 고객사 수정 */
	public void updateHrCompany(HhCompanyDTO hhCompanyDTO) {
		hhCompanyMapper.updateHrCompany(hhCompanyDTO);
		hhCompanyMapper.deleteManagerAll(hhCompanyDTO);
		for (HhCompanyDTO h : hhCompanyDTO.getManagerList()) {
			hhCompanyMapper.insertHrDetailManager(h);
		}
	}

	/** 고객사 메모 조회 */
	public List<HhCompanyDTO> selectHrMemoList(HhCompanyDTO hhCompanyDTO) {
		return hhCompanyMapper.selectHrMemoList(hhCompanyDTO);
	}

	/** 고객사 메모 등록 */
	public void insertHrMemo(HhCompanyDTO hhCompanyDTO) {
		hhCompanyMapper.insertHrMemo(hhCompanyDTO);
	}

	/** 포지션 조회 */
	public List<HhCompanyDTO> selectHrPositionList(HhCompanyDTO hhCompanyDTO) {
		return hhCompanyMapper.selectHrPositionList(hhCompanyDTO);
	}

	/** 인사담당자 조회 */
	public List<HhCompanyDTO> selectHrManagerList(HhCompanyDTO hhCompanyDTO) {
		return hhCompanyMapper.selectHrManagerList(hhCompanyDTO);
	}

	/** 메모 수정 */
	public void deleteHrMemo(HhCompanyDTO hhCompanyDTO) {
		// 메모 삭제
		hhCompanyMapper.deleteHrMemo(hhCompanyDTO);
	}
}