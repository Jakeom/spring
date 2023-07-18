package com.fw.core.mapper.db1.fo;


import com.fw.core.dto.fo.FoBlacklistHhDTO;
import com.fw.core.dto.fo.resume.FoHhReadingHistoryDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FoHhReadingHistoryMapper {

    void insertHhReadingHistory(FoHhReadingHistoryDTO foHhReadingHistoryDTO); // 헤드헌터 열람 내역 추가

}