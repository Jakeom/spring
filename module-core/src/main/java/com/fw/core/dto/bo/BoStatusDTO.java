package com.fw.core.dto.bo;

import com.fw.core.dto.CommonDTO;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoStatusDTO extends CommonDTO {

    private String memberCnt;
    private String quitMemberCnt;
    private String joinMemberCnt;
    private String todayMemberCnt;
    private String todayQuitMemberCnt;
    private String todayJoinMemberCnt;
    private String apMemberCnt;
    private String quitApMemberCnt;
    private String joinApMemberCnt;
    private String todayApMemberCnt;
    private String todayApQuitMemberCnt;
    private String todayApJoinMemberCnt;
    private String hhMemberCnt;
    private String quitHhMemberCnt;
    private String joinHhMemberCnt;
    private String todayHhMemberCnt;
    private String todayHhQuitMemberCnt;
    private String todayHhJoinMemberCnt;
    private String resumeCnt;
    private String openResumeCnt;
    private String closeResumeCnt;
    private String todayResumeCnt;
    private String todayOpenResumeCnt;
    private String todayCloseResumeCnt;
    private String monthApMemberCnt;
    private String monthApQuitMemberCnt;
    private String monthApJoinMemberCnt;
    private String monthHhMemberCnt;
    private String monthHhQuitMemberCnt;
    private String monthHhJoinMemberCnt;
    private String monthResumeCnt;
    private String monthOpenResumeCnt;
    private String monthCloseResumeCnt;
    private String monthMemberCnt;
    private String monthQuitMemberCnt;
    private String monthJoinMemberCnt;

    private String startDate;
    private String endDate;
    private String date;

}
