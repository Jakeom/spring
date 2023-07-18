package com.fw.fo.system.config.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Slf4j
@Setter
@Getter
@RequiredArgsConstructor
@Component
public class AES256Provider {

    /**
     * AES 암호화를 지원한다. 암호화를 위한 키는 32바이트여야 한다.
     */
    public static class Aes {
        /**
         * 문자열을 암호화 한다.
         * 
         * @param key          암호화할 키
         * @param strToEncrypt 암호화할 문자열
         * @return
         *         암호화된 문자열을 바이트 배열로 반환한다.
         * @throws Exception
         */

        public byte[] encryptToBytes(String key, String strToEncrypt) throws Exception {
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return cipher.doFinal(strToEncrypt.getBytes("UTF-8"));

        }// :

        /**
         * 문자열을 암호화 한다.
         * 
         * @param key          암호화할 키
         * @param strToEncrypt 암호화할 문자열
         * @return
         *         암호화된 문자열을 base64로 encode해서 반환한다
         * @throws Exception
         */
        public String encrypt(String key, String strToEncrypt) throws Exception {
            String encryptedStr = Base64.getEncoder().encodeToString(encryptToBytes(key, strToEncrypt));
            return encryptedStr;
        }// :

        /**
         * 암호화된 바이트 배열을 복호화 한다.
         * 
         * @param key            암호화 키
         * @param bytesToDecrypt 복호화할 바이트 배열
         * @return
         *         복호화된 바이트 배열
         * @throws Exception
         */
        public byte[] decryptToBytes(String key, byte[] bytesToDecrypt) throws Exception {
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return cipher.doFinal(bytesToDecrypt);
        }// :

        /**
         * Base 64로 감싸진 문자열을 복호화 한다.
         * 
         * @param key          암호화 키
         * @param strToDecrypt 복호화할 문자열
         * @return
         *         복호화된 문자열
         * @throws Exception
         */
        public String decrypt(String key, String strToDecrypt) throws Exception {
            byte[] bytesToDecrypt = Base64.getDecoder().decode(strToDecrypt);
            String decreptedStr = new String(decryptToBytes(key, bytesToDecrypt));
            return decreptedStr;
        }
    }

    private static Aes aes = new Aes();
    private static String jwtSecret;
  
    @Value("${jwt.secret.key}")
    private void setJwtSecret(String jwtSecret) {
        AES256Provider.jwtSecret = jwtSecret;
    }

    /** Aes 인스턴스를 반환한다. */
    public static Aes getAES() {
        return aes;
    }// :

    public String encryptStr(String targetStr) {
        String result = "";
        Aes aes = AES256Provider.getAES();
        String key = AES256Provider.jwtSecret.substring(0,32);

        try {
            result = aes.encrypt(key, targetStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public String decryptStr(String encryptedStr) {
        String result = "";
        Aes aes = AES256Provider.getAES();
        String key = AES256Provider.jwtSecret.substring(0,32);
        
        try {
            result = aes.decrypt(key, encryptedStr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}