package com.fw.batch.hh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fw.core.dto.hh.HhPointDTO;
import com.fw.core.dto.hh.HhPointUseHistoryDTO;
import com.fw.core.mapper.db1.hh.HhMyApMapper;
import com.fw.core.dto.hh.HhMyapListDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
@RequiredArgsConstructor
public class MyApService {
    
    @Autowired
    private HhMyApMapper hhMyApMapper;


    public HhPointDTO selectPoint(HhPointDTO hhPointDTO) {
		return hhMyApMapper.selectPoint(hhPointDTO);
	}

    public int insertPoint(HhPointDTO hhPointDTO) {
		return hhMyApMapper.insertPoint(hhPointDTO);
	}

    public int updatePoint(HhPointDTO hhPointDTO) {
		return hhMyApMapper.updatePoint(hhPointDTO);
	}

    public int insertPointUseHistory(HhPointUseHistoryDTO hhPointUseHistoryDTO) {
		return hhMyApMapper.insertPointUseHistory(hhPointUseHistoryDTO);
	}

    public List<HhMyapListDTO> selectHhMyapExpiredList(){
        return hhMyApMapper.selectHhMyapExpiredList();
    }

    public int updateHhMyapListResumeOpend(HhMyapListDTO hhMyapListDTO){
        return hhMyApMapper.updateHhMyapListResumeOpend(hhMyapListDTO);
    }

    public void addExpiredPoint(HhMyapListDTO hhMyapListDTO){
        HhPointDTO hhPointDTO = new HhPointDTO();
        hhPointDTO.setFrontSession(hhMyapListDTO.getFrontSession());
        
        HhPointDTO tt = selectPoint(hhPointDTO);
        hhPointDTO.setBalance("20000");
        hhPointDTO.setFreePoint("20000");
        hhPointDTO.setPaidPoint("0");
        hhPointDTO.setMemberId(hhMyapListDTO.getFrontSession().getId());
        // 등록된 리워드가 없을경우 신규등록
        if(tt == null){
            
            insertPoint(hhPointDTO);
        }else {
            // 이미 존재하는 포인트인 경우
            hhPointDTO.setId(tt.getId());
            updatePoint(hhPointDTO);
        }
        
        // 포인트 히스토리 등록
        HhPointUseHistoryDTO hhPointUseHistoryDTO = new HhPointUseHistoryDTO();
        hhPointUseHistoryDTO.setReasonCd("USE_RESUME");
        hhPointUseHistoryDTO.setMemberId(hhMyapListDTO.getFrontSession().getId());
        hhPointUseHistoryDTO.setPointId(hhPointDTO.getId());
        hhPointUseHistoryDTO.setFreeIncrease("20000");
        hhPointUseHistoryDTO.setPaidIncrease("0");
        insertPointUseHistory(hhPointUseHistoryDTO);
    }
}
