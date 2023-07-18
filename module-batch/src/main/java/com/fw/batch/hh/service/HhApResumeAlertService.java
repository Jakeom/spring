package com.fw.batch.hh.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fw.core.dto.hh.HhPointDTO;
import com.fw.core.dto.hh.HhPointUseHistoryDTO;
import com.fw.core.mapper.db1.hh.HhApSearchsMapper;
import com.fw.core.mapper.db1.hh.HhMyApMapper;
import com.fw.core.dto.hh.HhMyapListDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
@RequiredArgsConstructor
public class HhApResumeAlertService {
    @Autowired
    private HhApSearchsMapper hhApSearchsMapper;

    public List<HhMyapListDTO> selectHhApList(HhMyapListDTO hhMyapListDTO){
        return hhApSearchsMapper.selectHhApList(hhMyapListDTO);
    }
    
    public List<HhMyapListDTO> selectHhApAlertList(){
        return hhApSearchsMapper.selectHhApAlertList();
    }
}
