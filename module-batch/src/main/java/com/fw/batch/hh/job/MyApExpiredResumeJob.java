package com.fw.batch.hh.job;


import org.springframework.stereotype.Component;

import com.fw.batch.hh.service.MyApService;
import com.fw.core.dto.fo.FoSessionDTO;
import com.fw.core.dto.hh.HhMyapListDTO;

import java.util.List;

import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MyApExpiredResumeJob extends QuartzJobBean implements InterruptableJob {
    
    @Autowired
    private MyApService myApService;

    @Override
    public void interrupt() throws UnableToInterruptJobException {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try {
            
            // 만료된 이력서 데이타 취득 (헤드헌터 일련번호, 이력서 일련번호)
            List<HhMyapListDTO> expiredList = myApService.selectHhMyapExpiredList();
            for(HhMyapListDTO hhMyapListDTO : expiredList){
                FoSessionDTO tmpFo = new FoSessionDTO();
                tmpFo.setId(hhMyapListDTO.getMemberId());
                hhMyapListDTO.setFrontSession(tmpFo);
                hhMyapListDTO.setOpened("1");
                // 최초 지급 플래그 변경 + 이력서 자동 오픈
                myApService.updateHhMyapListResumeOpend(hhMyapListDTO);
                // 포인트 지급
                myApService.addExpiredPoint(hhMyapListDTO);
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

    
}