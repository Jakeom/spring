package com.fw.core.mapper.db1.m;

import com.fw.core.dto.m.MServiceDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MServiceMapper {

    MServiceDTO selectNoticeInfoM(MServiceDTO mServiceDTO);

    MServiceDTO selectFaqInfoM(MServiceDTO mServiceDTO);
}
