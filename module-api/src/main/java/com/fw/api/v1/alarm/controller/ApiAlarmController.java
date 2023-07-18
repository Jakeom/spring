package com.fw.api.v1.alarm.controller;

import com.fw.api.v1.alarm.service.ApiAlarmService;
import com.fw.core.code.ResponseCode;
import com.fw.core.dto.api.ApiApAlertDTO;
import com.fw.core.dto.api.ApiPushHistDTO;
import com.fw.core.vo.ResponseVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Alarm Controller
 * 
 * @author wsh
 */

@Api("Alarm API")
@RequestMapping("/api/v1")
@Slf4j
@RestController
@RequiredArgsConstructor
public class ApiAlarmController {

	private final ApiAlarmService apiAlarmService;

	/**
	 * 알림 리스트 조회
	 */
	@ApiOperation(value = "알림 리스트", notes = "")
	@PostMapping("/alarm/list")
	@ResponseBody
	public ResponseEntity<ResponseVO> selectAlarmList(@RequestBody ApiPushHistDTO apiPushHistDTO) {
		ResponseCode code = ResponseCode.SUCCESS;

		/** 필수값 체크 */
		String memberId = apiPushHistDTO.getMemberId();
		if (StringUtils.isBlank(memberId)) {
			code = ResponseCode.INVALID_PARAMETER;
			return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(code).build());
		}

		Map<String, Object> map = new HashMap<>();
		map.put("totalCount", apiAlarmService.selectAlarmCnt(apiPushHistDTO));
		map.put("list", apiAlarmService.selectAlarmList(apiPushHistDTO));

		return ResponseEntity.status(code.getHttpStatus())
				.body(ResponseVO.builder(code).data(map).build());
	}

	/**
	 * 알림 수정
	 */
	@ApiOperation(value = "알림 수정", notes = "")
	@PostMapping("/alarm/update")
	@ResponseBody
	public ResponseEntity<ResponseVO> updateAlarm(@RequestBody ApiPushHistDTO apiPushHistDTO) {
		ResponseCode code = ResponseCode.SUCCESS;

		/** 필수값 체크 */
		String pushHistSeq = apiPushHistDTO.getPushHistSeq();

		if (StringUtils.isBlank(pushHistSeq)) {
			code = ResponseCode.INVALID_PARAMETER;
			return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(code).build());
		}

		try {
			apiAlarmService.updateAlarm(apiPushHistDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(code).build());
	}

	/**
	 * 후보자 알림설정 조회
	 */
	@ApiOperation(value = "후보자 알림설정 조회", notes = "")
	@PostMapping("/alarm/setting-ap")
	@ResponseBody
	public ResponseEntity<ResponseVO> selectAlarmSettingAp(@RequestBody ApiApAlertDTO apiApAlertDTO) {
		ResponseCode code = ResponseCode.SUCCESS;

		/** 필수값 체크 */
		String memberId = apiApAlertDTO.getMemberId();
		if (StringUtils.isBlank(memberId)) {
			code = ResponseCode.INVALID_PARAMETER;
			return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(code).build());
		}

		ApiApAlertDTO apSetting = new ApiApAlertDTO();
		apSetting = apiAlarmService.selectAlarmSettingAp(apiApAlertDTO);
		apSetting.setIndustryList(apiAlarmService.selectApIndustryList(apiApAlertDTO)); // 맞춤 채용공고 리스트


		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(apSetting).build());
	}

	/**
	 * 후보자 알림설정 수정
	 */
	@ApiOperation(value = "후보자 알림설정 수정", notes = "")
	@PostMapping("/alarm/setting-ap/update")
	@ResponseBody
	public ResponseEntity<ResponseVO> updateAlarmSettingAp(@RequestBody ApiApAlertDTO apiApAlertDTO) {
		ResponseCode code = ResponseCode.SUCCESS;

		/** 필수값 체크 */
		String memberId = apiApAlertDTO.getMemberId();
		if (StringUtils.isBlank(memberId)) {
			code = ResponseCode.INVALID_PARAMETER;
			return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(code).build());
		}

		try {
			apiAlarmService.updateAlarmSettingAp(apiApAlertDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(code).build());
	}

	/**
	 * 헤드헌터 알림설정 조회
	 */
	@ApiOperation(value = "헤드헌터 알림설정 조회", notes = "")
	@PostMapping("/alarm/setting-hh")
	@ResponseBody
	public ResponseEntity<ResponseVO> selectAlarmSettingHh(@RequestBody ApiApAlertDTO apiApAlertDTO) {
		ResponseCode code = ResponseCode.SUCCESS;

		/** 필수값 체크 */
		String memberId = apiApAlertDTO.getMemberId();
		if (StringUtils.isBlank(memberId)) {
			code = ResponseCode.INVALID_PARAMETER;
			return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(code).build());
		}

		ApiApAlertDTO hhSetting = new ApiApAlertDTO();
		hhSetting = apiAlarmService.selectAlarmSettingHh(apiApAlertDTO);

		String hp2String = hhSetting.getHp2String();	// hp2 String
		if(StringUtils.isNotBlank(hp2String)) {	// hp2 String to Array
			String[] hp2Array = hp2String.split(",");
			hhSetting.setHp2(hp2Array);
		}

		String locString = hhSetting.getLocString();	// loc String
		if(StringUtils.isNotBlank(locString)) { // loc String to Array
			String[] locArray = locString.split(",");
			hhSetting.setLoc(locArray);
		}

		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(hhSetting).build());
	}

	/**
	 * 헤드헌터 알림설정 수정
	 */
	@ApiOperation(value = "헤드헌터 알림설정 수정", notes = "")
	@PostMapping("/alarm/setting-hh/update")
	@ResponseBody
	public ResponseEntity<ResponseVO> updateAlarmSettingHh(@RequestBody ApiApAlertDTO apiApAlertDTO) {
		ResponseCode code = ResponseCode.SUCCESS;

		/** 필수값 체크 */
		String memberId = apiApAlertDTO.getMemberId();
		if (StringUtils.isBlank(memberId)) {
			code = ResponseCode.INVALID_PARAMETER;
			return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(code).build());
		}

		try {
			apiAlarmService.updateAlarmSettingHh(apiApAlertDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(code).build());
	}

}