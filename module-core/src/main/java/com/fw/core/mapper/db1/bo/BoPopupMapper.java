package com.fw.core.mapper.db1.bo;

import com.fw.core.dto.bo.BoPopupDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoPopupMapper {

    /**
     * 팝업관리 리스트
     */
    List<BoPopupDTO> selectPopupList(BoPopupDTO boPopupDTO);

    /**
     * 팝업관리 상세
     */
    BoPopupDTO selectPopupDetail(BoPopupDTO boPopupDTO);

    /**
     * 팝업관리 등록
     */
    void insertPopup(BoPopupDTO boPopupDTO);

    /**
     * 팝업관리 수정
     */
    void updatePopup(BoPopupDTO boPopupDTO);

    /**
     * 팝업관리 삭제
     */
    void deletePopup(BoPopupDTO boPopupDTO);
}
