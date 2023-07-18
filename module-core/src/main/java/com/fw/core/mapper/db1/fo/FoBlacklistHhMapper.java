package com.fw.core.mapper.db1.fo;


import com.fw.core.dto.fo.FoApplicantDTO;
import com.fw.core.dto.fo.FoBlacklistHhDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FoBlacklistHhMapper {

    void insertBlacklistHh(FoBlacklistHhDTO foBlacklistHhDTO); // 블랙리스트 헤드헌터 추가

}