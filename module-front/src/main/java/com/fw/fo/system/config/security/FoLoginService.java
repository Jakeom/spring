package com.fw.fo.system.config.security;

import com.fw.core.dto.fo.FoMemberDTO;
import com.fw.core.dto.fo.FoSessionDTO;
import com.fw.core.dto.fo.login.FoSimpleLoginDTO;
import com.fw.core.mapper.db1.fo.FoLoginMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FoLoginService implements UserDetailsService {
    private final FoLoginMapper foLoginMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        FoSessionDTO userDTO = foLoginMapper.selectAccountInfo(username);

        if(userDTO == null){
            throw new UsernameNotFoundException(null);
        }

        return userDTO;
    }

    public FoSessionDTO selectDoSimpleLoginInfo(FoSessionDTO foSessionDTO){
        FoSessionDTO userDTO = foLoginMapper.selectDoSimpleLoginInfo(foSessionDTO);
        return userDTO;
    }

    public FoSimpleLoginDTO selectAutoLogin(String memberId){
        return foLoginMapper.selectAutoLogin(memberId);
    }

    public void updateDeleteAutoLogin(String memberId){
        foLoginMapper.updateDeleteAutoLogin(memberId);
    }

    public int selectBlack(String memberId){
        return foLoginMapper.selectBlack(memberId);
    }

    public FoSessionDTO selectTempMember(String username){
        return foLoginMapper.selectTempMember(username);
    }

    public String selectHhApproved(FoSessionDTO foSessionDTO){
        return foLoginMapper.selectHhApproved(foSessionDTO);
    }
}