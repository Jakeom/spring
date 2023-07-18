package com.fw.bo.system.screen.service;

import com.fw.core.dto.bo.BoPopupDTO;
import com.fw.core.mapper.db1.bo.BoPopupMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScreenPopupService {

    private final BoPopupMapper boPopupMapper;

    /**
     * 팝업관리 리스트
     */
    public List<BoPopupDTO> selectPopupList(BoPopupDTO boPopupDTO) {
        return boPopupMapper.selectPopupList(boPopupDTO);
    }

    /**
     * 팝업관리 상세
     */
    public BoPopupDTO selectPopupDetail(BoPopupDTO boPopupDTO) {
        return boPopupMapper.selectPopupDetail(boPopupDTO);
    }

    /**
     * 팝업관리 등록
     */
    public void insertPopup(BoPopupDTO boPopupDTO) {
        boPopupMapper.insertPopup(boPopupDTO);
    }

    /**
     * 팝업관리 수정
     */
    public void updatePopup(BoPopupDTO boPopupDTO) {
        boPopupMapper.updatePopup(boPopupDTO);
    }

    /**
     * 팝업관리 삭제
     */
    public void deletePopup(BoPopupDTO boPopupDTO) {
        boPopupMapper.deletePopup(boPopupDTO);
    }
}
