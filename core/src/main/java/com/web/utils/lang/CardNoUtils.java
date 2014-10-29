package com.web.utils.lang;

import org.apache.commons.lang.StringUtils;
/**
 * 处理卡号中间号码隐藏
 * @author YaoYuan
 *
 */
public class CardNoUtils {
	
	public static String hideCardNo(String cardNo) {
		if(StringUtils.isBlank(cardNo)){
			return "";
		}
		int size = cardNo.length();
		int front = 0;
		int behind = 0;
		if(size <= 10){
			front = 4;
			behind = 4;
		}else{
			front = 6;
			behind = 4;
		}
		StringBuffer buf = new StringBuffer();
		buf.append(cardNo.substring(0, front));
		for(int i=0; i<6; i++){
			buf.append("*");
		}
		buf.append(cardNo.substring((size - behind), size));
		return buf.toString();
		
//		String hideCard = "";
//		if (StringUtils.isEmpty(cardNo)) {
//			return "";
//		}
//		if (cardNo.length() <= 10) {
//			return cardNo;
//		}
//		hideCard = cardNo.substring(0, 6);
//		for (int i = 0; i < cardNo.length() - 10; i++) {
//			hideCard += "*";
//		}
//		hideCard += cardNo.substring(cardNo.length() - 4, cardNo.length());
//		return hideCard;
	}

}
