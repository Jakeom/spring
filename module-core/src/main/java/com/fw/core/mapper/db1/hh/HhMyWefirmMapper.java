package com.fw.core.mapper.db1.hh;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.fw.core.dto.hh.HhWefirmCustomerDTO;
import com.fw.core.dto.hh.HhWefirmDTO;
import com.fw.core.dto.hh.HhWefirmHeadhunterDTO;
import com.fw.core.dto.hh.HhWefirmPositionDTO;

@Mapper
public interface HhMyWefirmMapper {

	// Wefrim 포지션 공고 리스트
	public List<HhWefirmPositionDTO> selectViewPositionList(HhWefirmPositionDTO hhWefirmPositionDTO);

	// Wefrim 헤드헌터 리스트
	public List<HhWefirmHeadhunterDTO> selectHeadhunterList(HhWefirmHeadhunterDTO hhWefirmHeadhunterDTO);

	// Wefrim 헤드헌터 상세
	public HhWefirmHeadhunterDTO selectHhInfo(HhWefirmHeadhunterDTO hhWefirmHeadhunterDTO);

	// Wefrim 헤드헌터 필드
	public List<HhWefirmHeadhunterDTO> selectHhFieldInfo(HhWefirmHeadhunterDTO hhWefirmHeadhunterDTO);

	int selectHeadhunterListCnt(HhWefirmHeadhunterDTO hhWefirmHeadhunterDTO);

	int selectViewPositionListCnt(HhWefirmPositionDTO hhWefirmHeadhunterDTO);

	List<HhWefirmCustomerDTO> selectWefirmCustomerList(HhWefirmCustomerDTO hhWefirmCustomerDTO);

	int selectWefirmCustomerListCnt(HhWefirmCustomerDTO hhWefirmCustomerDTO);

	List<HhWefirmHeadhunterDTO> selectWefirmHeadhunterList(HhWefirmHeadhunterDTO hhWefirmHeadhunterDTO);

	void deleteClientPm(HhWefirmCustomerDTO hhWefirmCustomerDTO);

	void deleteClientPass(HhWefirmCustomerDTO hhWefirmCustomerDTO);

	void insertClientPm(HhWefirmCustomerDTO hhWefirmCustomerDTO);

	void insertClientPass(HhWefirmCustomerDTO hhWefirmCustomerDTO);

	void updateClientPm(HhWefirmCustomerDTO hhWefirmCustomerDTO);

	void updateClientPass(HhWefirmCustomerDTO hhWefirmCustomerDTO);

	String selectWefirmId(HhWefirmDTO hhWefirmDTO);

	HhWefirmCustomerDTO selectClientPmInfo(HhWefirmCustomerDTO hhWefirmCustomerDTO);

	HhWefirmCustomerDTO selectClientPassInfo(HhWefirmCustomerDTO hhWefirmCustomerDTO);

	List<HhWefirmCustomerDTO> selectClientPassList(HhWefirmCustomerDTO hhWefirmCustomerDTO);

	public HhWefirmHeadhunterDTO selectWefirmAuth(HhWefirmHeadhunterDTO hhWefirmHeadhunterDTO);

	public List<HhWefirmHeadhunterDTO> selectPmPositionList(HhWefirmHeadhunterDTO hhWefirmHeadhunterDTO);
}
