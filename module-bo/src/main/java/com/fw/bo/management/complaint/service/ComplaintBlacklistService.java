package com.fw.bo.management.complaint.service;


import com.fw.core.dto.bo.BoMemberDTO;
import com.fw.core.dto.bo.BoTbBlacklistDTO;
import com.fw.core.mapper.db1.bo.BoBlacklistMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComplaintBlacklistService {
    private final BoBlacklistMapper boBlacklistMapper;

    public List<BoMemberDTO> selectMemberList(BoMemberDTO boMemberDTO) {
        return boBlacklistMapper.selectMemberList(boMemberDTO);
    }

    public int selectMemberCnt(BoMemberDTO boMemberDTO) {
        return boBlacklistMapper.selectMemberCnt(boMemberDTO);
    }

    public List<BoTbBlacklistDTO> selectBlacklistList(BoTbBlacklistDTO boTbBlacklistDTO) {
        return boBlacklistMapper.selectBlacklistList(boTbBlacklistDTO);
    }

    public void insertBlacklist(BoTbBlacklistDTO boTbBlacklistDTO) {
        if(boTbBlacklistDTO.getBlacklistType().equals("GUEST")) {
            boBlacklistMapper.insertBlacklistGuest(boTbBlacklistDTO);
        } else {
            int size = boTbBlacklistDTO.getCheckSeq().size();
            for(int i = 0; i < size; i++) {
                boTbBlacklistDTO.setMemberId(boTbBlacklistDTO.getCheckSeq().get(i));
                boBlacklistMapper.insertBlacklist(boTbBlacklistDTO);
            }
        }

    }

    public void updateBlacklist(BoTbBlacklistDTO boTbBlacklistDTO) {
        boBlacklistMapper.updateBlacklist(boTbBlacklistDTO);
    }

    public void deleteBlacklist(BoTbBlacklistDTO boTbBlacklistDTO) {
        boBlacklistMapper.deleteBlacklist(boTbBlacklistDTO);
    }

    public BoTbBlacklistDTO selectBlacklistDetail(BoTbBlacklistDTO boTbBlacklistDTO) {
        return boBlacklistMapper.selectBlacklistDetail(boTbBlacklistDTO);
    }
}
