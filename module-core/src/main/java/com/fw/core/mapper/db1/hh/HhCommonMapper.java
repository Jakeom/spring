package com.fw.core.mapper.db1.hh;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.fw.core.dto.hh.HhCommonDTO;
import com.fw.core.dto.hh.HhHeadhunterDTO;

@Mapper
public interface HhCommonMapper {

	HhCommonDTO selectInfo(HhCommonDTO hhCommonDTO); // 정보 취득

	List<HhCommonDTO> selectBannerList(HhCommonDTO hhCommonDTO); // 배너 리스트 취득

	List<HhCommonDTO> selectMainDisplayList(HhCommonDTO hhCommonDTO); // 메인 디스플레이 리스트

	HhHeadhunterDTO selectHeadhunterInfo(HhHeadhunterDTO HhHeadhunterDTO); // 위펌 , 서치펌
}
