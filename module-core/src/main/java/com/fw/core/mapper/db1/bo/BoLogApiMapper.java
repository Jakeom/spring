package com.fw.core.mapper.db1.bo;

import com.fw.core.dto.bo.BoApiLogDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoLogApiMapper {

    /**
     * API 로그
     */
    List<BoApiLogDTO> selectApiLog(BoApiLogDTO boApiLogDTO);

}
