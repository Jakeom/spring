package com.fw.fo.login.service;


import com.fw.core.dto.fo.login.FoSimpleLoginDTO;
import com.fw.core.mapper.db1.fo.FoSimpleLoginMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class FoSimpleLoginService {

    private final FoSimpleLoginMapper foSimpleLoginMapper;

    public FoSimpleLoginDTO selectSimpleLoginInfo(FoSimpleLoginDTO foSimpleLoginDTO){
        return foSimpleLoginMapper.selectSimpleLoginInfo(foSimpleLoginDTO);
    }
    @Transactional
    public void insertMemberSimpleAuth(FoSimpleLoginDTO foSimpleLoginDTO){
        foSimpleLoginMapper.updateMemberSimpleAuthDel(foSimpleLoginDTO);
        foSimpleLoginMapper.insertMemberSimpleAuth(foSimpleLoginDTO);
    }

}
