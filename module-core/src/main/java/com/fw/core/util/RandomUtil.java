package com.fw.core.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Random;

@Slf4j
@Component
public class RandomUtil {

    /**
     * N자리 난수 생성
     */
    public static String createRandomText(int n){
        Random random = new Random();
        double maxVal = Math.pow(10, n);
        int max = ((int) Math.floor(maxVal)) - 1;
        return String.format("%0" + n +"d", random.nextInt(max));
    }

    /**
     * N자리 문자 생성
     */
    public static String createRandomStr(int n){
        char[] tmp = new char[n];
        for(int i = 0; i < tmp.length; i++) {
            int div = (int) Math.floor( Math.random() * 2 );
            if(div == 0) {
                tmp[i] = (char) (Math.random() * 10 + '0') ;
            } else {
                tmp[i] = (char) (Math.random() * 26 + 'A') ;
            }
        }
        return new String(tmp);
    }

}
