package com.fw.core.dto.hh;

import com.fw.core.dto.CommonDTO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class HhNoticeDTO extends CommonDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private String noticeSeq;    /** 공지 seq */
    private String noticeTypeCd; /** 공지 종류 코드(BOARD_TYPE) */
    private String title;        /** 공지 제목 */
    private String content;      /** 공지 내용 */
    private String fileSeq;      /** 공지 첨부파일 seq */
    private String hit;          /** 조회수 */
    private String delFlag;      /** 삭제여부 */
    private String createSeq;    /** 만든 seq */
    private String createDt;     /** 만든날  */
    private String updateSeq;    /** 수정 seq */
    private String updateDt;     /** 수정날 */

    private String cnt;
    private String searchVal;
}
