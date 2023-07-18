package com.fw.batch.hh.job;

import com.fw.batch.push.service.PushService;
import com.fw.core.dto.batch.PushHistBean;
import com.fw.core.dto.fo.FoMemberDTO;
import com.fw.core.dto.hh.HhWefirmDTO;
import com.fw.core.mapper.db1.common.CommonMapper;
import com.fw.core.mapper.db1.hh.HhWefirmMapper;
import org.apache.commons.lang3.StringUtils;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.List;

public class HhResignNoticeJob extends QuartzJobBean implements InterruptableJob {

    @Autowired
    private PushService pushService;

    @Autowired
    private HhWefirmMapper hhWefirmMapper;

    @Autowired
    private CommonMapper commonMapper;

    @Override
    public void interrupt() throws UnableToInterruptJobException {
        // TODO Auto-generated method stub

    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try {
            // 위펌 대표 및 관리자 -> 퇴사자명단 통보요청
            HhWefirmDTO hhWefirmDTO = new HhWefirmDTO();
            List<HhWefirmDTO> wefirmAdminList = hhWefirmMapper.selectWefirmAdminList(hhWefirmDTO);
            if(wefirmAdminList.size() > 0) {
                for (HhWefirmDTO wefirmDTO : wefirmAdminList) {
                    FoMemberDTO foMemberDTO = new FoMemberDTO();
                    foMemberDTO.setMemberId(wefirmDTO.getMemberId());
                    FoMemberDTO member = commonMapper.selectMemberInfoByMemberId(foMemberDTO);

                    if (StringUtils.isNotBlank(member.getPushToken()) && StringUtils.isNotBlank(member.getPhoneType())) {
                        PushHistBean ph = new PushHistBean();
                        ph.setDispType("RESIGN_NOTICE");
                        ph.setMemberId(wefirmDTO.getMemberId());
                        ph.setTitle("퇴사자명단 통보요청");
                        ph.setContent("지난 1개월 간 귀사 위펌의 퇴사자 명단을 통보 바랍니다.(직업안정법 위반)");
                        ph.setPhoneType(member.getPhoneType());
                        ph.setPhoneToken(member.getPushToken());
                        pushService.insertPushHist(ph);
                    }
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
