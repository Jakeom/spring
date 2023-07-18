//package com.fw.hh.common.controller;
//
//import java.util.Objects;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.fw.core.code.ResponseCode;
//import com.fw.core.common.service.CommonService;
//import com.fw.core.dto.CommonDTO;
//import com.fw.core.util.CertUtil;
//import com.fw.core.util.MailUtil;
//import com.fw.core.vo.ResponseVO;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@Controller
//@RequiredArgsConstructor
//public class HhCommonController {
//
//	private final CommonService commonService;
//
//	@Value("${cert.server}")
//	private String CERT_SERVER;
//
//	@Value("${cert.callback-url}")
//	private String CERT_CALLBACK_URL;
//
//	/**
//	 * 고등학교 리스트
//	 */
//	@GetMapping("/fo/common/high-school/list")
//	@ResponseBody
//	public ResponseEntity<ResponseVO> getHighSchoolList(CommonDTO commonDTO) {
//		ResponseCode code = ResponseCode.SUCCESS;
//		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(commonService.selectHighSchoolList(commonDTO)).build());
//	}
//
//	/**
//	 * 대학교 리스트
//	 */
//	@GetMapping("/fo/common/university/list")
//	@ResponseBody
//	public ResponseEntity<ResponseVO> getUniversityList(CommonDTO commonDTO) {
//		ResponseCode code = ResponseCode.SUCCESS;
//		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(commonService.selectUniversityList(commonDTO)).build());
//	}
//
//	/**
//	 * 기업정보 리스트
//	 */
//	@GetMapping("/fo/common/company/list")
//	@ResponseBody
//	public ResponseEntity<ResponseVO> getCompanyList(CommonDTO commonDTO) {
//		ResponseCode code = ResponseCode.SUCCESS;
//		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(commonService.selectCompanyList(commonDTO)).build());
//	}
//
//	/**
//	 * 나이스 기업정보 리스트
//	 */
//	@GetMapping("/fo/common/nice-company/list")
//	@ResponseBody
//	public ResponseEntity<ResponseVO> getNiceCompanyList(CommonDTO commonDTO) {
//		ResponseCode code = ResponseCode.SUCCESS;
//		return ResponseEntity.status(code.getHttpStatus())
//				.body(ResponseVO.builder(code).data(commonService.selectNiceCompanySearchList(commonDTO)).build());
//	}
//
//	/**
//	 * 템플릿 리스트
//	 */
//	@GetMapping("/fo/common/template/list/{mediaType}")
//	@ResponseBody
//	public ResponseEntity<ResponseVO> getTemplateList(CommonDTO commonDTO) {
//		ResponseCode code = ResponseCode.SUCCESS;
//		if (!Objects.isNull(commonDTO.getFrontSession())) {
//			commonDTO.setMemberId(commonDTO.getFrontSession().getId());
//		}
//		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(commonService.selectTemplateList(commonDTO)).build());
//	}
//
//	/**
//	 * 인증 키 요청
//	 */
//	@GetMapping("/fo/auth/cert")
//	@ResponseBody
//	public ResponseEntity<ResponseVO> getCert() {
//		ResponseCode code = ResponseCode.SUCCESS;
//		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(CertUtil.getCert()).build());
//	}
//
//	/**
//	 * 인증 키 요청 (모바일 페이지)
//	 */
//	@GetMapping("/fo/auth/cert/mobile")
//	public String getCert(ModelMap model, @RequestParam String mode) {
//		model.addAttribute("certServer", CERT_SERVER); // 본인인증 URL
//		model.addAttribute("certCallbackUrl", CERT_CALLBACK_URL); // 본인인증 CALLBACK URL
//		model.addAttribute("certInfo", CertUtil.getCert());
//		model.addAttribute("mode", mode);
//		return "/fo/auth/cert/mobile";
//	}
//
//	/**
//	 * 인증 키 확인
//	 */
//	@GetMapping("/fo/auth/cert/callback")
//	public String getCertCallback(HttpServletRequest request, ModelMap model) {
//		String recCert = request.getParameter("rec_cert");
//		String certNum = request.getParameter("certNum");
//		String mode = request.getParameter("mode");
//		model.addAttribute("certInfo", CertUtil.getCertResult(recCert, certNum));
//		model.addAttribute("mode", mode);
//		return "fo/auth/cert/callback";
//	}
//
//	/**
//	 * ADD Email Template
//	 */
//	@GetMapping("/fo/auth/email/template")
//	public void addTemplate() {
//		String content = "<!DOCTYPE html>\n" + "<html lang=\"ko\">\n" + "<head>\n" + "    <meta charset=\"UTF-8\"/>\n"
//				+ "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"/>\n"
//				+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>\n" + "    <title>Document</title>\n" + "</head>\n"
//				+ "<body style=\"margin: 0; background: #eeeeee\">\n"
//				+ "<table style=\"font-family: '나눔고딕', 돋움, dotum, arial; font-size: 14px; color: #000; width: 800px; border: 0; table-layout: fixed; border-collapse: collapse; border-spacing: 0; background: #ffffff\">\n"
//				+ "    <!-- header -->\n" + "    <tbody>\n" + "    <tr>\n" + "        <td style=\"padding: 0\">\n"
//				+ "            <table style=\"table-layout: fixed; border-collapse: collapse; border-spacing: 0; border: 0; width: 100%\">\n"
//				+ "                <colgroup>\n" + "                    <col style=\"width: 40px\"/>\n" + "                    <col/>\n"
//				+ "                    <col style=\"width: 40px\"/>\n" + "                </colgroup>\n" + "                <tbody>\n"
//				+ "                <tr>\n" + "                    <td style=\"padding: 0\">&nbsp;</td>\n"
//				+ "                    <td style=\"padding: 0\">\n"
//				+ "                        <table style=\"table-layout: fixed; border-collapse: collapse; border-spacing: 0; border: 0; width: 100%; border-bottom: 4px solid #000\">\n"
//				+ "                            <tbody>\n" + "                            <tr>\n"
//				+ "                                <td style=\"height: 20px; padding: 0\"></td>\n" + "                            </tr>\n"
//				+ "                            <tr>\n"
//				+ "                                <td style=\"padding: 0\"><img src=\"https://www.resume9.co.kr/nuxtfiles/img/n_logo_03.f09a7d2.png\" alt=\"Resume9\" style=\"border: 0; vertical-align: top\"/></td>\n"
//				+ "                            </tr>\n" + "                            <tr>\n"
//				+ "                                <td style=\"height: 20px; padding: 0\"></td>\n" + "                            </tr>\n"
//				+ "                            </tbody>\n" + "                        </table>\n" + "                    </td>\n"
//				+ "                    <td style=\"padding: 0\">&nbsp;</td>\n" + "                </tr>\n" + "                </tbody>\n"
//				+ "            </table>\n" + "        </td>\n" + "    </tr>\n" + "    <!-- //header -->\n" + "    <!-- container -->\n" + "    <tr>\n"
//				+ "        <td style=\"padding: 0\">\n"
//				+ "            <table style=\"table-layout: fixed; border-collapse: collapse; border-spacing: 0; width: 100%; border: 0\">\n"
//				+ "                <colgroup>\n" + "                    <col style=\"width: 40px\"/>\n" + "                    <col/>\n"
//				+ "                    <col style=\"width: 40px\"/>\n" + "                </colgroup>\n" + "                <tbody>\n"
//				+ "                <tr>\n" + "                    <td style=\"padding: 0\">&nbsp;</td>\n" + "                    <td>\n"
//				+ "                        <table style=\"table-layout: fixed; border-collapse: collapse; border-spacing: 0; border: 0; width: 100%; letter-spacing: -0.9px\">\n"
//				+ "                            <tbody>\n" + "                            <tr>\n"
//				+ "                                <td style=\"height: 45px; padding: 0\"></td>\n" + "                            </tr>\n"
//				+ "                            <tr>\n"
//				+ "                                <td style=\"font-size: 34px; font-weight: 700; color: #000000; letter-spacing: -1.8px\">\n"
//				+ "                                    이메일 인증번호\n" + "                                </td>\n" + "                            </tr>\n"
//				+ "                            <tr>\n" + "                                <td style=\"height: 20px; padding: 0\"></td>\n"
//				+ "                            </tr>\n" + "                            <tr>\n"
//				+ "                                <td style=\"font-size: 18px; line-height: 1.6; color: #666\">\n"
//				+ "                                    취업의 시작 경력의 완성, 레쥬메나인에 가입하신 것을 환영합니다. <br/>\n"
//				+ "                                    아래의 인증번호를 입력하시면 가입이 정상적으로 완료됩니다.\n"
//				+ "                                    <div style=\"margin-top: 40px\">\n"
//				+ "                                        <div style=\"display: inline-block; width: 220px; vertical-align: top\">\n"
//				+ "                                            <table style=\"table-layout: fixed; width: 100%; background: #f8f8fa; border-spacing: 0\">\n"
//				+ "                                                <tbody>\n" + "                                                <tr>\n"
//				+ "                                                    <td style=\"height: 48px; padding: 0 10px; font-size: 22px; color: #000; letter-spacing: 3px; font-weight: bold\">\n"
//				+ "                                                        {{certCode}}\n"
//				+ "                                                    </td>\n" + "                                                </tr>\n"
//				+ "                                                </tbody>\n" + "                                            </table>\n"
//				+ "                                        </div>\n" + "                                    </div>\n"
//				+ "                                </td>\n" + "                            </tr>\n" + "                            <tr>\n"
//				+ "                                <td style=\"height: 80px; padding: 0\"></td>\n" + "                            </tr>\n"
//				+ "                            </tbody>\n" + "                        </table>\n" + "                    </td>\n"
//				+ "                    <td style=\"padding: 0\">&nbsp;</td>\n" + "                </tr>\n" + "                </tbody>\n"
//				+ "            </table>\n" + "        </td>\n" + "    </tr>\n" + "    <!-- //container -->\n" + "    <!-- footer -->\n" + "    <tr>\n"
//				+ "        <td style=\"padding: 0\">\n"
//				+ "            <table style=\"table-layout: fixed; border-collapse: collapse; border-spacing: 0; border: 0; background: #f8f8f8; width: 100%; border-top: 1px solid #dcdcdc\">\n"
//				+ "                <tbody>\n" + "                <tr>\n" + "                    <td style=\"height: 30px\"></td>\n"
//				+ "                </tr>\n" + "                <tr>\n"
//				+ "                    <td style=\"font-size: 13px; color: #999999; padding-left: 40px; padding-right: 40px\">본 메일은 발송전용이며,\n"
//				+ "                        문의에 대한 회신은 처리되지 않습니다.\n" + "                    </td>\n" + "                </tr>\n"
//				+ "                <tr>\n" + "                    <td style=\"height: 20px\"></td>\n" + "                </tr>\n"
//				+ "                <tr>\n"
//				+ "                    <td style=\"font-size: 14px; color: #666666; padding-left: 40px; padding-right: 40px\">레쥬메나인(주) |\n"
//				+ "                        사업자등록번호 : 543-81-01709 | 직업정보제공사업:J1200020200017 | 통신판매업 : 제 2021-서울강남-02745호\n"
//				+ "                    </td>\n" + "                </tr>\n" + "                <tr>\n"
//				+ "                    <td style=\"height: 6px\"></td>\n" + "                </tr>\n" + "                <tr>\n"
//				+ "                    <td style=\"font-size: 12px; color: #999999; padding-left: 40px; padding-right: 40px\">Copyright ⓒ\n"
//				+ "                        Resume9. All rights reserved.\n" + "                    </td>\n" + "                </tr>\n"
//				+ "                <tr>\n" + "                    <td style=\"height: 30px\"></td>\n" + "                </tr>\n"
//				+ "                </tbody>\n" + "            </table>\n" + "        </td>\n" + "    </tr>\n" + "    <!-- //footer -->\n"
//				+ "    </tbody>\n" + "</table>\n" + "</body>\n" + "</html>\n";
//
//		String subject = "[RESUME9] 인증코드 확인";
//		String template = "certCode";
//		MailUtil.createTemplate(subject, template, null, content);
//	}
//
//}