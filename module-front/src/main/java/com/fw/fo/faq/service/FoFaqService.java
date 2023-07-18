package com.fw.fo.faq.service;

import com.fw.core.dto.fo.faq.FoFaqDTO;
import com.fw.core.mapper.db1.fo.faq.FoFaqMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FoFaqService {

    private final FoFaqMapper foFaqMapper;

    //카테고리 리스트 취득
    public List<FoFaqDTO> selectFaqCateList(FoFaqDTO foFaqDTO) {
        return foFaqMapper.selectFaqCateList(foFaqDTO);
    }

    // faq 리스트 취득
    public List<FoFaqDTO> selectFaqList(FoFaqDTO foFaqDTO) {
        return foFaqMapper.selectFaqList(foFaqDTO);
    }

    // faq 리스트 취득
    public List<FoFaqDTO> selectMainFaqList(FoFaqDTO foFaqDTO) {
        return foFaqMapper.selectMainFaqList(foFaqDTO);
    }


}