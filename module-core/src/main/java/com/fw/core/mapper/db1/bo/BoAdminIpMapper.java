package com.fw.core.mapper.db1.bo;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.fw.core.dto.bo.BoAdminIpDTO;

/**
 * BO Admin IP 인터페이스
 * @author ghazal
 */
@Mapper
public interface BoAdminIpMapper {

	/** 관리자 ip 리스트 */
	List<BoAdminIpDTO> selectAdminIpList(BoAdminIpDTO BoAdminIpDTO);
	/** ip 등록 */
	void insertAdminIp(BoAdminIpDTO boAdminIpDTO);
	/** ip 수정 */
	void updateAdminIp(BoAdminIpDTO BoAdminIpDTO);
	/** ip 삭제 */
	void deleteAdminIp(BoAdminIpDTO BoAdminIpDTO);

}
