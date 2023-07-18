package com.fw.core.mapper.db1.bo;

import com.fw.core.dto.bo.BoAdminDTO;
import com.fw.core.dto.bo.BoAdminLogDTO;
import com.fw.core.dto.bo.BoAdminLoginLogDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoLogAdminMapper {
    /**
     * 관리자 로그
     */
    List<BoAdminLogDTO> selectAdminLog(BoAdminLogDTO boAdminLogDTO);

    /**
     * 관리자 아이디 조회
     */
    List<BoAdminDTO> selectAdminId(BoAdminDTO boAdminDTO);

    /**
     * 관리자 로그인 로그 조회
     */
    List<BoAdminLoginLogDTO> selectAdminLoginLog(BoAdminLoginLogDTO boAdminLoginLogDTO);

}
