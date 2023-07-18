package com.fw.batch.push.service;

import com.fw.batch.constants.CommonConstant.SEND_YN;
import com.fw.core.dto.batch.PushHistBean;
import com.fw.core.mapper.db1.batch.BatchPushMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@EnableAsync
@Service
@Slf4j
@RequiredArgsConstructor
public class PushService {

    /** 안드로이드키 */
    @Value("${push.android_auth_key}")
    private String ANDROID_AUTH_KEY;

    /** IOS DB URL */
    @Value("${push.ios_database_url}")
    private static String IOS_DATABASE_URL;

    /** DOMAIN */
    // @Value("${service.domain}")
    private String DOMAIN;

    /** MOBILE DOMAIN */
    // @Value("${service.mobile_domain}")
    private static String MOBILE_DOMAIN;

    private final ResourceLoader resourceLoader;
    private final BatchPushMapper batchPushMapper;


    /**
     * 각 OS별로 분리 및 메시지 설정
     */
    public void insertPush() throws Exception {
        // PhoneType, PhoneToken 안 들어 온거 Fail 처리
        batchPushMapper.updatePushHistPhoneTokenANDPhoneTypeIsNull();

        // userList를 안드로이드용과 iOS용으로 분리
        List<PushHistBean> userListAndroid = new ArrayList<>();
        List<PushHistBean> userListIos = new ArrayList<>();
        List<PushHistBean> pushList = batchPushMapper.selectTargetPushHistList(null);

        for (PushHistBean p : pushList) {
            if ("ANDROID".equals(p.getPhoneType())) {
                userListAndroid.add(p);
            }

            if ("IOS".equals(p.getPhoneType())) {
                userListIos.add(p);
            }
        }

        // Android 메시지 설정 및 푸시 발송
        for (PushHistBean p : userListAndroid) {

            boolean result = false;

            if(StringUtils.isNotBlank(p.getPhoneToken()) && StringUtils.isNotBlank(p.getPhoneType())) { // pushToken,phoneType 존재 시에만 푸시발송
                Map<String, Object> paramMap = new HashMap<String, Object>();

                paramMap.put("title", p.getTitle());
                paramMap.put("content", p.getContent());
                paramMap.put("smartphoneToken", p.getPhoneToken());
                paramMap.put("pushType", p.getDispType());
                paramMap.put("image", DOMAIN + p.getImgPath());

                try {
                    /** 푸시 결과값  */
                    result = sendAndroid(paramMap, 1);
                } catch (Exception e) {
                    result = false;
                    log.error("error", e);
                }
            }

            /** 푸시 결과값  history update */
            PushHistBean bean = new PushHistBean();
            bean.setPushHistSeq(p.getPushHistSeq());
            bean.setUpdateId("push");
            bean.setSendYn(result?SEND_YN.Y:SEND_YN.F);
            batchPushMapper.updatePushHist(bean);
        }

        // IOS 메시지 설정 및 푸시 발송
        for (PushHistBean p : userListIos) {

            /** 최초에만 firsebase init 실행 */
            if(FirebaseApp.getApps().size() == 0) {
                initializeIOS();
            }

            boolean result = false;

            if(StringUtils.isNotBlank(p.getPhoneToken()) && StringUtils.isNotBlank(p.getPhoneType())) { // pushToken,phoneType 존재 시에만 푸시발송
                try {
                    /** ios 푸시 발송*/
                    sendToIOS(p);

                    result = true;
                } catch (Exception e) {
                    log.error("error", e);
                }
            }

            PushHistBean bean = new PushHistBean();
            bean.setPushHistSeq(p.getPushHistSeq());
            bean.setUpdateId("push");
            bean.setSendYn(result?SEND_YN.Y:SEND_YN.F);
            batchPushMapper.updatePushHist(bean);

        }

    }

    /**
     * IOS FIREBASE initalize
     */
    public void initializeIOS() {

        FileInputStream serviceAccount;
        try {
            final org.springframework.core.io.Resource resource = resourceLoader.getResource("classpath:resume9-n-firebase-adminsdk-hkeq1-f2e03ec7c3.json");
            serviceAccount = new FileInputStream(resource.getFile().toPath().toString());
            @SuppressWarnings("deprecation")
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setDatabaseUrl(IOS_DATABASE_URL)
                    .build();

            FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            log.error("error", e);
        }

    }

    public void sendToIOS(PushHistBean pushBean) throws FirebaseMessagingException {

        /** 이벤트 Service */

        Notification notification = Notification.builder()
                .setTitle(pushBean.getTitle())
                .setBody(pushBean.getContent())
                .build();

        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("title", pushBean.getTitle());
        paramMap.put("content", pushBean.getContent());
        paramMap.put("pushType", pushBean.getDispType());
        paramMap.put("smartphoneToken", pushBean.getPhoneToken());

        if(StringUtils.isNotBlank(pushBean.getUrl())) {
            paramMap.put("url", pushBean.getUrl());
        }

        Message message = null;

        message = Message.builder()
                .setNotification(notification)
                .setApnsConfig(ApnsConfig.builder()
                                .putAllCustomData(paramMap)
                                .setAps(Aps.builder()
                                .setMutableContent(true)
                                .setContentAvailable(true)
                                .setSound("alarm.wav")
                                .setBadge(0)
                                .build())
                        //.setFcmOptions(ApnsFcmOptions.builder().setImage(DOMAIN + pushBean.getImgPath())
                          //      .build())
                        .build())
                .setToken(pushBean.getPhoneToken())
                .build();


        // Send a message to the device corresponding to the provided
        // registration token.
        String response = String.valueOf(FirebaseMessaging.getInstance().sendAsync(message));
        // Response is a message ID string.
        JSONObject j = new JSONObject(paramMap);
        log.info("Payload - {}", j);
        log.info("Successfully sent message: {}", response);

    }

    @Async
    boolean sendAndroid(Map<String, Object> paramMap, int retryCnt) throws Exception {

        /** 결과 용 value */
        boolean pushResult = false;

        try {

            URL url = new URL("https://fcm.googleapis.com/fcm/send");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            //conn.setConnectTimeout(60 * 1000);
            //conn.setReadTimeout(60 * 1000);
            //conn.setAllowUserInteraction(true);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "key=" + ANDROID_AUTH_KEY);
            conn.setDoOutput(true);


            JSONObject data = new JSONObject();
            data.put("title", paramMap.get("title"));

            JSONObject android = new JSONObject();
            data.put("priority", paramMap.get("high"));

            JSONObject message = new JSONObject();

            data.put("messageBody", paramMap.get("content"));
            if(paramMap.get("image") != null || paramMap.get("image") != "") {
                data.put("image", paramMap.get("image"));
            }

            data.put("pushType", paramMap.get("pushType"));
            data.put("url", paramMap.get("url"));

            message.put("data", data);
            message.put("android", android);
            message.put("to", paramMap.get("smartphoneToken"));

            String input = message.toString();

            OutputStream os = conn.getOutputStream();

            // 서버에서 날려서 한글 깨지는 사람은 아래처럼  UTF-8로 인코딩해서 날려주자
            os.write(input.getBytes("UTF-8"));
            os.flush();
            os.close();

            int responseCode = conn.getResponseCode();
            log.info("\nSending 'POST' request to URL : {}", url);
            log.info("Payload : {}", input);
            log.info("Response Code : {}", responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            log.info("#### ANDROID PUSH : {}", response.toString());

            /** 정상작동하면서 error code가 안넘어 온 경우 */
            if( responseCode == 200 && !response.toString().contains("error") )
            {
                log.info("#### PUSH RESULT : SUCCESS");
                pushResult = true;
            }
            /** 정상작동이 아니거나 error code가 넘어 온 경우 */
            else
            {
                log.info("#### PUSH RESULT : FAIL");
            }
        } catch (IOException e) {

            // 재시도 횟수가 3이상인경우
            if (retryCnt >= 3) {
                log.error("error", e);
                throw e;
            } else {
                Thread.sleep(500);
                sendAndroid(paramMap, retryCnt + 1);
            }

        } catch (Exception e) {
            log.error("error", e);
            throw e;
        }
        return pushResult;
    }

    /**
     * 안드로이드 푸시전송
     *
     * @param msg        송신 메시지
     * @param deviceList 안드로이드 토큰리스트
     * @throws Exception
     */
    public void pushAndroidList(String title, String msg, String type, List<String> deviceList) throws Exception {

        // 메시지 설정
        Map<String, Object> bodyMap = new HashMap<String, Object>();
        bodyMap.put("title", title);
        bodyMap.put("body", msg);
        bodyMap.put("type", type);

        // 한번에 1000 건씩 송신이 가능하기 때문에, 1000건씩 분할 한다.
        List<List<String>> subDeviceList = getSubList(deviceList);

        for (int i = 0; i < subDeviceList.size(); i++) {

            if (i > 0) {
                Thread.sleep(500);
            }

            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("data", bodyMap);
            paramMap.put("registration_ids", subDeviceList.get(i));

            send(paramMap, 1);
        }

    }


    /**
     * 안드로이드 푸시전송
     *
     * @param msg        송신 메시지
     * @param deviceList 안드로이드 토큰리스트
     * @throws Exception
     */
    public void pushAndroid(String title, String msg, String type, String device_token) throws Exception {


        // 메시지 설정
        Map<String, Object> bodyMap = new HashMap<String, Object>();
        bodyMap.put("title", title);
        bodyMap.put("body", msg);
        bodyMap.put("type", type);


        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("data", bodyMap);
        paramMap.put("to", device_token);

        send(paramMap, 1);

    }

    /**
     * 리스트를 1000건씩 분할한다.
     *
     * @param deviceList
     * @return
     */
    private List<List<String>> getSubList(List<String> deviceList) {

        int maxSize = 999;
        List<List<String>> subDeviceList = new ArrayList<List<String>>();

        for (int i = 0; i < deviceList.size(); i += maxSize) {

            int toIdx = i + maxSize;

            if (deviceList.size() < toIdx) {
                toIdx = deviceList.size();
            }

            subDeviceList.add(deviceList.subList(i, toIdx));
        }

        return subDeviceList;
    }

    private void send(Map<String, Object> paramMap, int retryCnt) throws Exception {

        try {

            URL url = new URL("https://fcm.googleapis.com/fcm/send");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(60 * 1000);
            conn.setReadTimeout(60 * 1000);
            conn.setAllowUserInteraction(true);
            conn.setRequestProperty("Authorization", "key=" + ANDROID_AUTH_KEY);
            conn.setRequestProperty("Content-Type", "application/json");

            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            wr.write(new Gson().toJson(paramMap).toString());
            wr.flush();
            wr.close();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            log.debug("#### ANDROID PUSH : {}", response.toString());

        } catch (IOException e) {

            // 재시도 횟수가 3이상인경우
            if (retryCnt >= 3) {
                log.error("error", e);
                throw e;
            } else {
                Thread.sleep(500);
                send(paramMap, retryCnt + 1);
            }

        } catch (Exception e) {
            log.error("error", e);
            throw e;
        }
    }

    public int insertPushHist(PushHistBean bean){
        return batchPushMapper.insertPushHist(bean);
    }

    public int insertApPositionAlert(PushHistBean bean){
        return batchPushMapper.insertApPositionAlert(bean);
    }
}

