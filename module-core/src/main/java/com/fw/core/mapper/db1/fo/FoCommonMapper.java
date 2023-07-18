package com.fw.core.mapper.db1.fo;

import com.fw.core.dto.fo.FoCommonDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FoCommonMapper {

    FoCommonDTO selectInfo(FoCommonDTO foCommonDTO); // 정보 취득
    List<FoCommonDTO> selectBannerList(FoCommonDTO foCommonDTO); // 배너 리스트 취득
    List<FoCommonDTO> selectMainDisplayList(FoCommonDTO foCommonDTO); // 메인 디스플레이 리스트

}
