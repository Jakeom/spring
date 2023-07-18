package com.fw.hh.myap.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fw.core.code.ResponseCode;
import com.fw.core.dto.hh.HhMyapHistoryGroupDTO;
import com.fw.core.dto.hh.HhMyapListDTO;
import com.fw.core.vo.ResponseVO;
import com.fw.hh.myap.service.HhMyApService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HhMyApController {

	private final HhMyApService hhMyApService;

	/**
	 * 고객사 관리
	 */
	@GetMapping("/hh/myap/myap-list")
	public String myApList(ModelMap model, HhMyapListDTO hhMyapListDTO) {

		int totalCount = hhMyApService.selectHhMyapListCount(hhMyapListDTO);
		hhMyapListDTO.setTotalCount(totalCount);
		List<HhMyapListDTO> dataList = hhMyApService.selectHhMyapList(hhMyapListDTO);

		HhMyapHistoryGroupDTO tmp = new HhMyapHistoryGroupDTO();
		tmp.setFrontSession(hhMyapListDTO.getFrontSession());
		model.addAttribute("groupList", hhMyApService.selectHhMyapHistoryGroupList(tmp));
		model.addAttribute("detailList", dataList);
		model.addAttribute("searchInfo", hhMyapListDTO);

		return "hh/myap/myap-list";
	}

	/**
	 * index 내 인재관리 메인
	 */
	@GetMapping("/hh/main/myap/myap-list")
	public String postingMain(HhMyapListDTO hhMyapListDTO, ModelMap model, HttpServletRequest request) {
		model.addAttribute("myApList", hhMyApService.selectMyApListPaging(hhMyapListDTO));
		return "hh/myap/myap-list";
	}

	/**
	 * 헤드헌터 내인재관리 그룹 등록/수정/삭제
	 */
	@PostMapping("/hh/myap/addHistoryGroup")
	@ResponseBody
	public ResponseEntity<ResponseVO> addHistoryGroup(HhMyapHistoryGroupDTO hhMyapHistoryGroupDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			if (hhMyapHistoryGroupDTO.getId() != null && hhMyapHistoryGroupDTO.getId().length() == 0) {
				hhMyapHistoryGroupDTO.setId(null);
			}

			// 삭제일 경우 기본값은 삭제 안됨
			if (hhMyapHistoryGroupDTO.getId() != null && "Y".equals(hhMyapHistoryGroupDTO.getDelFlag())) {

				HhMyapHistoryGroupDTO tmpp = hhMyApService.selectHhMyapHistoryGroupList(hhMyapHistoryGroupDTO).get(0);
				if ("1".equals(tmpp.getIsDefault())) {
					return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(ResponseCode.METHOD_NOT_ALLOWED).build());
				}

				// 삭제되는 그룹이 있다면 기존데이터 그룹을 기본으로 변경
				HhMyapListDTO tmp = new HhMyapListDTO();
				tmp.setFrontSession(hhMyapHistoryGroupDTO.getFrontSession());
				tmp.setGroupId(hhMyapHistoryGroupDTO.getId());
				hhMyApService.updateHhMyapListGorup(tmp);
			}

			// 그룹 등록/수정/삭제 처리함
			hhMyApService.insertMyapgroup(hhMyapHistoryGroupDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}

	/**
	 * 헤드헌터 내인재관리 그룹 이동처리
	 */
	@PostMapping("/hh/myap/moveHistoryGroup")
	@ResponseBody
	public ResponseEntity<ResponseVO> moveHistoryGroup(HhMyapListDTO hhMyapListDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			hhMyapListDTO.setCheckFieldArr(hhMyapListDTO.getCheckedFields().split(","));
			hhMyApService.updateHhMyapListGorup(hhMyapListDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}

	/**
	 * 헤드헌터 내인재관리 공개/비공개
	 */
	@PostMapping("/hh/myap/resumeOpened")
	@ResponseBody
	public ResponseEntity<ResponseVO> resumeOpened(HhMyapListDTO hhMyapListDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			List<HhMyapListDTO> tmpList = hhMyApService.selectHhMyapList(hhMyapListDTO);
			if (tmpList.size() == 0) {
				code = ResponseCode.METHOD_NOT_ALLOWED;
			} else {
				HhMyapListDTO tmp = tmpList.get(0);

				hhMyApService.updateHhMyapListResumeOpend(hhMyapListDTO);

				// 처음으로 공개하는 경우 20000포인트 지급함
				if ("0".equals(tmp.getFirstOpenChanged())) {
					hhMyApService.addExpiredPoint(hhMyapListDTO);
				}
			}
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}

}
