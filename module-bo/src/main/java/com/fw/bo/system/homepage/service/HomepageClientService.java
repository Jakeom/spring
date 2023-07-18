package com.fw.bo.system.homepage.service;

import com.fw.core.dto.bo.BoClientPmDTO;
import com.fw.core.mapper.db1.bo.BoClientMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HomepageClientService {
    private final BoClientMapper boClientMapper;

    public List<BoClientPmDTO> selectClientPmList(BoClientPmDTO boClientPmDTO) {
        return boClientMapper.selectClientPmList(boClientPmDTO);
    }

    public List<BoClientPmDTO> selectWefirmList(BoClientPmDTO boClientPmDTO) {
        return boClientMapper.selectWefirmList(boClientPmDTO);
    }

}
