package com.fw.core.mapper.db1.bo;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.fw.core.dto.bo.BoFaqDTO;

@Mapper
public interface BoFaqMapper {
  
  List<BoFaqDTO> selectFaqList(BoFaqDTO boFaqDTO);

  List<BoFaqDTO> selectFaqDisplayList(BoFaqDTO boFaqDTO);

  List<BoFaqDTO> selectHHFaqDisplayList(BoFaqDTO boFaqDTO);

  List<BoFaqDTO> searchFaqList(BoFaqDTO boFaqDTO);

  BoFaqDTO selectFaq(BoFaqDTO boFaqDTO);

  BoFaqDTO cntDisplayFlag(BoFaqDTO boFaqDTO);

  void insertFaq(BoFaqDTO boFaqDTO);
  
  void deleteFaq(BoFaqDTO boFaqDTO);
  
  void updateFaq(BoFaqDTO boFaqDTO);

  void updateDisplayFlag(BoFaqDTO boFaqDTO);

  void deleteDisplay(BoFaqDTO boFaqDTO);

}