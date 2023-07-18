package com.fw.batch.fo.job;


import com.fw.batch.push.service.PushService;
import com.fw.core.dto.batch.PushHistBean;
import lombok.extern.slf4j.Slf4j;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ApPositionAlertJob extends QuartzJobBean implements InterruptableJob {

    @Value("${service.web_domain}")
    private String WEB_DOMAIN;
    @Autowired
    private PushService pushService;

    @Override
    public void interrupt() throws UnableToInterruptJobException {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try {
            // 1시간 이내에 등록된 키워드에 맞는 포이션 데이타 취득 후 푸시 발송 등록
            ///fo/user/jop/posting/detail?id=  url
            PushHistBean bean = new PushHistBean();
            bean.setWebDomain(WEB_DOMAIN);
            pushService.insertApPositionAlert(bean);
        } catch (Exception e) {
            log.error("error", e);
        }
        
    }

    
}