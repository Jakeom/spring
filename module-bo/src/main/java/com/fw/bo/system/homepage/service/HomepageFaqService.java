package com.fw.bo.system.homepage.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fw.core.dto.bo.BoFaqCategoryDTO;
import com.fw.core.dto.bo.BoFaqDTO;
import com.fw.core.mapper.db1.bo.BoFaqCategoryMapper;
import com.fw.core.mapper.db1.bo.BoFaqMapper;

import lombok.RequiredArgsConstructor;

/**
 * @author dongbeom
 */
@Service
@RequiredArgsConstructor
public class HomepageFaqService {
  
  private final BoFaqMapper boFaqMapper;
  private final BoFaqCategoryMapper boFaqCategoryMapper;
  
  public List<BoFaqDTO> selectFaqList(BoFaqDTO boFaqDTO) {
    return boFaqMapper.selectFaqList(boFaqDTO);
  }

  public List<BoFaqDTO> selectFaqDisplayList(BoFaqDTO boFaqDTO) {
    return boFaqMapper.selectFaqDisplayList(boFaqDTO);
  }

  public List<BoFaqDTO> selectHHFaqDisplayList(BoFaqDTO boFaqDTO) {
    return boFaqMapper.selectHHFaqDisplayList(boFaqDTO);
  }

  public List<BoFaqDTO> searchFaqList(BoFaqDTO boFaqDTO) {
    return boFaqMapper.searchFaqList(boFaqDTO);
  }

  public BoFaqDTO selectFaq(BoFaqDTO boFaqDTO) {
    return boFaqMapper.selectFaq(boFaqDTO);
  }
  
  public List<BoFaqCategoryDTO> selectCategoryNm(BoFaqCategoryDTO boFaqCategoryDTO) {
    return boFaqCategoryMapper.selectCategoryNm(boFaqCategoryDTO);
  }
  
  public void insertFaq(BoFaqDTO boFaqDTO) {
    boFaqMapper.insertFaq(boFaqDTO);
  }
  
  public void insertCategory(BoFaqCategoryDTO boFaqCategoryDTO) {
    boFaqCategoryMapper.insertCategory(boFaqCategoryDTO);
  }
  
  public void deleteCategory(BoFaqCategoryDTO boFaqCategoryDTO) {
    boFaqCategoryMapper.deleteCategory(boFaqCategoryDTO);
    boFaqCategoryMapper.deleteCategoryFAQ(boFaqCategoryDTO);
  }
  
  public void deleteFaq(BoFaqDTO boFaqDTO) {
    boFaqMapper.deleteFaq(boFaqDTO);
  }
  
  public void deleteDisplay(BoFaqDTO boFaqDTO) {
    boFaqMapper.deleteDisplay(boFaqDTO);
  }
  public void updateFaq(BoFaqDTO boFaqDTO) {
    boFaqMapper.updateFaq(boFaqDTO);
  }

  public void updateDisplayFlag(BoFaqDTO boFaqDTO) {
    int size = boFaqDTO.getCheckSeq().size();
    for(int i = 0; i < size; i++) {
      boFaqDTO.setFaqSeq(boFaqDTO.getCheckSeq().get(i));
      boFaqDTO.setDisplayFlag(boFaqDTO.getDisplayFlagCheck().get(i));
      boFaqMapper.updateDisplayFlag(boFaqDTO);
    }
  }

}