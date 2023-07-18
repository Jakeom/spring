package com.fw.fo.system.config.rabbitmq;

import com.fw.core.code.ResponseCode;
import com.fw.core.common.service.CommonFileService;
import com.fw.core.dto.FileDTO;
import com.fw.core.vo.ResponseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ParserController {

    private static final String EXCHANGE_NAME = "r9.parser";

    private final RabbitTemplate rabbitTemplate;
    private final CommonFileService commonFileService;
    private final SimpMessagingTemplate messagingTemplate;

    @PostMapping("/fo/auth/rabbit")
    @ResponseBody
    public ResponseEntity<ResponseVO> rabbit(@RequestPart(value = "resumeFile", required = false) MultipartFile[] resumeFile,
                                             @RequestPart(value = "memberId", required = true) String memberId,
                                             @RequestPart(value = "resumeType", required = true) String resumeType
    ) {
        ResponseCode code = ResponseCode.SUCCESS;
        try {
            int fileSeq = commonFileService.fileUpload(resumeFile, "RESUME");
            List<FileDTO> list = commonFileService.selectFileDetailList(String.valueOf(fileSeq));
            Map<String, Object> map = new HashMap<>();
            map.put("memberSn", "ADMIN_" + memberId);
            map.put("fileName", list.get(0).getName());
            map.put("filePath", list.get(0).getPath());
            map.put("resumeType", resumeType);
            map.put("callType", "show");
            rabbitTemplate.convertAndSend(EXCHANGE_NAME, "single", JSONObject.toJSONString(map));
        } catch (Exception e){
            log.error("error", e);
            code = ResponseCode.INTERNAL_SERVER_ERROR;
        }
        return ResponseEntity.status(code.getHttpStatus()).body(ResponseVO.builder(code).data(code).build());
    }

    @PutMapping("/fo/auth/rabbit/request")
    @ResponseBody
    public String rabbitRequest(@RequestBody Map<String, Object> map){
        try{
            log.info("{}", JSONObject.toJSONString(map));
            messagingTemplate.convertAndSend("/topic/all", JSONObject.toJSONString(map));
        } catch (Exception e){
            log.error("error", e);
        }


        return JSONObject.toJSONString(map);
    }

}
