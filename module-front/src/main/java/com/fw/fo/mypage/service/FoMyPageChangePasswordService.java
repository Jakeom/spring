package com.fw.fo.mypage.service;

import com.fw.core.dto.fo.FoMemberDTO;
import com.fw.core.mapper.db1.fo.FoMemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class FoMyPageChangePasswordService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final FoMemberMapper foMemberMapper;

    public void updateChangePassword(FoMemberDTO foMemberDTO){
        foMemberDTO.setNewPassword(bCryptPasswordEncoder.encode(foMemberDTO.getNewPassword()));
        foMemberMapper.updateChangePassword(foMemberDTO);
    }

    public FoMemberDTO selectMemberPassword(FoMemberDTO foMemberDTO){
        return foMemberMapper.selectMemberPassword(foMemberDTO);
    }
}
