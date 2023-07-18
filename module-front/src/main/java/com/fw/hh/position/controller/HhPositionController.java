package com.fw.hh.position.controller;

import com.fw.core.code.ResponseCode;
import com.fw.core.dto.fo.FoMemberDTO;
import com.fw.core.dto.hh.HhCompanyDTO;
import com.fw.core.dto.hh.HhMyProfileDTO;
import com.fw.core.dto.hh.HhPositionDTO;
import com.fw.core.vo.ResponseVO;
import com.fw.hh.mypage.service.HhMyProfileService;
import com.fw.hh.position.service.HhPositionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HhPositionController {

	private final HhPositionService hhPositionService;
	private final HhMyProfileService hhMyProfileService;

	/**
	 * 포지션 관리 페이지
	 */
	@GetMapping("/hh/position/position-list")
	public String positionList(ModelMap model, HhPositionDTO hhPositionDTO) {
		// 진행중 공고
		hhPositionDTO.setStatusList(Arrays.asList("DOING"));
		model.addAttribute("doingList", hhPositionService.selectHhPositionList(hhPositionDTO));

		// 마감된 공고
		hhPositionDTO.setStatusList(Arrays.asList("CLOSE", "END"));
		model.addAttribute("endList", hhPositionService.selectHhPositionList(hhPositionDTO));

		model.addAttribute("searchInfo", hhPositionDTO);
		return "hh/position/position-list";
	}

	/**
	 * 포지션 상세 페이지
	 */
	@GetMapping("/hh/position/position-detail")
	public String positionDetail(ModelMap model, HhPositionDTO hhPositionDTO) {
		model.addAttribute("detail", hhPositionService.selectPosition(hhPositionDTO));

		// 컨택 리스트
		hhPositionDTO.setMode("CONTACT");
		model.addAttribute("contactList", hhPositionService.selectPositionApplicant(hhPositionDTO));

		// 전형 리스트
		hhPositionDTO.setMode("PROCESS");
		model.addAttribute("processList", hhPositionService.selectPositionApplicant(hhPositionDTO));

		HhMyProfileDTO HhMyProfileDTO = new HhMyProfileDTO();
        HhMyProfileDTO.setFrontSession(hhPositionDTO.getFrontSession());
		model.addAttribute("profile", hhMyProfileService.selectMyProfileInfo(HhMyProfileDTO));

		model.addAttribute("coworkerList", hhPositionService.selectPositionCoworkerList(hhPositionDTO));
		model.addAttribute("searchInfo", hhPositionDTO);
		return "hh/position/position-detail";
	}

	/**
	 * 포지션 등록 페이지
	 */
	@GetMapping("/hh/position/position-register")
	public String positionRegister(ModelMap model) {
		return "hh/position/position-register";
	}

	/**
	 * 포지션 등록
	 */
	@PostMapping(value = "/hh/position/position-register", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
	@ResponseBody
	public ResponseEntity<ResponseVO> positionRegister(@RequestPart(value = "jsonData") HhPositionDTO hhPositionDTO,
			@RequestPart(value = "licenceFile", required = false) MultipartFile[] licenceFile,
			@RequestPart(value = "companyInfoFiles", required = false) MultipartFile[] companyInfoFiles) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			hhPositionDTO.setLicenceFile(licenceFile);
			hhPositionDTO.setCompanyInfoFiles(companyInfoFiles);
			hhPositionService.insertPosition(hhPositionDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(hhPositionDTO).build());
	}

	/**
	 * 기등록 기업 조회
	 */
	@GetMapping(value = "/hh/position/reg-company-list")
	@ResponseBody
	public ResponseEntity<ResponseVO> regCompanyList(HhCompanyDTO hhCompanyDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		return ResponseEntity.status(code.getHttpStatus())
				.body(ResponseVO.builder(code).data(hhPositionService.selectHrCompanyList(hhCompanyDTO)).build());
	}

	/**
	 * 기등록 포지션 조회
	 */
	@GetMapping(value = "/hh/position/reg-position-list")
	@ResponseBody
	public ResponseEntity<ResponseVO> regPositionList(HhPositionDTO hhPositionDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		return ResponseEntity.status(code.getHttpStatus())
				.body(ResponseVO.builder(code).data(hhPositionService.selectRegPositionList(hhPositionDTO)).build());
	}

	/**
	 * We펌 코워커 리스트 조회
	 */
	@GetMapping(value = "/hh/position/coworker-list")
	@ResponseBody
	public ResponseEntity<ResponseVO> coworkerList(HhPositionDTO hhPositionDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		return ResponseEntity.status(code.getHttpStatus())
				.body(ResponseVO.builder(code).data(hhPositionService.selectWeFirmCoworkerList(hhPositionDTO)).build());
	}

	/**
	 * 포지션 연장
	 */
	@PostMapping(value = "/hh/position/deadline")
	@ResponseBody
	public ResponseEntity<ResponseVO> saveDeadline(HhPositionDTO hhPositionDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			hhPositionService.updatePositionDeadline(hhPositionDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}

	/**
	 * 포지션 삭제
	 */
	@PostMapping(value = "/hh/position/delete")
	@ResponseBody
	public ResponseEntity<ResponseVO> deletePosition(HhPositionDTO hhPositionDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			hhPositionService.updatePositionDelete(hhPositionDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}

	/**
	 * 포지션(채용공고) 채용현황 조회 (headhunter)
	 */
	@GetMapping("hh/position/selectPositionStatus")
	public String selectPositionStatus(ModelMap model, HhPositionDTO hhPositionDTO) {
		model.addAttribute("status", hhPositionService.selectPositionStatus(hhPositionDTO));

		return "hh/position/position-detail";
	}

	/**
	 * 컨택리스트 수동 등록
	 */
	@PostMapping(value = "/hh/position/contact-manual")
	@ResponseBody
	public ResponseEntity<ResponseVO> saveDeadline(@RequestBody FoMemberDTO foMemberDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		String serviceCode = "";
		try {
			serviceCode = hhPositionService.insertContactList(foMemberDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).serviceCode(serviceCode).build());
	}

	/**
	 * 포지션 종료
	 */
	@PostMapping(value = "/hh/position/position-end")
	@ResponseBody
	public ResponseEntity<ResponseVO> positionEnd(@RequestBody HhPositionDTO hhPositionDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			hhPositionService.updatePositionEnd(hhPositionDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}

	/**
	 * 코웤 종료
	 */
	@PostMapping(value = "/hh/position/coworker-end")
	@ResponseBody
	public ResponseEntity<ResponseVO> coworkerEnd(@RequestBody HhPositionDTO hhPositionDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			hhPositionDTO.setMemberId(hhPositionDTO.getFrontSession().getId());
			hhPositionService.updateCoworkerEnd(hhPositionDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}

	/**
	 * 컨택리스트 삭제
	 */
	@PostMapping(value = "/hh/position/delete-contact-list")
	@ResponseBody
	public ResponseEntity<ResponseVO> deleteContactList(@RequestBody HhPositionDTO hhPositionDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			hhPositionService.deleteContactList(hhPositionDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}

	/**
	 * 컨택리스트 부적합 처리
	 */
	@PostMapping(value = "/hh/position/next-progress")
	@ResponseBody
	public ResponseEntity<ResponseVO> nextProgress(@RequestBody HhPositionDTO hhPositionDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			if (StringUtils.equals("PASS", hhPositionDTO.getProgress())) {
				hhPositionDTO.setProgressStep("REGISTER");
				hhPositionDTO.setProcessStatus("DOCUMENT_REVIEW");
				hhPositionService.updateProgressStep(hhPositionDTO);
				hhPositionService.updatePositionApplicant(hhPositionDTO);
			} else if (StringUtils.equals("FAIL", hhPositionDTO.getProgress())) {
				hhPositionDTO.setProcessStatus("FAIL");
				hhPositionService.updatePositionApplicant(hhPositionDTO);
			}
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}

	/**
	 * 컨택리스트 - 문자 발송
	 */
	@PostMapping(value = "/hh/position/send-sms")
	@ResponseBody
	public ResponseEntity<ResponseVO> sendSms(@RequestBody HhPositionDTO hhPositionDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		String serviceCode = "";
		try {
			serviceCode = hhPositionService.updateSendSms(hhPositionDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).serviceCode(serviceCode).build());
	}

	/**
	 * 컨택리스트 - 실패 문자 발송
	 */
	@PostMapping(value = "/hh/position/send-fail-sms")
	@ResponseBody
	public ResponseEntity<ResponseVO> sendFailSms(@RequestBody HhPositionDTO hhPositionDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			hhPositionService.updateSendFailSms(hhPositionDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}

	/**
	 * 컨택리스트 - 포지션 제안 메일 발송
	 */
	@PostMapping(value = "/hh/position/send-mail")
	@ResponseBody
	public ResponseEntity<ResponseVO> sendMail(@RequestBody HhPositionDTO hhPositionDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			hhPositionService.updateSendMail(hhPositionDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}

	/**
	 * 접수 - 연락처 공개
	 */
	@PostMapping(value = "/hh/position/open-info")
	@ResponseBody
	public ResponseEntity<ResponseVO> openInfo(@RequestBody HhPositionDTO hhPositionDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			hhPositionService.updateOpenInfo(hhPositionDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}

	/**
	 * 접수 - 고객사 추천 메일 발송
	 */
	@PostMapping(value = "/hh/position/company-recommend")
	@ResponseBody
	public ResponseEntity<ResponseVO> companyRecommend(@RequestBody HhPositionDTO hhPositionDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			hhPositionService.updateCompanyRecommend(hhPositionDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}

	/**
	 * 접수 - (CO) PM 접수
	 */
	@PostMapping(value = "/hh/position/pm-submit")
	@ResponseBody
	public ResponseEntity<ResponseVO> pmSubmit(@RequestBody HhPositionDTO hhPositionDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			hhPositionService.updatePmSubmit(hhPositionDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}

	/**
	 * 관심인재 추가 > 포지션 추천
	 */
	@PostMapping(value = "/hh/position/recommend")
	@ResponseBody
	public ResponseEntity<ResponseVO> positionRecommend(@RequestBody HhPositionDTO hhPositionDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			hhPositionService.insertPositionRecommend(hhPositionDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}

	/**
	 * 포지션 추천 중복여부 확인
	 */
	@PostMapping("/hh/position/recommend-duplicate")
	@ResponseBody
	public ResponseEntity<ResponseVO> recommendDuplicate(@RequestBody HhPositionDTO hhPositionDTO) {
		log.info(hhPositionDTO.getApMemberId());
		ResponseCode code = ResponseCode.SUCCESS;
		String serviceCode = "NOT_DUPLICATE";
		try {
			boolean duplicate = hhPositionService.selectRecommendDuplicate(hhPositionDTO);
			if (duplicate) {
				serviceCode = "DUPLICATE";
			}
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).serviceCode(serviceCode).build());
	}

	/**
	 * 추천용 이력서 등록
	 */
	@PostMapping("/hh/position/upload-file")
	@ResponseBody
	public ResponseEntity<ResponseVO> uploadFile(@RequestPart(value = "jsonData") HhPositionDTO hhPositionDTO,
			@RequestPart(value = "files", required = false) MultipartFile[] files) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			hhPositionDTO.setFiles(files);
			hhPositionService.uploadFiles(hhPositionDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}

	/**
	 * C
	 */
	@PostMapping("/hh/position/final-pass")
	@ResponseBody
	public ResponseEntity<ResponseVO> finalPass(@RequestBody HhPositionDTO hhPositionDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			hhPositionService.updateFinalPass(hhPositionDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}

	/**
	 * Co-work 참여하기
	 */
	@PostMapping("/hh/position/cowork-join")
	@ResponseBody
	public ResponseEntity<ResponseVO> coworkJoin(@RequestBody HhPositionDTO hhPositionDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			hhPositionService.insertCoworkJoin(hhPositionDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}

	/**
	 * 이력서 보기 클릭시 열람내역 insert
	 */
	@PostMapping("/hh/position/updateShowHist")
	@ResponseBody
	public ResponseEntity<ResponseVO> updateShowHist(@RequestBody HhPositionDTO hhPositionDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			hhPositionService.updateShowHist(hhPositionDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}

	/**
	 * 종료 포지션 남은인원 탈락처리
	 */
	@PostMapping("/hh/position/remain-fail")
	@ResponseBody
	public ResponseEntity<ResponseVO> updateRemainFail(HhPositionDTO hhPositionDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			hhPositionService.updateRemainFail(hhPositionDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}
}