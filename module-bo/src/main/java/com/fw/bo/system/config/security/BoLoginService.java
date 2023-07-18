package com.fw.bo.system.config.security;

import com.fw.core.dto.bo.BoAdminDTO;
import com.fw.core.dto.bo.BoAdminIpDTO;
import com.fw.core.mapper.db1.bo.BoAdminIpMapper;
import com.fw.core.mapper.db1.bo.BoAdminMapper;
import com.fw.core.util.IpUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 로그인 서비스
 * @author dongbeom
 */
@Service
@RequiredArgsConstructor
public class BoLoginService {

    private final BoAdminMapper boAdminMapper;
    private final BoAdminIpMapper boAdminIpMapper;

    public BoAdminDTO selectAdminForAdminId(BoAdminDTO boAdminDTO) {
        return boAdminMapper.selectAdminForAdminId(boAdminDTO);
    }

    public void updatePasswordFailCount(BoAdminDTO boAdminDTO) {
        boAdminMapper.updatePasswordFailCount(boAdminDTO);
    }

    public void updateResetPasswordFailCount(BoAdminDTO boAdminDTO) {
        boAdminMapper.updateResetPasswordFailCount(boAdminDTO);
    }

    public boolean isAccessIp(BoAdminIpDTO boAdminIpDTO) {
        List<BoAdminIpDTO> ipList = boAdminIpMapper.selectAdminIpList(boAdminIpDTO);
        boolean result = false;
        String ip = IpUtil.getClientIp();

        if(ipList.isEmpty()) {
            result = true;
        } else {
            for (BoAdminIpDTO baid : ipList){
                if (StringUtils.equals(baid.getIp(), ip)){
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

}