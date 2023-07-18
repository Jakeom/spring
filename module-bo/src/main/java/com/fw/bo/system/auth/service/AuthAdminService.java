package com.fw.bo.system.auth.service;

import com.fw.core.dto.bo.BoAdminDTO;
import com.fw.core.dto.bo.BoAdminMenuProgramDTO;
import com.fw.core.dto.bo.BoAuthAdminDTO;
import com.fw.core.mapper.db1.bo.BoAuthAdminMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthAdminService {

    private final BoAuthAdminMapper boAuthAdminMapper;

    /**
     * 메뉴프로그램 리스트
     */
    public List<BoAdminMenuProgramDTO> selectMenuProgram(BoAdminMenuProgramDTO boAdminMenuProgramDTO) {
        return boAuthAdminMapper.selectMenuProgram(boAdminMenuProgramDTO);
    }

    /**
     * 관리자 리스트
     */
    public List<BoAdminDTO> selectAdminList(BoAdminDTO boAdminDTO) {
        return boAuthAdminMapper.selectAdminList(boAdminDTO);
    }

    /**
     * 관리자별 권한 정보
     */
    public List<BoAuthAdminDTO> selectAdminAuth(BoAuthAdminDTO boAuthAdminDTO) {
        return boAuthAdminMapper.selectAdminAuth(boAuthAdminDTO);
    }

    /**
     * 관리자별 권한 부여
     */
    public void insertAdminAuth(BoAuthAdminDTO boAuthAdminDTO) {
        /* 권한 적용 전 모든 권한 초기화 */
        boAuthAdminMapper.deleteAdminAuth(boAuthAdminDTO);
        /* 권한적용 */
        boAuthAdminMapper.insertAdminAuth(boAuthAdminDTO);
    }
}
