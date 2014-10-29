package com.web.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

/**
 * excel utils
 * @author DapingZhao
 *
 */
public class ExcelUtils {

	/**
	 * read by file and path
	 * @param path
	 */
	public static List<String> read(String path){
		try {
			return read(new FileInputStream(path));
		} catch (IOException e) {
			return new ArrayList<String>();
		}
	}
	
	/**
	 * read by input stream
	 * @param inputStream
	 */
	public static List<String> read(InputStream inputStream){
		return read(inputStream,false);
	}
	
	/**
	 * read by input stream
	 * @param inputStream
	 */
	public static List<String> read(InputStream inputStream,boolean skipHead){
		HSSFWorkbook workbook03=null;
		List<String> list = new ArrayList<String>();
		try {
			workbook03 = new HSSFWorkbook(inputStream);
			HSSFSheet sheet03 = workbook03.getSheetAt(0);
			int start = 1;
			if(skipHead){
				start = 2;
			}
			for (int r = start; r < sheet03.getLastRowNum()+1; r++) {
				HSSFRow row = sheet03.getRow(r);
				String cellContent = "";
				
				for (Iterator<Cell> cit = row.cellIterator(); cit.hasNext();) {
					Cell cell = (Cell)cit.next();
					cellContent = cellContent + cell.getStringCellValue()+",";
				}
				list.add(cellContent);
			}
		} catch (IOException e) {
			return new ArrayList<String>();
		}
		return list;
	}
}
