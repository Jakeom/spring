package com.fw.core.mapper.db1.fo;

import com.fw.core.dto.fo.FoTokenDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FoTokenMapper {

    // 인증번호 생성
    void insertToken(FoTokenDTO foTokenDTO);

    // 인증번호 확인
    FoTokenDTO selectToken(FoTokenDTO foTokenDTO);

    // 인증번호 사용 처리
    void updateToken(FoTokenDTO foTokenDTO);

}
