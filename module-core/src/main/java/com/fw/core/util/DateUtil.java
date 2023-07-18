package com.fw.core.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DateUtil {

	/**
	 * 현재 날짜를 요청받은 형식으로 변환
	 * 
	 * @param pattern 패턴
	 * @return String
	 */
	public static String getStrNow(String pattern) {
		return LocalDate.now().format(DateTimeFormatter.ofPattern(pattern));
	}

	/**
	 * 날짜 비교 함수 s1 > s2 인지 확인
	 * 
	 * @param s1      비교 할 날짜 1
	 * @param s2      비교 할 날짜 2
	 * @param pattern 패턴
	 * @return boolean
	 */
	public static boolean isAfter(String s1, String s2, String pattern) {
		LocalDate ld1 = LocalDate.parse(s1, DateTimeFormatter.ofPattern(pattern));
		LocalDate ld2 = LocalDate.parse(s2, DateTimeFormatter.ofPattern(pattern));
		return ld1.isAfter(ld2);
	}

	/**
	 * 현재 일시를 요청받은 형식으로 변환
	 * 
	 * @param pattern 패턴
	 * @return String
	 */
	public static String getDtStrNow(String pattern) {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern));
	}

	/**
	 * 일시 비교 함수 s1 > s2 인지 확인
	 * 
	 * @param s1      비교 할 일시 1
	 * @param s2      비교 할 일시 2
	 * @param pattern 패턴
	 * @return boolean
	 */
	public static boolean isDtAfter(String s1, String s2, String pattern) {
		LocalDateTime ld1 = LocalDateTime.parse(s1, DateTimeFormatter.ofPattern(pattern));
		LocalDateTime ld2 = LocalDateTime.parse(s2, DateTimeFormatter.ofPattern(pattern));
		return ld1.isAfter(ld2);
	}

	/**
	 * 나이를 받아서 yyyyMMdd 으로 변환
	 * @param age 나이
	 * 
	 */
	public static String getAgeDateStr(int age) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return LocalDateTime.now().minusYears(age).format(formatter);
	}

}
