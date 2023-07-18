package com.fw.core.mapper.db1.m;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.fw.core.dto.m.MCompanyDTO;

@Mapper
public interface MCompanyMapper {

	List<MCompanyDTO> selectSuggetList(MCompanyDTO mCompanyDTO);

	MCompanyDTO selectPositionDetail(MCompanyDTO mCompanyDTO);
	void updateSuggestStatus(MCompanyDTO mCompanyDTO);
	MCompanyDTO selectCheckPassword(MCompanyDTO mCompanyDTO);

}
