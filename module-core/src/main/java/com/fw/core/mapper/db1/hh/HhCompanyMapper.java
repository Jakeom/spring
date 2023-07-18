package com.fw.core.mapper.db1.hh;

import com.fw.core.dto.hh.HhCompanyDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HhCompanyMapper {

	/** 내 고객사 조회 */
	List<HhCompanyDTO> selectHrCompanyList(HhCompanyDTO hhCompanyDTO);

	/** 고객사 등록 */
	void insertHrCompany(HhCompanyDTO hhCompanyDTO);

	/** 인사 담당자 등록 */
	void insertHrManager(HhCompanyDTO hhCompanyDTO);

	/** 고객사 메모 조회 */
	List<HhCompanyDTO> selectHrMemoList(HhCompanyDTO hhCompanyDTO);

	/** 고객사 메모 등록 */
	void insertHrMemo(HhCompanyDTO hhCompanyDTO);

	/** 고객사 삭제 */
	void deleteHrCompany(HhCompanyDTO hhCompanyDTO);

	/** 인사 담당자 삭제 */
	void deleteHrManager(HhCompanyDTO hhCompanyDTO);

	/** 고객사 상세 */
	HhCompanyDTO selectHrCompanyDetail(HhCompanyDTO hhCompanyDTO);

	/** 고객사 상세(수정가능) */
	HhCompanyDTO selectHrDetailCompany(HhCompanyDTO hhCompanyDTO);

	/** 고객사 수정 */
	void updateHrCompany(HhCompanyDTO hhCompanyDTO);

	/** 상세에서 고객사 인사담당자 등록 */
	void insertHrDetailManager(HhCompanyDTO hhCompanyDTO);

	/** 고객사 인사담당자 상세 */
	HhCompanyDTO selectHrManagerDetail(HhCompanyDTO hhCompanyDTO);

	/** 상세에서 고객사 인사담당자 수정 */
	void updateHrManager(HhCompanyDTO hhCompanyDTO);

	/** 포지션 리스트 */
	List<HhCompanyDTO> selectHrPositionList(HhCompanyDTO hhCompanyDTO);

	/** 인사 담당자 조회 */
	List<HhCompanyDTO> selectHrManagerList(HhCompanyDTO hhCompanyDTO);

	/** 인사담당자 모두 삭제 */
	void deleteManagerAll(HhCompanyDTO hhCompanyDTO);

	void deleteHrMemo(HhCompanyDTO hhCompanyDTO);

	List<HhCompanyDTO> selectCompanyDetailByLicense(HhCompanyDTO hhCompanyDTO);
}
