package com.fw.core.mapper.db1.fo.notice;

import com.fw.core.dto.fo.notice.FoNoticeDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FoNoticeMapper {

    //  리스트 카운트 취득
    public int selectNoticeListCntForPaging(FoNoticeDTO foNoticeDTO);

    // 공고 리스트 취득
    public List<FoNoticeDTO> selectNoticeListPaging(FoNoticeDTO foNoticeDTO);

    //공고 상세보기

    public FoNoticeDTO selectNoticeInfo(FoNoticeDTO foNoticeDTO);

    // 메인 공지사항
    public List<FoNoticeDTO> selectNoticeList(FoNoticeDTO foNoticeDTO);

    public void updateNoticeHit(FoNoticeDTO foNoticeDTO);
}
