package com.fw.core.mapper.db1.hh;

import com.fw.core.dto.hh.HhNoticeSettingDTO;
import com.fw.core.dto.hh.HhPointDTO;
import com.fw.core.dto.hh.HhPointUseHistoryDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface HhMypageMapper {
    void updateHhApFlag(HhNoticeSettingDTO hhNoticeSettingDTO);
    boolean selectApAlertExists(HhNoticeSettingDTO hhNoticeSettingDTO);
    void insertApAlert(HhNoticeSettingDTO hhNoticeSettingDTO);
    void updateApAlert(HhNoticeSettingDTO hhNoticeSettingDTO);
    void deleteHp2(HhNoticeSettingDTO hhNoticeSettingDTO);
    void insertHp2(HhNoticeSettingDTO hhNoticeSettingDTO);
    void deleteLoc(HhNoticeSettingDTO hhNoticeSettingDTO);
    void insertLoc(HhNoticeSettingDTO hhNoticeSettingDTO);
    HhNoticeSettingDTO selectApAlertInfo(HhNoticeSettingDTO hhNoticeSettingDTO);
    List<HhNoticeSettingDTO> selectHp2List(HhNoticeSettingDTO hhNoticeSettingDTO);
    List<HhNoticeSettingDTO> selectLocList(HhNoticeSettingDTO hhNoticeSettingDTO);
    HhPointDTO selectMyPoint(HhPointDTO hhPointDTO);
    List<HhPointUseHistoryDTO> selectPointUseHistory(HhPointUseHistoryDTO hhPointUseHistoryDTO);
    boolean selectInWeekPurchasePoint(HhPointUseHistoryDTO hhPointUseHistoryDTO);
    int selectPointUseHistoryCnt(HhPointUseHistoryDTO hhPointUseHistoryDTO);


}
