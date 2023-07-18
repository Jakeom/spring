package com.fw.core.mapper.db1.fo.searchHh;

import com.fw.core.dto.fo.headhunter.FoSearchHhDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FoSearchHhMapper {
    //헤드헌터검색 카운트 취득
    public int selectSearchHhListCntForPaging(FoSearchHhDTO foSearchHhDTO);

    //  헤드헌터검색 조회 페이징
    public List<FoSearchHhDTO> selectSearchHhListPaging(FoSearchHhDTO foSearchHhDTO);

    //  헤드헌터보기
    public FoSearchHhDTO selectHhInfo(FoSearchHhDTO foSearchHhDTO);

    //헤트헌터 필드
    public List<FoSearchHhDTO> selectHhFieldInfo(FoSearchHhDTO foSearchHhDTO);

    //관심헤드헌터 등록
    public void insertInterestHh(FoSearchHhDTO foSearchHhDTO);

    //관심헤드헌터 취소
    public void updateInterestHh(FoSearchHhDTO foSearchHhDTO);
}
