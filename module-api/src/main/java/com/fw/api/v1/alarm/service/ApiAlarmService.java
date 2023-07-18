package com.fw.api.v1.alarm.service;

import com.fw.core.dto.api.ApiApAlertDTO;
import com.fw.core.dto.api.ApiPushHistDTO;
import com.fw.core.mapper.db1.api.ApiAlarmMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author wsh
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ApiAlarmService {

	private final ApiAlarmMapper apiAlarmMapper;

	/** 알림 리스트 */
	public List<ApiPushHistDTO> selectAlarmList(ApiPushHistDTO apiPushHistDTO) {
		return apiAlarmMapper.selectAlarmList(apiPushHistDTO);
	}

	/** 알림 카운트 */
	public int selectAlarmCnt(ApiPushHistDTO apiPushHistDTO) {
		return apiAlarmMapper.selectAlarmCnt(apiPushHistDTO);
	}

	/** 알림 수정 */
	public void updateAlarm(ApiPushHistDTO apiPushHistDTO) {
		apiPushHistDTO.setReceiveYn("Y");
		apiAlarmMapper.updateAlarm(apiPushHistDTO);
	}

	/** NEW 후보자 알림설정 조회 */
	public ApiApAlertDTO selectAlarmSettingAp(ApiApAlertDTO apiApAlertDTO) {
		return apiAlarmMapper.selectAlarmSettingAp(apiApAlertDTO);
	}

	/** NEW 후보자 맞춤 채용공고 리스트 조회 */
	public List<ApiApAlertDTO> selectApIndustryList(ApiApAlertDTO apiApAlertDTO) {
		return apiAlarmMapper.selectApIndustryList(apiApAlertDTO);
	}

	/** NEW 후보자 알림설정 수정 */
	public void updateAlarmSettingAp(ApiApAlertDTO apiApAlertDTO) {

		// agreeMarketing 값 변환
		if (Objects.equals(apiApAlertDTO.getAgreeMarketing(),"Y")) {
			apiApAlertDTO.setAgreeMarketing("1");
		} else if (Objects.equals(apiApAlertDTO.getAgreeMarketing(), "N")) {
			apiApAlertDTO.setAgreeMarketing("0");
		}

		apiAlarmMapper.updateApAlarmFlag(apiApAlertDTO); // 알림 Flag update

		if(StringUtils.isNotBlank(apiApAlertDTO.getIndustry())) { // industry 파라미터 존재 시 맞춤 채용공고 직무 등록
			apiAlarmMapper.insertApIndustry(apiApAlertDTO);
		}

		if(StringUtils.isNotBlank(apiApAlertDTO.getPositionAlertSeq())) { // positionAlertSeq 파라미터 존재 시 맞춤 채용공고 직무 삭제
			apiAlarmMapper.updateApIndustry(apiApAlertDTO);
		}
	}

	/** NEW 헤드헌터 알림설정 조회 */
	public ApiApAlertDTO selectAlarmSettingHh(ApiApAlertDTO apiApAlertDTO) {
		return apiAlarmMapper.selectAlarmSettingHh(apiApAlertDTO);
	}

	/** 헤드헌터 알림설정 수정 */
	public void updateAlarmSettingHh(ApiApAlertDTO apiApAlertDTO) {
		// APP '선택' 체크 시 null처리
		if(StringUtils.isBlank(apiApAlertDTO.getCareerSt())) {
			apiApAlertDTO.setCareerSt(null);
		}
		if(StringUtils.isBlank(apiApAlertDTO.getCareerEn())) {
			apiApAlertDTO.setCareerEn(null);
		}
		if(StringUtils.isBlank(apiApAlertDTO.getAgeSt())) {
			apiApAlertDTO.setAgeSt(null);
		}
		if(StringUtils.isBlank(apiApAlertDTO.getAgeEn())) {
			apiApAlertDTO.setAgeEn(null);
		}
		if(StringUtils.isBlank(apiApAlertDTO.getSalarySt())) {
			apiApAlertDTO.setSalarySt(null);
		}
		if(StringUtils.isBlank(apiApAlertDTO.getSalaryEn())) {
			apiApAlertDTO.setSalaryEn(null);
		}

		// agreeMarketing 값 변환
		if (Objects.equals(apiApAlertDTO.getAgreeMarketing(),"Y")) {
			apiApAlertDTO.setAgreeMarketing("1");
		} else if (Objects.equals(apiApAlertDTO.getAgreeMarketing(), "N")) {
			apiApAlertDTO.setAgreeMarketing("0");
		}

		apiAlarmMapper.updateHhAlarmFlag(apiApAlertDTO);

		// 맞춤 인재 알림 설정 (등록/수정)
		boolean check  = apiAlarmMapper.selectApAlertCheck(apiApAlertDTO);
		if(check) { // 수정
			apiAlarmMapper.updateHhAlarmSetting(apiApAlertDTO);

			// hp2 , loc 수정 시 apAlertSeq setting
			ApiApAlertDTO apAlertSeq = new ApiApAlertDTO();
			apAlertSeq = apiAlarmMapper.selectApAlertSeq(apiApAlertDTO);

			apiApAlertDTO.setApAlertSeq(apAlertSeq.getApAlertSeq());

			if(apiApAlertDTO.getHp2() != null && apiApAlertDTO.getHp2().length > 0) { // hp2 파라미터 존재 시 삭제 후 등록
				apiAlarmMapper.deleteHp2(apiApAlertDTO);
				apiAlarmMapper.insertHp2(apiApAlertDTO);
			}

			if(apiApAlertDTO.getLoc() != null && apiApAlertDTO.getLoc().length > 0) { // loc 파라미터 존재 시 삭제 후 등록
				apiAlarmMapper.deleteLoc(apiApAlertDTO);
				apiAlarmMapper.insertLoc(apiApAlertDTO);
			}
		} else { // 등록
			apiAlarmMapper.insertHhAlarmSetting(apiApAlertDTO);

			if(apiApAlertDTO.getHp2() != null && apiApAlertDTO.getHp2().length > 0) { // hp2 파라미터 존재 시 등록
				apiAlarmMapper.insertHp2(apiApAlertDTO);
			}

			if(apiApAlertDTO.getLoc() != null && apiApAlertDTO.getLoc().length > 0) { // loc 파라미터 존재 시 등록
				apiAlarmMapper.insertLoc(apiApAlertDTO);
			}
		}
	}
}
