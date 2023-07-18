package com.fw.core.mapper.db1.api;

import com.fw.core.dto.api.ApiApAlertDTO;
import com.fw.core.dto.api.ApiPushHistDTO;

import java.util.List;

/***
 * @author wsh
 */
public interface ApiAlarmMapper {

	/** 알림 리스트 */
	List<ApiPushHistDTO> selectAlarmList(ApiPushHistDTO apiPushHistDTO);

	/** 알림 카운트 */
	int selectAlarmCnt(ApiPushHistDTO apiPushHistDTO);

	/** 알림 수정 */
	void updateAlarm(ApiPushHistDTO apiPushHistDTO);

	/** NEW 헤드헌터 알림설정 조회 */
	ApiApAlertDTO selectAlarmSettingHh(ApiApAlertDTO apiApAlertDTO);

	/** 후보자 알림설정 조회 (AP) */
	ApiApAlertDTO selectAlarmSettingAp(ApiApAlertDTO apiApAlertDTO);

	/** 맞춤 채용공고 직무 리스트 (AP) */
	List<ApiApAlertDTO> selectApIndustryList(ApiApAlertDTO apiApAlertDTO);

	/** 알림설정 수정 (AP) */
	void updateApAlarmFlag(ApiApAlertDTO apiApAlertDTO);

	/** 맞춤 채용공고 직무 삭제 (AP) */
	void updateApIndustry(ApiApAlertDTO apiApAlertDTO);

	/** 맞춤 채용공고 직무 등록 (AP) */
	void insertApIndustry(ApiApAlertDTO apiApAlertDTO);

	/** 알림설정 수정 (HH) */
	void updateHhAlarmFlag(ApiApAlertDTO apiApAlertDTO);

	/** 맞춤 인재 알림 존재여부(등록/수정 분별) */
	boolean selectApAlertCheck(ApiApAlertDTO apiApAlertDTO);

	/** 맞춤 인재 알림 설정 등록 (HH) */
	void insertHhAlarmSetting(ApiApAlertDTO apiApAlertDTO);

	/** 맞춤 인재 알림 설정 수정 (HH) */
	void updateHhAlarmSetting(ApiApAlertDTO apiApAlertDTO);

	/** hp2등록 (HH) */
	void insertHp2(ApiApAlertDTO apiApAlertDTO);

	/** loc등록 (HH) */
	void insertLoc(ApiApAlertDTO apiApAlertDTO);

	/** hp2삭제 (HH) */
	void deleteHp2(ApiApAlertDTO apiApAlertDTO);

	/** loc삭제 (HH) */
	void deleteLoc(ApiApAlertDTO apiApAlertDTO);

	/** apAlertSeq 조회 (수정 시) */
	ApiApAlertDTO selectApAlertSeq(ApiApAlertDTO apiApAlertDTO);
}
