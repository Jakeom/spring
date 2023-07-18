package com.fw.core.util;

import com.coocon.securty.util.ISASSeedCBC;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ISASUtil {

    public static JSONObject encode(String inData) throws Exception {
        JSONObject output = new JSONObject();
        if(inData != null){
            /* JSON Parser */
            JSONParser inputParse = new JSONParser();
            JSONObject input = (JSONObject) inputParse.parse (inData);
            /* 결과 Data */
            String result   = (String)input.get("Data");
            String Uid      = (String)input.get("Uid");
            String Action   = (String)input.get("Action");

            /* 결과 암호화 */
            String encResults;
            if(Uid != null && Action != null && Uid.length() >= 30 && Action.length() >= 30){
                //고정키 아닌 경우
                encResults = ISASSeedCBC.encrypt(result , Uid, Action );
            } else {
                encResults = ISASSeedCBC.encrypt(result);
            }
            output.put("Result", "ENC=" + encResults);
        }
        return output;
    }

    public static JSONObject decode(String inData) throws Exception {
        JSONObject output = new JSONObject();
        if(inData != null){
            /* JSON Parser */
            JSONParser jParser = new JSONParser();

            /* Input JSONArray */
            JSONObject inJObj = (JSONObject) jParser.parse(inData);

            String data   = (String)inJObj.get("Data");
            String Uid      = (String)inJObj.get("Uid");
            String Action   = (String)inJObj.get("Action");

            String decResults;

            if(data!= null && data.startsWith("ENC=")){
                data =  data.substring(4, data.length());
            }

            if(Uid != null && Action != null && Uid.length() >= 30 && Action.length() >= 30){
                decResults = ISASSeedCBC.decrypt(data , Uid, Action );
            } else {
                decResults = ISASSeedCBC.decrypt(data);
            }
            output.put("Result", decResults);
        }
        return output;
    }


}
