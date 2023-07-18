package com.fw.core.mapper.db1.api;

import com.fw.core.dto.api.ApiTokenDTO;

/***
 * @author YJW
 */
public interface ApiCertMapper {

    // 인증번호 생성
    void insertToken(ApiTokenDTO apiTokenDTO);

    // 인증번호 확인
    ApiTokenDTO selectToken(ApiTokenDTO apiTokenDTO);

    // 인증번호 사용 처리
    void updateToken(ApiTokenDTO apiTokenDTO);
}
