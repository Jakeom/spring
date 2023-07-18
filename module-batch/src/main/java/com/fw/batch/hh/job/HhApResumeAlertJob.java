package com.fw.batch.hh.job;


import com.fw.batch.hh.service.HhApResumeAlertService;
import com.fw.batch.push.service.PushService;
import com.fw.core.dto.batch.PushHistBean;
import com.fw.core.dto.hh.HhMyapListDTO;
import com.fw.core.mapper.db1.hh.HhApSearchsMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class HhApResumeAlertJob extends QuartzJobBean implements InterruptableJob {

    @Value("${service.web_domain}")
    private String WEB_DOMAIN;
    @Autowired
    private HhApSearchsMapper hhApSearchsMapper;

    @Autowired
    private PushService pushService;

    @Override
    public void interrupt() throws UnableToInterruptJobException {
        // TODO Auto-generated method stub
        
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try {
            
            // 알람 설정유저 취득
            List<HhMyapListDTO> alertTartets = hhApSearchsMapper.selectHhApAlertList();

            // 1시간 이내에 등록된 조건에 맞는 이력서 취득
            for(HhMyapListDTO searchData : alertTartets) {
                // 희망근무지 값 설정
                if(searchData.getWorkplace() != null && searchData.getWorkplace().length()>0){
                    searchData.setWorkplaceArr(searchData.getWorkplace().split(","));
                }

                // 학력2 값 설정
                if(searchData.getEdu2() != null && searchData.getEdu2().length()>0){
                    searchData.setEdu2Arr(searchData.getEdu2().split(","));
                }

                // 키워드 값 설정
                if(searchData.getKeyword() != null){
                    ArrayList<String> andArr = new ArrayList<>();
                    String and[] = searchData.getKeyword().split(",");
                    for (String andData : and) {
                        
                        String or[] = andData.split(" ");
                        ArrayList<String> orArr = new ArrayList<>();
                        for(String orData : or) {
                            if(orData.length() != 0) {
                                orArr.add(orData.trim());
                            }
                        }
                        andArr = orArr;
                    }
                    String[] arr = andArr.toArray(new String[0]);
                    searchData.setKeywordArr(arr);
                }

                // 1시간 이내 조건 
                List<HhMyapListDTO> dataList = hhApSearchsMapper.selectHhApListByBatch(searchData);
                if(dataList.size() > 0){
                    // 취득된 이력서 내용으로 헤드헌터에게 푸시보낼 내용 등록
                    for(HhMyapListDTO data : dataList){
                        PushHistBean ph = new PushHistBean();
                        ph.setDispType("CUSTOM_AP");
                        ph.setMemberId(searchData.getMemberId());
                        ph.setTitle("[헤드헌터] 맞춤인재알림");
                        ph.setContent("맞춤인재알림\n"+ data.getName() + (StringUtils.isBlank(data.getBirthNm()) ? "" : data.getBirthNm())  +"\n맞춤인재를 발견했습니다. \n\n클릭하면 이력서 상세페이지 이동");
                        ph.setPhoneToken(searchData.getPushToken());
                        ph.setPhoneType(searchData.getPhoneType());
                        ph.setCreateId("BATCH");
                        ph.setUrl(WEB_DOMAIN + "/m/resume/resume-detail-hh?resumeId=" + data.getResumeId() + "&memberId=" + searchData.getMemberId());
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