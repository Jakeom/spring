package com.fw.core.mapper.db1.bo;

import com.fw.core.dto.bo.BoAdminDTO;
import com.fw.core.dto.bo.BoAdminMenuProgramDTO;
import com.fw.core.dto.bo.BoAuthAdminDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoAuthAdminMapper {

    /**
     * 메뉴프로그램 리스트
     */
    List<BoAdminMenuProgramDTO> selectMenuProgram(BoAdminMenuProgramDTO boAdminMenuProgramDTO);

    /**
     * 관리자 리스트
     */
    List<BoAdminDTO> selectAdminList(BoAdminDTO boAdminDTO);

    /**
     * 관리자별 권한 정보
     */
    List<BoAuthAdminDTO> selectAdminAuth(BoAuthAdminDTO boAuthAdminDTO);

    /**
     * 관리자별 권한 부여
     */
    void insertAdminAuth(BoAuthAdminDTO boAuthAdminDTO);

    /**
     * 관리자별 권한 초기화
     */
    void deleteAdminAuth(BoAuthAdminDTO boAuthAdminDTO);
}
