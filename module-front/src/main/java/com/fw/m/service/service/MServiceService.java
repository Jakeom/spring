package com.fw.m.service.service;

import com.fw.core.dto.m.MServiceDTO;
import com.fw.core.mapper.db1.m.MServiceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MServiceService {
    private final MServiceMapper mServiceMapper;

    public MServiceDTO selectNoticeInfoM(MServiceDTO mServiceDTO) {
        return mServiceMapper.selectNoticeInfoM(mServiceDTO);
    }

    public MServiceDTO selectFaqInfoM(MServiceDTO mServiceDTO) {
        return mServiceMapper.selectFaqInfoM(mServiceDTO);
    }
}
