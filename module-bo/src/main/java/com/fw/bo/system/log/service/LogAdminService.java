package com.fw.bo.system.log.service;

import com.fw.core.dto.bo.BoAdminDTO;
import com.fw.core.dto.bo.BoAdminLogDTO;
import com.fw.core.dto.bo.BoAdminLoginLogDTO;
import com.fw.core.mapper.db1.bo.BoLogAdminMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LogAdminService {

    private final BoLogAdminMapper boLogAdminMapper;

    /**
     * 관리자 로그
     */
    public List<BoAdminLogDTO> selectAdminLog(BoAdminLogDTO boAdminLogDTO) {
        return boLogAdminMapper.selectAdminLog(boAdminLogDTO);
    }

    /**
     * 관리자 아이디 조회
     */
    public List<BoAdminDTO> selectAdminId(BoAdminDTO boAdminDTO) {
        return boLogAdminMapper.selectAdminId(boAdminDTO);
    }

    /**
     * 관리자 로그인 로그 조회
     */
    public List<BoAdminLoginLogDTO> selectAdminLoginLog(BoAdminLoginLogDTO boAdminLoginLogDTO) {
        return boLogAdminMapper.selectAdminLoginLog(boAdminLoginLogDTO);
    }

}
