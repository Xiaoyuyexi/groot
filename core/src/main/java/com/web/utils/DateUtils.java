package com.web.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	/**   
	 * 得到本月的第一天   
	 * @return   
	 */    
	public static Date  getMonthFirstDay() {    
		
	    Calendar calendar = Calendar.getInstance();     
	    calendar.set(Calendar.DAY_OF_MONTH, calendar     
	            .getActualMinimum(Calendar.DAY_OF_MONTH));     
	    return  calendar.getTime();     
	}     
	    
	/**   
	 * 得到本月的最后一天   
	 *    
	 * @return   
	 */    
	public static Date getMonthLastDay() {     
	    Calendar calendar = Calendar.getInstance();     
	    calendar.set(Calendar.DAY_OF_MONTH, calendar     
	            .getActualMaximum(Calendar.DAY_OF_MONTH));     
	    return calendar.getTime();     
	}   
}
