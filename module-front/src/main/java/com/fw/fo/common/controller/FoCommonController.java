package com.fw.fo.common.controller;

import com.fw.core.code.ResponseCode;
import com.fw.core.common.service.CommonService;
import com.fw.core.dto.CommonDTO;
import com.fw.core.util.CertUtil;
import com.fw.core.util.ISASUtil;
import com.fw.core.util.MailUtil;
import com.fw.core.vo.ResponseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.Objects;

@Slf4j
@Controller
@RequiredArgsConstructor
public class FoCommonController {

    private final CommonService commonService;

    @Value("${cert.server}")
    private String CERT_SERVER;

    @Value("${cert.callback-url}")
    private String CERT_CALLBACK_URL;

    /**
     * Coocon Encode
     */
    @PostMapping("/fo/common/encode")
    @ResponseBody
    public ResponseEntity<ResponseVO> encode(HttpServletRequest request) throws Exception {
        ResponseCode code = ResponseCode.SUCCESS;
        String inData = request.getParameter("data");
        log.info("{}", inData);
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(ISASUtil.encode(inData)).build());
    }

    /**
     * Coocon Decode
     */
    @PostMapping("/fo/common/decode")
    @ResponseBody
    public ResponseEntity<ResponseVO> decode(HttpServletRequest request) throws Exception {
        ResponseCode code = ResponseCode.SUCCESS;
        String inData = request.getParameter("data");
        log.info("{}", inData);
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(ISASUtil.decode(inData)).build());
    }

    /**
     * 고등학교 리스트
     */
    @GetMapping("/fo/common/high-school/list")
    @ResponseBody
    public ResponseEntity<ResponseVO> getHighSchoolList(CommonDTO commonDTO){
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(commonService.selectHighSchoolList(commonDTO)).build());
    }

    /**
     * 고등학교 리스트 - RPA
     */
    @GetMapping("/fo/common/high-school/list/rpa")
    @ResponseBody
    public ResponseEntity<ResponseVO> getHighSchoolListBot(CommonDTO commonDTO){
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(commonService.selectHighSchoolListRpa(commonDTO)).build());
    }

    /**
     * 대학교 리스트
     */
    @GetMapping("/fo/common/university/list")
    @ResponseBody
    public ResponseEntity<ResponseVO> getUniversityList(CommonDTO commonDTO){
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(commonService.selectUniversityList(commonDTO)).build());
    }

    /**
     * 대학교 리스트 - RPA
     */
    @GetMapping("/fo/common/university/list/rpa")
    @ResponseBody
    public ResponseEntity<ResponseVO> getUniversityListRpa(CommonDTO commonDTO){
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(commonService.selectUniversityListRpa(commonDTO)).build());
    }

    /**
     * 기업정보 리스트
     */
    @GetMapping("/fo/common/company/list")
    @ResponseBody
    public ResponseEntity<ResponseVO> getCompanyList(CommonDTO commonDTO){
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(commonService.selectCompanyList(commonDTO)).build());
    }

    /**
     * 나이스 기업정보 리스트
     */
    @GetMapping("/fo/common/nice-company/list")
    @ResponseBody
    public ResponseEntity<ResponseVO> getNiceCompanyList(CommonDTO commonDTO){
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(commonService.selectNiceCompanySearchList(commonDTO)).build());
    }

    /**
     * 템플릿 리스트
     */
    @GetMapping("/fo/common/template/list/{mediaType}")
    @ResponseBody
    public ResponseEntity<ResponseVO> getTemplateList(CommonDTO commonDTO){
        ResponseCode code = ResponseCode.SUCCESS;
        if(!Objects.isNull(commonDTO.getFrontSession())){
            commonDTO.setMemberId(commonDTO.getFrontSession().getId());
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(commonService.selectTemplateList(commonDTO)).build());
    }

    /**
     * 인증 키 요청
     */
    @GetMapping("/fo/auth/cert")
    @ResponseBody
    public ResponseEntity<ResponseVO> getCert() {
        ResponseCode code = ResponseCode.SUCCESS;
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(CertUtil.getCert()).build());
    }

    /**
     * 인증 키 요청 (모바일 페이지)
     */
    @GetMapping("/fo/auth/cert/mobile")
    public String getCert(ModelMap model, @RequestParam String mode) {
        model.addAttribute("certServer", CERT_SERVER);                       // 본인인증 URL
        model.addAttribute("certCallbackUrl", CERT_CALLBACK_URL);            // 본인인증 CALLBACK URL
        model.addAttribute("certInfo", CertUtil.getCert());
        model.addAttribute("mode", mode);
        return "/fo/auth/cert/mobile";
    }

    /**
     * 인증 키 확인
     */
    @GetMapping("/fo/auth/cert/callback")
    public String getCertCallback(HttpServletRequest request, ModelMap model) {
        String recCert = request.getParameter("rec_cert");
        String certNum = request.getParameter("certNum");
        String mode = request.getParameter("mode");
        model.addAttribute("certInfo", CertUtil.getCertResult(recCert, certNum));
        model.addAttribute("mode", mode);
        return "fo/auth/cert/callback";
    }

    /**
     * 맞춤법 검사
     */
    @PostMapping("/fo/auth/spell-check")
    @ResponseBody
    public String spellCheck(String content){
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("https://m.search.naver.com/p/csearch/ocontent/spellchecker.nhn");
        httpGet.setHeader(org.apache.http.HttpHeaders.REFERER, "https://search.naver.com/");
        httpGet.setHeader(HttpHeaders.USER_AGENT,"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36");
        String responseString = null;
        try {
            URI uri = new URIBuilder(httpGet.getURI())
                    .addParameter("_callback", "window.__jindo2_callback._spellingCheck_0")
                    .addParameter("q", content)
                    .build();
            httpGet.setURI(uri);
            HttpResponse response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            responseString = EntityUtils.toString(entity, "UTF-8");
            log.info(">>> {}", responseString);
        } catch (Exception e) {
            log.error("error", e);
        }
        return responseString.substring(42, responseString.length()-2);
    }

    //https://www.resume9.co.kr/nuxtfiles/img/n_logo_03.f09a7d2.png
    //http://54.180.223.180:8079/fo/user/application/job-position
    /**
     * ADD Email Template
     */
    @GetMapping("/fo/auth/email/template")
    public void addTemplate(){
        String content =    "<!DOCTYPE html>\n" +
                "           <html lang=\"ko\">\n" +
                "               <head>\n" +
                "                   <meta charset=\"UTF-8\" />\n" +
                "                   <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\n" +
                "                   <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n" +
                "                   <title>Document</title>\n" +
                "                   </head>\n" +
                "               <body style=\"margin: 0; background: #eeeeee\">\n" +
                "                   <table style=\"font-family: '나눔고딕', 돋움, dotum, arial; font-size: 14px; color: #000; width: 800px; border: 0; table-layout: fixed; border-collapse: collapse; border-spacing: 0; background: #ffffff\">\n" +
                "                       <!-- header -->\n" +
                "                       <tbody>\n" +
                "                          <tr>\n" +
                "                            <td style=\"padding: 0\">\n" +
                "                            <table style=\"table-layout: fixed; border-collapse: collapse; border-spacing: 0; border: 0; width: 100%\">\n" +
                "                            <colgroup>\n" +
                "                            <col style=\"width: 40px\" />\n" +
                "                            <col />\n" +
                "                            <col style=\"width: 40px\" />\n" +
                "                            </colgroup>\n" +
                "                            <tbody>\n" +
                "                         <tr>\n" +
                "                           <td style=\"padding: 0\">&nbsp;</td>\n" +
                "                           <td style=\"padding: 0\">\n" +
                "                           <table style=\"table-layout: fixed; border-collapse: collapse; border-spacing: 0; border: 0; width: 100%; border-bottom: 4px solid #000\">\n" +
                "                       <tbody>\n" +
                "                         <tr>\n" +
                "                           <td style=\"height: 20px; padding: 0\"></td>\n" +
                "                         </tr>\n" +
                "                         <tr>\n" +
                "                           <td style=\"padding: 0\"><img src=\"{{logoImagePath}}\" alt=\"Resume9\" style=\"border: 0; vertical-align: top\" /></td>\n" +
                "                         </tr>\n" +
                "                         <tr>\n" +
                "                           <td style=\"height: 20px; padding: 0\"></td>\n" +
                "                         </tr>\n" +
                "                       </tbody>\n" +
                "                   </table>\n" +
                "                   </td>\n" +
                "                   <td style=\"padding: 0\">&nbsp;</td>\n" +
                "                   </tr>\n" +
                "                   </tbody>\n" +
                "                   </table>\n" +
                "                   </td>\n" +
                "                   </tr>\n" +
                "                   <!-- //header -->\n" +
                "                   <!-- container -->\n" +
                "                   <tr>\n" +
                "                   <td style=\"padding: 0\">\n" +
                "                   <table style=\"table-layout: fixed; border-collapse: collapse; border-spacing: 0; width: 100%; border: 0\">\n" +
                "                   <colgroup>\n" +
                "                   <col style=\"width: 40px\" />\n" +
                "                   <col />\n" +
                "                   <col style=\"width: 40px\" />\n" +
                "                   </colgroup>\n" +
                "                   <tbody>\n" +
                "                   <tr>\n" +
                "                   <td style=\"padding: 0\">&nbsp;</td>\n" +
                "                   <td>\n" +
                "                   <table style=\"table-layout: fixed; border-collapse: collapse; border-spacing: 0; border: 0; width: 100%; letter-spacing: -0.9px\">\n" +
                "                   <tbody>\n" +
                "                   <tr>\n" +
                "                   <td style=\"height: 45px; padding: 0\"></td>\n" +
                "                   </tr>\n" +
                "                   <tr>\n" +
                "                   <td style=\"font-size: 34px; font-weight: 700; color: #000000; letter-spacing: -1.8px\">\n" +
                "                   이력서를 <span style=\"color: #1686f2; font-family: '맑은고딕', Malgun Gothic, '돋움', Dotum, Helvetica, 'Apple SD Gothic Neo', Sans-serif\">RESUME</span><span style=\"color: #f95b55\">9</span>에 등록했습니다.\n" +
                "                   </td>\n" +
                "                   </tr>\n" +
                "                   <tr>\n" +
                "                   <td style=\"height: 20px; padding: 0\"></td>\n" +
                "                   </tr>\n" +
                "                   <tr>\n" +
                "                   <td style=\"font-size: 14px; line-height: 1.6; color: #666; font-family: '맑은고딕', Malgun Gothic, '돋움', Dotum, Helvetica, 'Apple SD Gothic Neo', Sans-serif\">\n" +
                "                   <span style=\"color: #000\">{{apName}}</span>님 안녕하세요. <br />\n" +
                "                   취업의 시작, 경력의 완성. 헤드헌터 전용 플랫폼 RESUME9 입니다.\n" +
                "                   </td>\n" +
                "                   </tr>\n" +
                "                   <tr>\n" +
                "                   <td style=\"padding: 10px\"></td>\n" +
                "                   </tr>\n" +
                "                   <tr>\n" +
                "                   <td style=\"font-size: 14px; line-height: 1.6; color: #666; font-family: '맑은고딕', Malgun Gothic, '돋움', Dotum, Helvetica, 'Apple SD Gothic Neo', Sans-serif\">\n" +
                "                   헤드헌터 <span style=\"color: #000\">{{hhName}}</span> 님이 다음과 같이 <span style=\"color: #000\">{{apName}}</span>님의 이력서를 RESUME9에 등록하여 <br />\n" +
                "                   확인 및 비밀번호 설정에 대해 안내드립니다.\n" +
                "                   </td>\n" +
                "                   </tr>\n" +
                "                   <tr>\n" +
                "                   <td>\n" +
                "                   <div style=\"margin-top: 40px\">\n" +
                "                   <div style=\"display: inline-block; width: 100%; vertical-align: top\">\n" +
                "                   <table style=\"table-layout: fixed; width: 100%; background: #f8f8fa; border-spacing: 0; padding: 30px\">\n" +
                "                   <tbody>\n" +
                "                   <tr>\n" +
                "                   <td style=\"color: #000; line-height: 22px; font-family: '맑은고딕', Malgun Gothic, '돋움', Dotum, Helvetica, 'Apple SD Gothic Neo', Sans-serif\">[ 등록된 정보 ]</td>\n" +
                "                   </tr>\n" +
                "                   <tr>\n" +
                "                   <td style=\"padding: 10px\"></td>\n" +
                "                   </tr>\n" +
                "                   <tr>\n" +
                "                   <td style=\"color: #666; line-height: 22px; font-family: '맑은고딕', Malgun Gothic, '돋움', Dotum, Helvetica, 'Apple SD Gothic Neo', Sans-serif\"><span style=\"color: #000\">{{apName}} ({{apEmail}})</span>님의 이력서 및 RESUME9 서면 가입/등록 동의서</td>\n" +
                "                   </tr>\n" +
                "                   <tr>\n" +
                "                   <td style=\"padding: 10px\"></td>\n" +
                "                   </tr>\n" +
                "                   <tr>\n" +
                "                   <td style=\"color: #666; line-height: 22px; font-family: '맑은고딕', Malgun Gothic, '돋움', Dotum, Helvetica, 'Apple SD Gothic Neo', Sans-serif\"><span style=\"color: #000\">등록자 : {{hhName}}</span> (RESUME9 인증 헤드헌터)</td>\n" +
                "                   </tr>\n" +
                "                   <tr>\n" +
                "                   <td style=\"color: #666; line-height: 22px; font-family: '맑은고딕', Malgun Gothic, '돋움', Dotum, Helvetica, 'Apple SD Gothic Neo', Sans-serif\"><span style=\"color: #000\">등록일시 :</span> 2022년 12월 08일 17:53</td>\n" +
                "                   </tr>\n" +
                "                   </tbody>\n" +
                "                   </table>\n" +
                "                   </div>\n" +
                "                   </div>\n" +
                "                   </td>\n" +
                "                   </tr>\n" +
                "                   <tr>\n" +
                "                   <td align=\"center\" style=\"padding: 38px 0 0 0; font-family: '맑은고딕', Malgun Gothic, '돋움', Dotum, Helvetica, 'Apple SD Gothic Neo', Sans-serif; line-height: 24px; font-size: 14px; color: #424242\">\n" +
                "                   다음 링크를 통해 비밀번호를 설정하시면,\n" +
                "                   <br />현재 등록된 내용과 담당 헤드헌터 정보, 실시간 채용 진행상황 등을 확인하실 수 있습니다.\n" +
                "                   </td>\n" +
                "                   </tr>\n" +
                "                   <tr>\n" +
                "                       <td align=\"center\" style=\"padding: 20px 0 0 0; font-family: '맑은고딕', Malgun Gothic, '돋움', Dotum, Helvetica, 'Apple SD Gothic Neo', Sans-serif\">\n" +
                "                       <a href=\"{{signupUrl}}\"\n" +
                "                       style=\"display: block; line-height: 45px; text-align: center; background-color: #0a4571; text-decoration: none; font-size: 15px; color: #ffffff\"\n" +
                "                       rel=\"noreferrer noopener\"\n" +
                "                       target=\"_blank\"\n" +
                "                       >\n" +
                "                       비밀번호설정하기\n" +
                "                       </a>\n" +
                "                       </td>\n" +
                "                   </tr>\n" +
                "                    <tr>\n" +
                "                       <td bgcolor=\"#ffffff\">\n" +
                "                       <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
                "                       <tbody>\n" +
                "                           <tr>\n" +
                "                               <td align=\"center\" style=\"padding: 94px 0 0 0\">\n" +
                "                                   <img src=\"{{mailImage1}}\" width=\"641\" height=\"42\" style=\"display: block\" loading=\"lazy\" />\n" +
                "                               </td>\n" +
                "                           </tr>\n" +
                "                           <tr>\n" +
                "                               <td align=\"center\" style=\"padding: 67px 0 0 0\">\n" +
                "                                   <img src=\"{{mailImage2}}\" width=\"641\" height=\"97\" style=\"display: block\" loading=\"lazy\" />\n" +
                "                               </td>\n" +
                "                           </tr>\n" +
                "                           <tr>\n" +
                "                               <td align=\"center\" style=\"padding: 45px 0 0 0\">\n" +
                "                                   <img src=\"{{mailImage3}}\" width=\"641\" height=\"190\" style=\"display: block\" loading=\"lazy\" />\n" +
                "                               </td>\n" +
                "                           </tr>\n" +
                "                           <tr>\n" +
                "                               <td align=\"center\" style=\"padding: 64px 0 0 0\">\n" +
                "                                   <img src=\"{{mailImage4}}\" width=\"641\" height=\"43\" style=\"display: block\" loading=\"lazy\" />\n" +
                "                               </td>\n" +
                "                           </tr>\n" +
                "                           <tr>\n" +
                "                               <td align=\"center\" style=\"padding: 70px 0 0 0\">\n" +
                "                                   <img src=\"{{mailImage5}}\" width=\"641\" height=\"28\" style=\"display: block\" loading=\"lazy\" />\n" +
                "                               </td>\n" +
                "                           </tr>\n" +
                "                           <tr>\n" +
                "                               <td align=\"center\" style=\"padding: 56px 0 0 0\">\n" +
                "                                   <img src=\"{{mailImage6}}\" width=\"641\" height=\"237\" style=\"display: block\" loading=\"lazy\" />\n" +
                "                               </td>\n" +
                "                           </tr>\n" +
                "                           <tr>\n" +
                "                               <td align=\"center\" style=\"padding: 20px 0 0 0; font-family: '맑은고딕', Malgun Gothic, '돋움', Dotum, Helvetica, 'Apple SD Gothic Neo', Sans-serif\">\n" +
                "                                   <a href=\"{{homeUrl}}\" style=\"display: block; line-height: 45px; text-align: center; background-color: #0a4571; text-decoration: none; font-size: 15px; color: #ffffff\" rel=\"noreferrer noopener\" target=\"_blank\"> RESUME9 둘러보기 </a>\n" +
                "                               </td>\n" +
                "                           </tr>\n" +
                "                         </tbody>\n" +
                "                       </table>\n" +
                "                       </td>\n" +
                "                       </tr>\n" +
                "                       <tr>\n" +
                "                           <td style=\"height: 80px; padding: 0\"></td>\n" +
                "                       </tr>\n" +
                "                        </tbody>\n" +
                "                   </table>\n" +
                "                   </td>\n" +
                "                       <td style=\"padding: 0\">&nbsp;</td>\n" +
                "                   </tr>\n" +
                "                   </tbody>\n" +
                "                    </table>\n" +
                "                    </td>\n" +
                "                    </tr>\n" +
                "                    <!-- //container -->\n" +
                "                    <!-- footer -->\n" +
                "                   <tr>\n" +
                "                   <td style=\"padding: 0\">\n" +
                "                   <table style=\"table-layout: fixed; border-collapse: collapse; border-spacing: 0; border: 0; background: #f8f8f8; width: 100%; border-top: 1px solid #dcdcdc\">\n" +
                "                   <tbody>\n" +
                "                   <tr>\n" +
                "                   <td align=\"left\" style=\"padding: 20px 40px 20px 40px\">\n" +
                "                   <span style=\"font-family: '맑은고딕', Malgun Gothic, '돋움', Dotum, Helvetica, 'Apple SD Gothic Neo', Sans-serif; font-size: 12px; line-height: 20px; color: #ababab\">\n" +
                "                   <br />이 메일은 발신전용 메일입니다. 궁금하신 사항은 <a href=\"{{mailSupportUrl}}\" style=\"color: #242424; text-decoration: underline\" rel=\"noreferrer noopener\" target=\"_blank\">[고객센터]</a>를 이용해 주시기 바랍니다. <br />RESUME9의 모든 메일을 수신하기\n" +
                "                   원치 않으신다면 여기 <a href=\"{{mailRejectUrl}}\" target=\"_blank\" style=\"color: #242424; text-decoration: underline\" rel=\"noreferrer noopener\">[수신거부]</a>를 클릭하여 주시기 바랍니다.\n" +
                "                   </span>\n" +
                "                   </td>\n" +
                "                   </tr>\n" +
                "                   <tr>\n" +
                "                   <td style=\"height: 30px\"></td>\n" +
                "                   </tr>\n" +
                "                   <tr>\n" +
                "                   <td style=\"font-size: 13px; color: #999999; padding-left: 40px; padding-right: 40px; font-family: '맑은고딕', Malgun Gothic, '돋움', Dotum, Helvetica, 'Apple SD Gothic Neo', Sans-serif\">본 메일은 발송전용이며, 문의에 대한 회신은 처리되지 않습니다.</td>\n" +
                "                   </tr>\n" +
                "                   <tr>\n" +
                "                   <td style=\"height: 20px\"></td>\n" +
                "                   </tr>\n" +
                "                   <tr>\n" +
                "                   <td style=\"font-size: 14px; color: #666666; padding-left: 40px; padding-right: 40px; font-family: '맑은고딕', Malgun Gothic, '돋움', Dotum, Helvetica, 'Apple SD Gothic Neo', Sans-serif\">\n" +
                "                   레쥬메나인(주) | 사업자등록번호 : 543-81-01709 | 직업정보제공사업:J1200020200017 | 통신판매업 : 제 2021-서울강남-02745호\n" +
                "                   </td>\n" +
                "                   </tr>\n" +
                "                   <tr>\n" +
                "                   <td style=\"height: 6px\"></td>\n" +
                "                   </tr>\n" +
                "                   <tr>\n" +
                "                   <td style=\"font-size: 12px; color: #999999; padding-left: 40px; padding-right: 40px\">Copyright ⓒ Resume9. All rights reserved.</td>\n" +
                "                   </tr>\n" +
                "                   <tr>\n" +
                "                   <td style=\"height: 30px\"></td>\n" +
                "                   </tr>\n" +
                "                   </tbody>\n" +
                "                   </table>\n" +
                "                   </td>\n" +
                "                   </tr>\n" +
                "                   <!-- //footer -->\n" +
                "                   </tbody>\n" +
                "                   </table>\n" +
                "                   </body>\n" +
                "                   </html>";

 /*       String content = "<!DOCTYPE html>\n" +
                "<html lang=\"ko\">\n" +
                "  <head>\n" +
                "    <meta charset=\"UTF-8\" />\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n" +
                "    <title>Document</title>\n" +
                "  </head>\n" +
                "  <body style=\"margin: 0; background: #eeeeee\">\n" +
                "    <table style=\"font-family: '나눔고딕', 돋움, dotum, arial; font-size: 14px; color: #000; width: 800px; border: 0; table-layout: fixed; border-collapse: collapse; border-spacing: 0; background: #ffffff\">\n" +
                "      <!-- header -->\n" +
                "      <tbody>\n" +
                "        <tr>\n" +
                "          <td style=\"padding: 0\">\n" +
                "            <table style=\"table-layout: fixed; border-collapse: collapse; border-spacing: 0; border: 0; width: 100%\">\n" +
                "              <colgroup>\n" +
                "                <col style=\"width: 40px\" />\n" +
                "                <col />\n" +
                "                <col style=\"width: 40px\" />\n" +
                "              </colgroup>\n" +
                "              <tbody>\n" +
                "                <tr>\n" +
                "                  <td style=\"padding: 0\">&nbsp;</td>\n" +
                "                  <td style=\"padding: 0\">\n" +
                "                    <table style=\"table-layout: fixed; border-collapse: collapse; border-spacing: 0; border: 0; width: 100%; border-bottom: 4px solid #000\">\n" +
                "                      <tbody>\n" +
                "                        <tr>\n" +
                "                          <td style=\"height: 20px; padding: 0\"></td>\n" +
                "                        </tr>\n" +
                "                        <tr>\n" +
                "                          <td style=\"padding: 0\"><img src=\"{{logoImagePath}}\" alt=\"Resume9\" style=\"border: 0; vertical-align: top\" /></td>\n" +
                "                        </tr>\n" +
                "                        <tr>\n" +
                "                          <td style=\"height: 20px; padding: 0\"></td>\n" +
                "                        </tr>\n" +
                "                      </tbody>\n" +
                "                    </table>\n" +
                "                  </td>\n" +
                "                  <td style=\"padding: 0\">&nbsp;</td>\n" +
                "                </tr>\n" +
                "              </tbody>\n" +
                "            </table>\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "        <!-- //header -->\n" +
                "        <!-- container -->\n" +
                "        <tr>\n" +
                "          <td style=\"padding: 0\">\n" +
                "            <table style=\"table-layout: fixed; border-collapse: collapse; border-spacing: 0; width: 100%; border: 0\">\n" +
                "              <colgroup>\n" +
                "                <col style=\"width: 40px\" />\n" +
                "                <col />\n" +
                "                <col style=\"width: 40px\" />\n" +
                "              </colgroup>\n" +
                "              <tbody>\n" +
                "                <tr>\n" +
                "                  <td style=\"padding: 0\">&nbsp;</td>\n" +
                "                  <td>\n" +
                "                    <table style=\"table-layout: fixed; border-collapse: collapse; border-spacing: 0; border: 0; width: 100%; letter-spacing: -0.9px\">\n" +
                "                      <tbody>\n" +
                "                        <tr>\n" +
                "                          <td style=\"height: 45px; padding: 0\"></td>\n" +
                "                        </tr>\n" +
                "                        <tr>\n" +
                "                          <td align=\"center\" style=\"font-size: 34px; font-weight: 700; color: #000000; letter-spacing: -1.8px\">포지션 제안 안내</td>\n" +
                "                        </tr>\n" +
                "                        <tr>\n" +
                "                          <td style=\"height: 20px; padding: 0\"></td>\n" +
                "                        </tr>\n" +
                "                        <tr>\n" +
                "                          <td style=\"font-size: 14px; line-height: 1.6; color: #666; font-family: '맑은고딕', Malgun Gothic, '돋움', Dotum, Helvetica, 'Apple SD Gothic Neo', Sans-serif\">\n" +
                "                            <div style=\"color: #000\">{{content}}</div>\n" +
                "                          </td>\n" +
                "                        </tr>\n" +
                "                        <tr>\n" +
                "                          <td>\n" +
                "                            <div style=\"margin-top: 40px\">\n" +
                "                              <div style=\"display: inline-block; width: 100%; vertical-align: top\">\n" +
                "                                <table style=\"table-layout: fixed; width: 100%; background: #f8f8fa; border-spacing: 0; padding: 30px\">\n" +
                "                                  <tbody>\n" +
                "                                    <tr>\n" +
                "                                      <td style=\"color: #000; line-height: 22px; font-family: '맑은고딕', Malgun Gothic, '돋움', Dotum, Helvetica, 'Apple SD Gothic Neo', Sans-serif\">\n" +
                "                                        <a href=\"{{proposalUrl}}\" style=\"display: block; line-height: 45px; text-align: center; background-color: #0a4571; text-decoration: none; font-size: 15px; color: #ffffff\" rel=\"noreferrer noopener\" target=\"_blank\">\n" +
                "                                          동의서 및 이력서 등록하기\n" +
                "                                        </a>\n" +
                "                                      </td>\n" +
                "                                    </tr>\n" +
                "                                  </tbody>\n" +
                "                                </table>\n" +
                "                              </div>\n" +
                "                            </div>\n" +
                "                          </td>\n" +
                "                        </tr>\n" +
                "\n" +
                "                        <tr>\n" +
                "                          <td align=\"center\" style=\"padding: 35px 0 0 0\">\n" +
                "                            <font style=\"font-size: 12px; line-height: 20px; color: #ababab\">\n" +
                "                              이 메일은 <font style=\"color: #ea4141\">헤드헌팅 플랫폼 RESUME9</font>의 <b style=\"color: #242424\">인증 헤드헌터</b>가 발송한 메일로 발신전용 메일입니다. <br />헤드헌터에게 문의 또는 지원, 지원거부는 메일 내 버튼을 클릭하여 이용하실 수 있습니다.\n" +
                "                            </font>\n" +
                "                          </td>\n" +
                "                        </tr>\n" +
                "                        <tr>\n" +
                "                          <td style=\"height: 80px; padding: 0\"></td>\n" +
                "                        </tr>\n" +
                "                      </tbody>\n" +
                "                    </table>\n" +
                "                  </td>\n" +
                "                  <td style=\"padding: 0\">&nbsp;</td>\n" +
                "                </tr>\n" +
                "              </tbody>\n" +
                "            </table>\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "        <!-- //container -->\n" +
                "        <!-- footer -->\n" +
                "\n" +
                "        <tr>\n" +
                "          <td style=\"padding: 0\">\n" +
                "            <table style=\"table-layout: fixed; border-collapse: collapse; border-spacing: 0; border: 0; background: #f8f8f8; width: 100%; border-top: 1px solid #dcdcdc\">\n" +
                "              <tbody>\n" +
                "                <tr>\n" +
                "                  <td align=\"left\" style=\"padding: 20px 40px 20px 40px\">\n" +
                "                    <span style=\"font-family: '맑은고딕', Malgun Gothic, '돋움', Dotum, Helvetica, 'Apple SD Gothic Neo', Sans-serif; font-size: 12px; line-height: 20px; color: #ababab\">\n" +
                "                      본 메일은 정보통신망법률등 관련규정에 의거하여 발송날짜 기준으로 메일 수신에 동의한 회원님을 대상으로 발송되었습니다.\n" +
                "                      <br />만약 더 이상 메일 수신을 원하지 않으시면 여기 <a href=\"{{mailRejectUrl}}\" target=\"_blank\" style=\"color: #242424; text-decoration: underline\" rel=\"noreferrer noopener\">[수신거부]</a>를 클릭하여 주시기\n" +
                "                      바랍니다. <br />If you don’t want to receive this type of e-mail anymore,\n" +
                "                      <a href=\"{{mailRejectUrl}}\" target=\"_blank\" style=\"color: #242424; text-decoration: underline\" rel=\"noreferrer noopener\">please click the refuse here.</a> <br />본 메일은 발신전용으로 회신되지 않으며,\n" +
                "                      문의사항은 <a href=\"{{mailSupportUrl}}\" target=\"_blank\" style=\"color: #242424; text-decoration: underline\" rel=\"noreferrer noopener\">고객센터</a>를 이용해 주세요.\n" +
                "                    </span>\n" +
                "                  </td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                  <td style=\"height: 30px\"></td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                  <td style=\"font-size: 13px; color: #999999; padding-left: 40px; padding-right: 40px; font-family: '맑은고딕', Malgun Gothic, '돋움', Dotum, Helvetica, 'Apple SD Gothic Neo', Sans-serif\">본 메일은 발송전용이며, 문의에 대한 회신은 처리되지 않습니다.</td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                  <td style=\"height: 20px\"></td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                  <td style=\"font-size: 14px; color: #666666; padding-left: 40px; padding-right: 40px; font-family: '맑은고딕', Malgun Gothic, '돋움', Dotum, Helvetica, 'Apple SD Gothic Neo', Sans-serif\">\n" +
                "                    레쥬메나인(주) | 사업자등록번호 : 543-81-01709 | 직업정보제공사업:J1200020200017 | 통신판매업 : 제 2021-서울강남-02745호\n" +
                "                  </td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                  <td style=\"height: 6px\"></td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                  <td style=\"font-size: 12px; color: #999999; padding-left: 40px; padding-right: 40px\">Copyright ⓒ Resume9. All rights reserved.</td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                  <td style=\"height: 30px\"></td>\n" +
                "                </tr>\n" +
                "              </tbody>\n" +
                "            </table>\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "        <!-- //footer -->\n" +
                "      </tbody>\n" +
                "    </table>\n" +
                "  </body>\n" +
                "</html>\n";*/
       /* String content = "<!DOCTYPE html>\n" +
                "<html lang=\"ko\">\n" +
                "  <head>\n" +
                "    <meta charset=\"UTF-8\" />\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n" +
                "    <title>Document</title>\n" +
                "  </head>\n" +
                "  <body style=\"margin: 0; background: #eeeeee\">\n" +
                "    <table style=\"font-family: '나눔고딕', 돋움, dotum, arial; font-size: 14px; color: #000; width: 800px; border: 0; table-layout: fixed; border-collapse: collapse; border-spacing: 0; background: #ffffff\">\n" +
                "      <!-- header -->\n" +
                "      <tbody>\n" +
                "        <tr>\n" +
                "          <td style=\"padding: 0\">\n" +
                "            <table style=\"table-layout: fixed; border-collapse: collapse; border-spacing: 0; border: 0; width: 100%\">\n" +
                "              <colgroup>\n" +
                "                <col style=\"width: 40px\" />\n" +
                "                <col />\n" +
                "                <col style=\"width: 40px\" />\n" +
                "              </colgroup>\n" +
                "              <tbody>\n" +
                "                <tr>\n" +
                "                  <td style=\"padding: 0\">&nbsp;</td>\n" +
                "                  <td style=\"padding: 0\">\n" +
                "                    <table style=\"table-layout: fixed; border-collapse: collapse; border-spacing: 0; border: 0; width: 100%; border-bottom: 4px solid #000\">\n" +
                "                      <tbody>\n" +
                "                        <tr>\n" +
                "                          <td style=\"height: 20px; padding: 0\"></td>\n" +
                "                        </tr>\n" +
                "                        <tr>\n" +
                "                          <td style=\"padding: 0\"><img src=\"{{logoImagePath}}\" alt=\"Resume9\" style=\"border: 0; vertical-align: top\" /></td>\n" +
                "                        </tr>\n" +
                "                        <tr>\n" +
                "                          <td style=\"height: 20px; padding: 0\"></td>\n" +
                "                        </tr>\n" +
                "                      </tbody>\n" +
                "                    </table>\n" +
                "                  </td>\n" +
                "                  <td style=\"padding: 0\">&nbsp;</td>\n" +
                "                </tr>\n" +
                "              </tbody>\n" +
                "            </table>\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "        <!-- //header -->\n" +
                "        <!-- container -->\n" +
                "        <tr>\n" +
                "          <td style=\"padding: 0\">\n" +
                "            <table style=\"table-layout: fixed; border-collapse: collapse; border-spacing: 0; width: 100%; border: 0\">\n" +
                "              <colgroup>\n" +
                "                <col style=\"width: 40px\" />\n" +
                "                <col />\n" +
                "                <col style=\"width: 40px\" />\n" +
                "              </colgroup>\n" +
                "              <tbody>\n" +
                "                <tr>\n" +
                "                  <td style=\"padding: 0\">&nbsp;</td>\n" +
                "                  <td>\n" +
                "                    <table style=\"table-layout: fixed; border-collapse: collapse; border-spacing: 0; border: 0; width: 100%; letter-spacing: -0.9px\">\n" +
                "                      <tbody>\n" +
                "                        <tr>\n" +
                "                          <td style=\"height: 45px; padding: 0\"></td>\n" +
                "                        </tr>\n" +
                "                        <tr>\n" +
                "                          <td style=\"height: 20px; padding: 0\"></td>\n" +
                "                        </tr>\n" +
                "                        <tr>\n" +
                "                          <td style=\"font-size: 14px; line-height: 1.6; color: #666; font-family: '맑은고딕', Malgun Gothic, '돋움', Dotum, Helvetica, 'Apple SD Gothic Neo', Sans-serif\">\n" +
                "                            {{content}}" +
                "                          </td>\n" +
                "                        </tr>\n" +
                "                        <tr>\n" +
                "                          <td>\n" +
                "                            <div style=\"margin-top: 40px\">\n" +
                "                              <div style=\"display: inline-block; width: 100%; vertical-align: top\">\n" +
                "                                <table style=\"table-layout: fixed; width: 100%; background: #f8f8fa; border-spacing: 0; padding: 30px\">\n" +
                "                                  <tbody>\n" +
                "                                    <tr>\n" +
                "                                      <td style=\"color: #000; line-height: 22px; font-family: '맑은고딕', Malgun Gothic, '돋움', Dotum, Helvetica, 'Apple SD Gothic Neo', Sans-serif\">\n" +
                "                                        <a\n" +
                "                                          href=\"{{companyUrl}}\"\n" +
                "                                          style=\"display: block; line-height: 45px; text-align: center; background-color: #0a4571; text-decoration: none; font-size: 15px; color: #ffffff\"\n" +
                "                                          rel=\"noreferrer noopener\"\n" +
                "                                          target=\"_blank\"\n" +
                "                                        >\n" +
                "                                          확인하러가기\n" +
                "                                        </a>\n" +
                "                                      </td>\n" +
                "                                    </tr>\n" +
                "                                  </tbody>\n" +
                "                                </table>\n" +
                "                              </div>\n" +
                "                            </div>\n" +
                "                          </td>\n" +
                "                        </tr>\n" +
                "                        <tr>\n" +
                "                          <td align=\"center\" style=\"padding: 35px 0 0 0\">\n" +
                "                            <font style=\"font-size: 12px; line-height: 20px; color: #ababab\">\n" +
                "                              이 메일은 <font style=\"color: #ea4141\">헤드헌팅 플랫폼 RESUME9</font>의 <b style=\"color: #242424\">인증 헤드헌터</b>가 발송한 메일로 발신전용 메일입니다. <br />헤드헌터에게 문의 또는 지원, 지원거부는 메일 내 버튼을 클릭하여 이용하실 수 있습니다.\n" +
                "                            </font>\n" +
                "                          </td>\n" +
                "                        </tr>\n" +
                "                        <tr>\n" +
                "                          <td style=\"height: 80px; padding: 0\"></td>\n" +
                "                        </tr>\n" +
                "                      </tbody>\n" +
                "                    </table>\n" +
                "                  </td>\n" +
                "                  <td style=\"padding: 0\">&nbsp;</td>\n" +
                "                </tr>\n" +
                "              </tbody>\n" +
                "            </table>\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "        <!-- //container -->\n" +
                "        <!-- footer -->\n" +
                "\n" +
                "        <tr>\n" +
                "          <td style=\"padding: 0\">\n" +
                "            <table style=\"table-layout: fixed; border-collapse: collapse; border-spacing: 0; border: 0; background: #f8f8f8; width: 100%; border-top: 1px solid #dcdcdc\">\n" +
                "              <tbody>\n" +
                "                <tr>\n" +
                "                  <td align=\"left\" style=\"padding: 20px 40px 20px 40px\">\n" +
                "                    <span style=\"font-family: '맑은고딕', Malgun Gothic, '돋움', Dotum, Helvetica, 'Apple SD Gothic Neo', Sans-serif; font-size: 12px; line-height: 20px; color: #ababab\">\n" +
                "                      본 메일은 정보통신망법률등 관련규정에 의거하여 발송날짜 기준으로 메일 수신에 동의한 회원님을 대상으로 발송되었습니다.\n" +
                "                      <br />만약 더 이상 메일 수신을 원하지 않으시면 여기 <a href=\"{{mailRejectUrl}}\" target=\"_blank\" style=\"color: #242424; text-decoration: underline\" rel=\"noreferrer noopener\">[수신거부]</a>를 클릭하여 주시기\n" +
                "                      바랍니다. <br />If you don’t want to receive this type of e-mail anymore,\n" +
                "                      <a href=\"{{mailRejectUrl}}\" target=\"_blank\" style=\"color: #242424; text-decoration: underline\" rel=\"noreferrer noopener\">please click the refuse here.</a> <br />본 메일은 발신전용으로 회신되지 않으며,\n" +
                "                      문의사항은 <a href=\"{{mailSupportUrl}}\" target=\"_blank\" style=\"color: #242424; text-decoration: underline\" rel=\"noreferrer noopener\">고객센터</a>를 이용해 주세요.\n" +
                "                    </span>\n" +
                "                  </td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                  <td style=\"height: 30px\"></td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                  <td style=\"font-size: 13px; color: #999999; padding-left: 40px; padding-right: 40px; font-family: '맑은고딕', Malgun Gothic, '돋움', Dotum, Helvetica, 'Apple SD Gothic Neo', Sans-serif\">본 메일은 발송전용이며, 문의에 대한 회신은 처리되지 않습니다.</td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                  <td style=\"height: 20px\"></td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                  <td style=\"font-size: 14px; color: #666666; padding-left: 40px; padding-right: 40px; font-family: '맑은고딕', Malgun Gothic, '돋움', Dotum, Helvetica, 'Apple SD Gothic Neo', Sans-serif\">\n" +
                "                    레쥬메나인(주) | 사업자등록번호 : 543-81-01709 | 직업정보제공사업:J1200020200017 | 통신판매업 : 제 2021-서울강남-02745호\n" +
                "                  </td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                  <td style=\"height: 6px\"></td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                  <td style=\"font-size: 12px; color: #999999; padding-left: 40px; padding-right: 40px\">Copyright ⓒ Resume9. All rights reserved.</td>\n" +
                "                </tr>\n" +
                "                <tr>\n" +
                "                  <td style=\"height: 30px\"></td>\n" +
                "                </tr>\n" +
                "              </tbody>\n" +
                "            </table>\n" +
                "          </td>\n" +
                "        </tr>\n" +
                "        <!-- //footer -->\n" +
                "      </tbody>\n" +
                "    </table>\n" +
                "  </body>\n" +
                "</html>";
*/
        /*String subject = "{{subject}}";
        String template = "PROPOSAL_TEMP";
        MailUtil.createTemplate(subject, template, null, content);*/
        String subject = "{{subject}}";
        String template = "TEMP_SIGNUP";
        MailUtil.createTemplate(subject, template, null, content);
    }
}