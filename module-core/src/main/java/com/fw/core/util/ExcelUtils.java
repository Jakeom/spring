package com.fw.core.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

public class ExcelUtils {

	/**
	 * xlsx 엑셀 스타일 적용
	 */
	public static XSSFCell createXSSFCellStyle(XSSFRow row, int col, Font font, short bgColor, String cellValue) {
		VerticalAlignment vAlign = VerticalAlignment.CENTER;
		HorizontalAlignment hAlign = HorizontalAlignment.CENTER;

		XSSFSheet sheet = row.getSheet();
		XSSFWorkbook wb = sheet.getWorkbook();
		XSSFCellStyle cs = wb.createCellStyle();

		//정렬
		cs.setAlignment(hAlign);
		cs.setVerticalAlignment(vAlign);

		//테두리 설정
		cs.setBorderTop(BorderStyle.THIN);
		cs.setBorderRight(BorderStyle.THIN);
		cs.setBorderBottom(BorderStyle.THIN);
		cs.setBorderLeft(BorderStyle.THIN);

		//글꼴 설정
		cs.setFont(font);

		//배경색 설정
		if (bgColor > 0) {
			cs.setFillForegroundColor(bgColor);
			cs.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		}

		//셀 생성
		XSSFCell cell = row.createCell(col);
		cell.setCellStyle(cs);
		cell.setCellValue(cellValue);

		return cell;
	}

	/**
	 * Default value cell
	 */
	public static XSSFCell createDefaultValueCellStyle(XSSFRow row, int col, XSSFFont font, String cellValue) {
		return createXSSFCellStyle(row, col, font, IndexedColors.WHITE.getIndex(), cellValue);
	}

	/**
	 * Default header cell
	 */
	public static XSSFCell createDefaultHeaderCellStyle(XSSFRow row, int col, XSSFFont font, String cellValue) {
		return createXSSFCellStyle(row, col, font, IndexedColors.YELLOW.getIndex(), cellValue);
	}

}
