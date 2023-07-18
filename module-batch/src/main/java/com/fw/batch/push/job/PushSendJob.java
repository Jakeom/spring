package com.fw.batch.push.job;

import org.springframework.stereotype.Component;

import com.fw.batch.push.service.PushSendService;
import com.fw.batch.push.service.PushService;
import com.fw.core.dto.api.ApiCompanyDTO;

import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class PushSendJob extends QuartzJobBean implements InterruptableJob {
    
    @Autowired
    private PushSendService pushSendService;

    @Autowired
    private PushService pushService;

    @Override
    public void interrupt() throws UnableToInterruptJobException {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        // TODO Auto-generated method stub
        // ApiCompanyDTO aa = new ApiCompanyDTO();
        // aa.setCompanyId("28");
        // ApiCompanyDTO tmp = pushSendService.selectCompanyDetail(aa);
        // System.out.println(tmp);

        try {
            pushService.insertPush();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.error("error", e);
        }
        
    }

    
}
