package com.fw.core.mapper.db1.fo;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.fw.core.dto.fo.FoLoginLogDTO;

@Mapper
public interface FoLoginLogMapper {

	/** Front 메인 로그인 기록  cnt*/
	int selectLoginLogListCntPaging(FoLoginLogDTO foLoginLogDTO);

	/** Front 메인 로그인 기록 */
	List<FoLoginLogDTO> selectLoginLogListPaging(FoLoginLogDTO foLoginLogDTO);

}
