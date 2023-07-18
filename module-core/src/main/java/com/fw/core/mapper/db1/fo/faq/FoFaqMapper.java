package com.fw.core.mapper.db1.fo.faq;

import com.fw.core.dto.fo.faq.FoFaqDTO;
import com.fw.core.dto.fo.faq.FoFaqDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FoFaqMapper {


    // 카테고리 리스트 취득
    public List<FoFaqDTO> selectFaqCateList(FoFaqDTO foFaqDTO);

    // 카테고리 리스트 취득
    public List<FoFaqDTO> selectFaqList(FoFaqDTO foFaqDTO);

    // 카테고리 리스트 취득
    public List<FoFaqDTO> selectMainFaqList(FoFaqDTO foFaqDTO);


}
