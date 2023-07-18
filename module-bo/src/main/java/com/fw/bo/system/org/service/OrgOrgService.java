package com.fw.bo.system.org.service;

import com.fw.core.dto.bo.BoOrgDTO;
import com.fw.core.mapper.db1.bo.BoOrgMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 조직 관리 Service
 * @author 문종현
 */
@Service
@RequiredArgsConstructor
public class OrgOrgService {

    private final BoOrgMapper boOrgMapper;

    public List<BoOrgDTO> selectOrgList(BoOrgDTO boOrgDTO) {
        return boOrgMapper.selectOrgList(boOrgDTO);
    }
    
    public void updateOrgInfo(BoOrgDTO boOrgDTO){
		 boOrgMapper.updateOrgInfo(boOrgDTO);
    }
    
    public int selectOrgOverLap(String groupId) {
		return boOrgMapper.selectOrgOverLap(groupId);
	}
    
    public BoOrgDTO selectOrg(BoOrgDTO boOrgDTO){
    	return boOrgMapper.selectOrg(boOrgDTO);
    }
    
    public void insertOrgInfo(BoOrgDTO boOrgDTO){
    	 boOrgMapper.insertOrgInfo(boOrgDTO);
    }

	public void deleteOrgInfo(BoOrgDTO boOrgDTO) {
		boOrgMapper.deleteOrgInfo(boOrgDTO);
	}
   
}
