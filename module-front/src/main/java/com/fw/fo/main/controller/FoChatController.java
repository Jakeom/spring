package com.fw.fo.main.controller;

import com.fw.core.code.ResponseCode;
import com.fw.core.common.service.CommonFileService;
import com.fw.core.common.service.CommonService;
import com.fw.core.dto.PushHistDTO;
import com.fw.core.dto.fo.FoMemberDTO;
import com.fw.core.mapper.db1.common.CommonMapper;
import com.fw.core.vo.ResponseVO;
import com.fw.fo.main.service.FoChatService;
import com.fw.fo.position.service.FoPositionService;
import com.fw.hh.position.service.HhPositionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Main Controller
 * @author sjpaik
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class FoChatController {
	private final FoChatService foChatService;
	private final CommonService commonService;
	private final CommonMapper commonMapper;
	private final CommonFileService commonFileService;
	private final HhPositionService hhPositionService;
	private final FoPositionService foPositionService;

	@Value("${service.web_domain}")
    private String WEB_DOMAIN;
	
	private String chatSretKey = "resUme9_acem3cA#$dkzz1@";

    /**
     * Front 채팅 페이지
     */
    @GetMapping({"/hh/chat"})
    public String chat_hh_index(
	    			ModelMap model
	    			,HttpServletRequest request
	    			, Authentication authentication
					, PushHistDTO pushHistDTO
	    			, @RequestParam(value="errorMessage", required = false )String errorMessage
    			) {
    	model = get_alram_list(model, request, pushHistDTO, "Web");
        return "/fo/chat_index";
    }

    @GetMapping({"/fo/chat"})
    public String chat_fo_index(
					ModelMap model
					,HttpServletRequest request
					, Authentication authentication
					, PushHistDTO pushHistDTO
					, @RequestParam(value="errorMessage", required = false )String errorMessage
				) {
    	model = get_alram_list(model, request, pushHistDTO, "Web");
		model.addAttribute("mainUrl", WEB_DOMAIN);
        return "/fo/chat_index";
    }

    // 모바일로 들어오는 경우
	@GetMapping({"/m/chat"})
	public String mFoChat(
							 ModelMap model
							, HttpServletRequest request
							, @RequestParam(value="errorMessage", required = false ) String errorMessage
							, PushHistDTO pushHistDTO
	) {
		/*	
		Enumeration<String> headers = request.getHeaderNames();
		while (headers.hasMoreElements()) {
		    String name = (String) headers.nextElement();
		    String value = request.getHeader(name);
		    if("CHAT_ACCESS_KEY".equals(name)) {
		    	if(chatSretKey.equals(value)) {
		    		return "/fo/chat_index";
		    	}else {
		    		return "/common/error";
		    	}
		    }
		}
		*/		
		model = get_alram_list(model, request, pushHistDTO, "Mobile");
		model.addAttribute("mainUrl", WEB_DOMAIN);
		return "/fo/chat_index";
	}

	// 모바일로 들어오는 경우 채널 생성
	@GetMapping({"/m/chat/mobile"})
	public String mFoChat_mobile(
							 ModelMap model
							, HttpServletRequest request
							, @RequestParam(value="errorMessage", required = false ) String errorMessage
							, PushHistDTO pushHistDTO
	) {
		System.out.println("===========chatMobile===========");	
		model.addAttribute("mainUrl", WEB_DOMAIN);
		return "/fo/chat_mobile";
	}

	// 알림 목록 조회하기
	public ModelMap get_alram_list(
							 	  ModelMap model
								, HttpServletRequest request
								, PushHistDTO pushHistDTO
								, String type
							) {
		String userId = "";
		String dType = "";
		String userNm = "";
		if("Mobile".equals(type)) {
			userId = request.getParameter("userId")+"";
			dType = request.getParameter("userType")+"";
			userNm = request.getParameter("userNm")+"";
		}else {
			userId = pushHistDTO.getFrontSession().getId();
	    	dType = pushHistDTO.getFrontSession().getDType();
	    	// userNm = pushHistDTO.getFrontSession().getUsername();  // 사용자id
	    	userNm = pushHistDTO.getFrontSession().getName();		// 사용자 이름
		}
		model.addAttribute("userId", userId);
		model.addAttribute("userType", dType);
		model.addAttribute("userNm", userNm);

		// 알림 목록 조회
		pushHistDTO.setMemberId(userId);
		model.addAttribute("alarmList", foChatService.selectAlarmList(pushHistDTO));
		model.addAttribute("params", pushHistDTO);
		return model;
	}

	// 파일 다운로드
	@GetMapping({"/fo/chat/filedown"})
	public void fo_filedownload(
			 					HttpServletResponse response
			 					, @RequestParam Map<String, Object> params
			 					, HttpServletRequest request
			 				) throws Exception {
		hh_filedownload(response, params, request);
	}

	@GetMapping({"/hh/chat/filedown"})
	public void hh_filedownload(
			 					  HttpServletResponse response
			 					, @RequestParam Map<String, Object> params
			 					, HttpServletRequest request
			 				) throws Exception {
		URL url = null;
        InputStream in = null;
        OutputStream out = null;

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String line = null;

        try {
            String fileName = request.getParameter("fileName")+"";            
            String fileUrl = request.getParameter("filePath")+"";            
            String fileName2 = URLEncoder.encode(fileName, "UTF-8");
            fileUrl = fileUrl.split(fileName)[0]+fileName2;            
            
            response.setHeader("Content-Disposition",
					"attachment;filename=" + new String(fileName.getBytes("KSC5601"), "8859_1"));
			response.setHeader("Content-Transfer-Encoding", "binary");
            out = response.getOutputStream();           
            String httpsResult = "";

            url = new URL(fileUrl);
            in = url.openStream();
            while(true){
                //파일을 읽어온다.
                int data = in.read();
                if(data == -1){
                    break;
                }
                //파일을 쓴다.
                out.write(data);
            }
            in.close();
            out.close();

            /*
            String htmlUrl = fileUrl;
        	TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
		        	public X509Certificate[] getAcceptedIssuers() {
		        			return null;
		       		}
		       		public void checkClientTrusted(X509Certificate[] certs, String authType) {
		       		}
		       		public void checkServerTrusted(X509Certificate[] certs, String authType) {
		       		}
        		}
        	};

        	SSLContext sc = SSLContext.getInstance("SSL");
        	sc.init(null, trustAllCerts, new SecureRandom());
        	HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        	HttpsURLConnection conn = (HttpsURLConnection) new URL(htmlUrl).openConnection();
        	InputStream is = conn.getInputStream();

        	br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
        	while ((line = br.readLine()) != null) {
        		sb.append(line);

        		System.out.println(line);
        	}
        	*/

        } catch (Exception e) {
        } finally {
            if(in != null) in.close();
            if(out != null) out.close();
        }
	}

	/**
	 * 알림 읽음여부 처리
	 */
	@PostMapping("/m/chat/update-alarm-receive")
	@ResponseBody
	public ResponseEntity<ResponseVO> updateAlarmReceive(@RequestBody PushHistDTO pushHistDTO) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			foChatService.updateAlarmReceive(pushHistDTO);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}

	/**
	 * 알림 리스트 조회
	 */
	@GetMapping("/m/chat/alarm-list")
	public String selectAlarmList(ModelMap model, PushHistDTO pushHistDTO) {
		model.addAttribute("alarmList", foChatService.selectAlarmList(pushHistDTO));
		return "fo/alarm-list";
	}

	/**
	 * 채팅 푸시 등록
	 */
	@PostMapping("/m/chat/chat-push")
	@ResponseBody
	public ResponseEntity<ResponseVO> chatPush(PushHistDTO pushHistDTO , HttpServletRequest request) {
		ResponseCode code = ResponseCode.SUCCESS;
		try {
			String id = request.getParameter("targetId"); 		// 타겟일련번호
			JSONArray targetList = new JSONArray(id);
			String title = request.getParameter("title");		// 채팅방 이름
			String userId = request.getParameter("userId");		// 회원일련번호

			// TODO :: 알림설정에 맞게 푸쉬들어오는지 테스트필요
			for (int i=0; i<targetList.length(); i++) {
				// 수신자 정보조회
				String targetId = targetList.getString(i);
				if(!StringUtils.equals(targetId,userId)) {
					FoMemberDTO foMemberDTO = new FoMemberDTO();
					foMemberDTO.setMemberId(targetId);
					FoMemberDTO receiverInfo = commonService.selectMemberInfoByMemberId(foMemberDTO);
					// 발신자 정보조회
					FoMemberDTO memberDTO = new FoMemberDTO();
					memberDTO.setMemberId(userId);
					FoMemberDTO callerInfo = commonService.selectMemberInfoByMemberId(memberDTO);

					if (receiverInfo != null && callerInfo != null) {
						boolean flag = true;
						if (StringUtils.isNotBlank(receiverInfo.getPushToken()) && StringUtils.isNotBlank(receiverInfo.getPhoneType())) {
							// CASE1 - 수신자=후보자
							if (StringUtils.equals(receiverInfo.getDtype(), "AP")) {
								if (StringUtils.equals(receiverInfo.getApAlarmFlag(), "N")) { // 헤드헌터 메시지 알림여부 확인
									flag = false;
								}
							}
							// CASE2 - 수신자=헤드헌터
							if (StringUtils.equals(receiverInfo.getDtype(), "HH")) {
								if (StringUtils.equals(callerInfo.getDtype(), "AP")) { // CASE2 > case1 : 발신자=후보자
									if (StringUtils.equals(receiverInfo.getHhApMsgFlag(), "N")) { // 헤드헌터 > 후보자 메시지 알림여부 확인
										flag = false;
									}
								} else { // CASE2 > case2 : 발신자=헤드헌터
									if (StringUtils.equals(receiverInfo.getHhMsgFlag(), "N")) { // 헤드헌터 > 헤드헌터 메시지 알림여부 확인
										flag = false;
									}
								}
							}
							// 푸쉬발송
							if (flag) {
								// 수신자 AP/HH 구분에 따른 dispType 분기
								String dispType = "";
								if(StringUtils.equals(receiverInfo.getDtype(),"AP")) {
									dispType = "AP_CHAT";
								} else {
									dispType = "HH_CHAT";
								}
								pushHistDTO.setDispType(dispType);
								pushHistDTO.setMemberId(targetId);
								pushHistDTO.setTitle(title);
								pushHistDTO.setContent(callerInfo.getName() + "님이 메세지를 보냈습니다.");
								pushHistDTO.setPhoneType(receiverInfo.getPhoneType());
								pushHistDTO.setPhoneToken(receiverInfo.getPushToken());
								pushHistDTO.setCreateId(userId);
								commonMapper.insertPushHist(pushHistDTO);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).build());
	}

	/**
	 * 채팅 이미지가져오기
	 *
	 * request
	   imgList = [
		    {
		        memberId: 1
		    },
		    {
		        memberId: 2
		    },
		]

		response
		[
		    {
		        memberId: 1,
		        url: sjdfklfjkal
		    },
		    {
		        memberId: 2,
		        url: sjdfklfjkal
		    }
		]
	 */
	@PostMapping("/m/chat/chat-image")
	@ResponseBody
	public String chat_image(PushHistDTO pushHistDTO , HttpServletRequest request) {
		ResponseCode code = ResponseCode.SUCCESS;
		Map<String, Object> imgData = null;
		JSONArray imgTotList = new JSONArray();
		try {
			String imgStrng = request.getParameter("imgList");
			JSONArray imgList = new JSONArray(imgStrng);
			for (int i = 0; i < imgList.length(); i++) {
				JSONObject obj = imgList.getJSONObject(i);
				String memberId = obj.getString("memberId");

				System.out.println("==============memberId=============" + memberId);

				// 유저 프로필 파일 일련번호 조회
				FoMemberDTO foMemberDTO = new FoMemberDTO();
				foMemberDTO.setMemberId(memberId);
				FoMemberDTO userInfo = commonService.selectMemberInfoByMemberId(foMemberDTO);
				if (userInfo != null) {
					String url = "";
					// 파일 상세 조회
					if (StringUtils.isNotBlank(userInfo.getProfilePictureFileId())) {
						String fileSeq = userInfo.getProfilePictureFileId();
						userInfo.setCommonFileList(commonFileService.selectFileDetailList(fileSeq));
						url = userInfo.getCommonFileList().get(0).getUrl();
					}

					JSONObject imgMap = new JSONObject();
					imgMap.put("memberId", memberId);
					imgMap.put("url", url);
					imgTotList.put(imgMap);
				}

				System.out.println("==============memberId=============" + imgTotList);
			}
		} catch (Exception e) {
			log.error("error", e);
		}
		return imgTotList.toString();
	}

	/*@PostMapping("/m/chat/chat-image-position")
	@ResponseBody
	public ResponseEntity<ResponseVO> chat_image_position(PushHistDTO pushHistDTO , HttpServletRequest request) {
		ResponseCode code = ResponseCode.SUCCESS;
		Map<String, Object> imgData = null;
		List<Map<String, Object>> imgTotList = new ArrayList<Map<String, Object>>();
		try {
			String imgStrng = request.getParameter("imgList");
			JSONArray imgList = new JSONArray(imgStrng);
			for (int i = 0; i < imgList.length(); i++) {
				JSONObject obj = imgList.getJSONObject(i);
				String positionId = obj.getString("positionId");

				System.out.println("==============positionId============="+positionId);

				// 이미지 서버에서 가져오는 로직 가져와서 위에 response 형태로 만들어서 반환해줌

				// 유저 프로필 파일 일련번호 조회
				HhPositionDTO hhPositionDTO = new HhPositionDTO();
				hhPositionDTO.setPositionId(positionId);
				HhPositionDTO positionInfo = hhPositionService.selectPositionCompanyInfo(hhPositionDTO);

				// 파일 상세 조회
				String fileSeq = positionInfo.getIntroFileId();
				positionInfo.setCommonFileList(commonFileService.selectFileDetailList(fileSeq));

				Map<String, Object> imgMap = new HashMap<>();
				String url = "";
				if(positionInfo!=null){
					url = positionInfo.getCommonFileList().get(0).getUrl();
				}
				imgMap.put("positionId", positionId);
				imgMap.put("url", url);
				imgTotList.add(imgMap);
			}
			imgData.put("imgList", imgList);
		} catch (Exception e) {
			log.error("error", e);
			code = ResponseCode.INTERNAL_SERVER_ERROR;
		}
		return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(imgData).build());
	}*/




	public static void main(String args[]){
		callApi_token();
	}

	@GetMapping({"/m/chat/token"})
	@ResponseBody
	public String m_get_token(
			 					  HttpServletResponse response
			 					, @RequestParam Map<String, Object> params
			 					, HttpServletRequest request
			 				) throws Exception {
		String tokenJson = callApi_token();
		return tokenJson;
	}


	@GetMapping({"/hh/chat/token"})
	public String hh_get_token(
			 					  HttpServletResponse response
			 					, @RequestParam Map<String, Object> params
			 					, HttpServletRequest request
			 				) throws Exception {
		String tokenJson = callApi_token();
		return tokenJson;

	}

	@GetMapping({"/fo/chat/token"})
	public String fo_get_token(
			 					  HttpServletResponse response
			 					, @RequestParam Map<String, Object> params
			 					, HttpServletRequest request
			 				) throws Exception {
		String tokenJson = callApi_token();

		System.out.println("================tokenJson================="+tokenJson);

		return tokenJson;
	}

	// rest api 호출
	public static String callApi_token(){

        HttpURLConnection conn = null;
        JSONObject responseJson = null;

        String app_id = "resume9";
        //String app_key = "ca6de4691605708ccaa8ba05527ef96c";  	// eb1c2589658d95ae8c327b9bb9377b0a
    	//String app_url = "https://guest.gitplelive.io"; 		// resume9.gitplelive.io

        // 운영
    	String app_key = "eb1c2589658d95ae8c327b9bb9377b0a";
    	String app_url = "https://resume9.gitplelive.io";

        try {
            //URL 설정
        	String requestURL = app_url+"/v1/applications/token";
        	 HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
             HttpPost getRequest = new HttpPost(requestURL); //GET 메소드 URL 생성

             getRequest.setHeader("Accept", "application/json;charset=UTF-8;");
             getRequest.setHeader("Connection", "keep-alias");
             getRequest.setHeader("Content-Type", "application/json;charset=UTF-8;");

             getRequest.setHeader("APP_ID", app_id); //KEY 입력
             getRequest.setHeader("APP_API_KEY", app_key);

             HttpResponse response = client.execute(getRequest);
             ResponseHandler<String> handler = new BasicResponseHandler();
        	 String body = handler.handleResponse(response);

        	 System.out.println("================callApi_token================="+body);

        	 return body;
        } catch (Exception e) {
            System.out.println("not JSON Format response"+e);
            e.printStackTrace();
            return "{}";
        }
    }
}
