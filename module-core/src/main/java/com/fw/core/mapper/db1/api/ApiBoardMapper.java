package com.fw.core.mapper.db1.api;

import com.fw.core.dto.api.ApiBoardContentDTO;
import com.fw.core.dto.api.ApiBoardDTO;
import com.fw.core.dto.api.ApiBoardReplyDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Api BOARD
 * 
 * @author YJW
 */
@Mapper
public interface ApiBoardMapper {

	/**
	 * 게시판 리스트 조회 -> DB설계필요
	 */
	List<ApiBoardDTO> selectBoardList(ApiBoardDTO apiBoardDTO);

	/**
	 * 게시글 리스트 조회 -> DB설계필요
	 */
	List<ApiBoardContentDTO> selectBoardContentList(ApiBoardContentDTO apiBoardContentDTO);

	/**
	 * 게시글 (등록/수정) -> DB설계필요
	 */
	void insertBoardContent(ApiBoardContentDTO apiBoardContentDTO);

	/**
	 * 게시판 댓글 조회 -> DB설계필요
	 */
	List<ApiBoardReplyDTO> selectBoardReplyList(ApiBoardReplyDTO apiBoardReplyDTO);
}
