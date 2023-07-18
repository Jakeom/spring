package com.fw.batch.fo.job;


import com.fw.core.dto.fo.FoPositionDTO;
import com.fw.core.mapper.db1.fo.FoPositionMapper;
import lombok.extern.slf4j.Slf4j;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@Slf4j
public class PositionChangeStatusJob extends QuartzJobBean implements InterruptableJob {
    
    @Autowired
    private FoPositionMapper foPositionMapper;

    @Override
    public void interrupt() throws UnableToInterruptJobException {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try {
            FoPositionDTO foPositionDTO = new FoPositionDTO();
            List<FoPositionDTO> list = foPositionMapper.selectDoingPositionList(foPositionDTO); // 진행중인 포지션 조회

            for(FoPositionDTO f : list) {
                LocalDate today = LocalDate.now();
                LocalDate endDate = LocalDate.parse(f.getEndDate());
               if(endDate.isBefore(today)) { // 마감일 지난경우 포지션 종료처리
                    foPositionDTO.setStatus("END");
                    foPositionDTO.setId(f.getId());
                    foPositionMapper.updatePositionStatus(foPositionDTO);
               }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

    
}