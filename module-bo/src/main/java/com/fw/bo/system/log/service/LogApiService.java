package com.fw.bo.system.log.service;

import com.fw.core.dto.bo.BoApiLogDTO;
import com.fw.core.mapper.db1.bo.BoLogApiMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LogApiService {

    private final BoLogApiMapper boLogApiMapper;

    /**
     * API 로그 리스트
     */
    public List<BoApiLogDTO> selectApiLog(BoApiLogDTO boApiLogDTO) {
        return boLogApiMapper.selectApiLog(boApiLogDTO);
    }

}
