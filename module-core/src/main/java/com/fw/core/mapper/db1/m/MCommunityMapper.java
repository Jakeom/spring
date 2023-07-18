package com.fw.core.mapper.db1.m;

import com.fw.core.dto.fo.FoTbCommunityCommentDTO;
import com.fw.core.dto.fo.FoTbCommunityDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MCommunityMapper {

    List<FoTbCommunityDTO> selectCommunityList(FoTbCommunityDTO foTbCommunityDTO);

    List<FoTbCommunityCommentDTO> selectCommunityCommentList(FoTbCommunityCommentDTO foTbCommunityCommentDTO);

    FoTbCommunityDTO selectCommunityDetail(FoTbCommunityDTO foTbCommunityDTO);

    FoTbCommunityDTO selectRecommend(FoTbCommunityDTO foTbCommunityDTO);

    void updateRecommend(FoTbCommunityDTO foTbCommunityDTO);

    void updateRecommendComment(FoTbCommunityDTO foTbCommunityDTO);

    void updateCommunityHit(FoTbCommunityDTO foTbCommunityDTO);

    void insertCommunity(FoTbCommunityDTO foTbCommunityDTO);

    void insertCommunityRecommend(FoTbCommunityDTO foTbCommunityDTO);

    void deleteCommunityRecommend(FoTbCommunityDTO foTbCommunityDTO);

    void insertCommunityComment(FoTbCommunityCommentDTO foTbCommunityCommentDTO);

    List<FoTbCommunityCommentDTO> selectCommentMaxDepth(FoTbCommunityCommentDTO foTbCommunityCommentDTO);

    List<FoTbCommunityCommentDTO> selectFindDepth(FoTbCommunityCommentDTO foTbCommunityCommentDTO);

    FoTbCommunityCommentDTO selectCommentSeq(FoTbCommunityCommentDTO foTbCommunityCommentDTO);

    List<FoTbCommunityDTO> selectBestList(FoTbCommunityDTO foTbCommunityDTO);

    List<FoTbCommunityDTO> selectCommunityCategory(FoTbCommunityDTO foTbCommunityDTO);

    FoTbCommunityDTO selectHeadhunterByCompanySeq(FoTbCommunityDTO foTbCommunityDTO);

}
