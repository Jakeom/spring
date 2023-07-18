package com.fw.batch;

import com.fw.batch.fo.job.ApPositionAlertJob;
import com.fw.batch.fo.job.PositionChangeStatusJob;
import com.fw.batch.hh.job.HhApResumeAlertJob;
import com.fw.batch.hh.job.HhResignNoticeJob;
import com.fw.batch.hh.job.MyApExpiredResumeJob;
import com.fw.batch.push.job.PushSendJob;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

import static org.quartz.JobBuilder.newJob;

@Controller
public class SchedulerController {

	private final Log logger = LogFactory.getLog(getClass());
	private Map<String, Object> dbMap = new HashMap<String, Object>();

	@Autowired
    private Scheduler scheduler;

	// -------------------- 스프링 부트 배치 (quartz) [start] -----------------------------
    @PostConstruct
    public void start() {
        try {
			

            // 푸시전송 처리를 30초 단위로 실행함
            JobDetail recentDetail = buildJobDetail(
                PushSendJob.class, "batch", "test1", dbMap);
            scheduler.scheduleJob(recentDetail, buildJobTrigger("0/30 * * * * ?"));

            // 기간마료 이력서 자동 오픈 + 헤드헌터에게 포인트 지급 처리를 3시간 단위로 실행함
            JobDetail myApExpiredResumeJob = buildJobDetail(
                MyApExpiredResumeJob.class, "batch", "test2", dbMap);
            scheduler.scheduleJob(myApExpiredResumeJob, buildJobTrigger("0 */3 * * * ?"));

            // 헤드헌터 맞춤인재 알림 등록 1시간 단위로 실행
            JobDetail hhApResumeAlertJob  = buildJobDetail(
                HhApResumeAlertJob.class, "batch", "test3", dbMap);
            scheduler.scheduleJob(hhApResumeAlertJob, buildJobTrigger("0 */1 * * * ?"));

            // 후보자 맞춤채용공고 알림 등록 1시간 단위로 실행
            JobDetail apPositionAlertJob  = buildJobDetail(
                ApPositionAlertJob.class, "batch", "test4", dbMap);
            scheduler.scheduleJob(apPositionAlertJob, buildJobTrigger("0 */1 * * * ?"));

            // 퇴사자명단 통보요청 매월1일 1달 단위로 실행
            JobDetail hhResignNoticeJob  = buildJobDetail(
                HhResignNoticeJob.class, "batch", "test5", dbMap);
            scheduler.scheduleJob(hhResignNoticeJob, buildJobTrigger("0 0 0 1 1/1 ? *"));

            // 포지션 마감처리 하루 단위로 실행
            JobDetail positionChangeStatusJob  = buildJobDetail(
                PositionChangeStatusJob.class, "batch", "test6", dbMap);
            scheduler.scheduleJob(positionChangeStatusJob, buildJobTrigger("0 0 0 1/1 * ? *"));
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    //String scheduleExp ="0 40 11 * * ?"; 초 분 시 일 월 ?
    public Trigger buildJobTrigger(String scheduleExp) {
        return TriggerBuilder.newTrigger()
                .withSchedule(CronScheduleBuilder.cronSchedule(scheduleExp)).build();
    }

    public JobDetail buildJobDetail(Class job, String name, String group, Map params) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.putAll(params);
        return newJob(job).withIdentity(name, group)
                .usingJobData(jobDataMap)
                .build();
    }
    // -------------------- 스프링 부트 배치 (quartz) [end] -----------------------------
}