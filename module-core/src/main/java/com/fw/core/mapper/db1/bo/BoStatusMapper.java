package com.fw.core.mapper.db1.bo;

import com.fw.core.dto.bo.BoStatusDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoStatusMapper {

    BoStatusDTO selectMemberCnt(BoStatusDTO boStatusDTO);

    BoStatusDTO selectTodayMemberCnt(BoStatusDTO boStatusDTO);

    BoStatusDTO selectMonthMemberCnt(BoStatusDTO boStatusDTO);

    BoStatusDTO selectResumeCnt(BoStatusDTO boStatusDTO);

    BoStatusDTO selectTodayResumeCnt(BoStatusDTO boStatusDTO);

    BoStatusDTO selectMonthResumeCnt(BoStatusDTO boStatusDTO);

    BoStatusDTO selectDayStatus(BoStatusDTO boStatusDTO);

    BoStatusDTO selectMonthStatus(BoStatusDTO boStatusDTO);

}
