package com.web.utils.lang;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * 閲戦宸ュ叿绫�
 * @author ZhaoDaping
 *
 */
public class MoneyUtils extends DecimalFormat{

	public String normalFormat(String pattern,Object amount){
		applyPattern(pattern);
		return format(amount);
	}
	
	public String halfRoundFormat(String pattern,Object amount){
		applyPattern(pattern);
		return format(amount);
	}
	
	public String normalCommaFormat(Object amount){
		applyPattern("##,##0.00");
		return format(amount);
	}
	
	public String normalFormat(Object amount){
		applyPattern("0.00");
		return format(amount);
	}
	
	public String halfRoundCommaFormat(Object amount){
		applyPattern("##,##0.00");
		setRoundingMode(RoundingMode.HALF_UP); 
		return format(amount);
	}
	
	public String halfRoundFormat(Object amount){
		applyPattern("0.00");
		setRoundingMode(RoundingMode.HALF_UP); 
		return format(amount);
	}
	public  static String moneyToZeroFormat(String money){
		  
		String money_ = null;
		if(money.contains(".")){
				
				String[] moneyStr = money.split("\\.");
				System.out.println("moneyStr length="+moneyStr.length);
				String centMoney = moneyStr[1];
				if(centMoney.length()<2){
					centMoney = centMoney+"0";
				}
				money_ = moneyStr[0] + centMoney;
		}else{
				money_ = money+"00";
		 }
		  
		  int len = money_.length();
		  
		  int left = 12-len;
		  StringBuffer str = new StringBuffer();
		  for(int i=0;i<left;i++){
			  str.append("0");
		  }
		  
		  String s = str + money_;
		  return s;

	  }
}
