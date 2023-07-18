package com.fw.core.mapper.db1.bo;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.fw.core.dto.bo.BoFaqCategoryDTO;

/**
 * 
 * @author dongbeom
 *
 */
@Mapper
public interface BoFaqCategoryMapper {
  
  List<BoFaqCategoryDTO> selectCategoryNm(BoFaqCategoryDTO boFaqCategoryDTO);
  
  void insertCategory(BoFaqCategoryDTO boFaqCategoryDTO);

  void deleteCategory(BoFaqCategoryDTO boFaqCategoryDTO);

  void deleteCategoryFAQ(BoFaqCategoryDTO boFaqCategoryDTO);

}
