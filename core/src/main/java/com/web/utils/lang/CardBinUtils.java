package com.web.utils.lang;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import com.web.bo.CardBO;

/**
 * 卡BIN工具类
 * @author admin
 *
 */
public class CardBinUtils {

	public final static int trackTwoLen = 48;
	
	public final static int trackTwoLen2 = 64;
	
	public final static int delta = 2;
	public final static int alpha = 16;
	
	public final static String fillChar = "F";
	/**
	 * 卡BIN校验
	 * @param magnetic
	 * @param cardBin
	 * @return
	 */
	public static CardBO validate(String magnetic,List<CardBO> cardBin){
		CardBO trackBO = new CardBO();
		if(StringUtils.isEmpty(magnetic) || magnetic.length()<trackTwoLen2){
			trackBO.returnValue="请提供合法卡磁!";
			return trackBO;
		}
		if(magnetic.length()==trackTwoLen2){
			String trackTwo = magnetic.substring(0, trackTwoLen2);
			trackBO = analyzeMagnetic(trackTwo,cardBin,"2");
			trackBO.setTrackTwo(trackTwo.replace(fillChar, ""));
		}else if(magnetic.length()>trackTwoLen2){
			String trackTwo = magnetic.substring(0, trackTwoLen);
			trackBO = analyzeMagnetic(trackTwo,cardBin,"2");
			trackBO.setTrackTwo(trackTwo.replace(fillChar, ""));
			if(!"00".equals(trackBO.returnCode)){
				String trackThree = magnetic.substring(trackTwoLen,magnetic.length());
				if(trackThree.length()%alpha!=0){
					trackBO.returnCode="301";
					trackBO.returnValue="三磁信息有误，非16倍数";
				}else{
					CardBO trackThreeBO = analyzeMagnetic(trackThree,cardBin,"3");
					trackThreeBO.setTrackThree(trackThree.replace(fillChar, ""));
					//三磁道失败，返回二磁道失败信息，只有成功的时候返回三磁道
					if("00".equals(trackThreeBO.returnCode)){
						trackBO = trackThreeBO;
					}
				}
			}
		}
		
		return trackBO;
	}
	
	/**
	 * 获取卡BIN信息
	 * @param cardNo
	 * @param cardBin
	 * @return
	 */
	public static CardBO getCardBinInfo(String cardNo,List<CardBO> cardBin){
		CardBO returnBO = new CardBO();
		returnBO.returnCode="10";
		//校验卡号是否为数字
		if(!NumberUtils.isNumber(cardNo)){
			returnBO.returnCode="20";
			return returnBO;
		}
		for(CardBO o:cardBin){
			String bin = cardNo.substring(0,o.getCardBinLen());
			//校验卡BIN
			if(o.getCardSign().equals(bin)){
				//卡号
				o.returnCode="00";
				o.cardNum=cardNo;
				return o;
			}
		}
		return returnBO;
	}
	
	
	/**
	 * 卡磁校验
	 * @param magnetic
	 * @param cardBin
	 * @return
	 */
	public static CardBO analyzeMagnetic(String magnetic,List<CardBO> cardBin,String track){
		Calendar now = Calendar.getInstance();
		now.set(Calendar.DAY_OF_MONTH, 1);
		String yearPrefix = String.valueOf(now.get(Calendar.YEAR)).substring(0, 2);
		Calendar effective = Calendar.getInstance();
		if(!CollectionUtils.isEmpty(cardBin)){ 
			for(CardBO o:cardBin){
				if(!o.getCardSignTrack().equals(track))continue;
				if(o.getStartingPos()-delta<0)continue;
				//卡BINtrack
				String bin = magnetic.substring(o.getStartingPos()-delta, o.getStartingPos()-delta+o.getCardBinLen());
				//校验卡BIN
				if(o.getCardSign().equals(bin)){
					//卡号
					String cardNum = magnetic.substring(o.getCardNoExcusion()-delta, o.getCardNoExcusion()-delta+o.getCardNoLength());
					System.out.println(cardNum);
					o.cardNum=cardNum;
					//校验卡号是否为数字
					if(NumberUtils.isNumber(cardNum)){
						//标记
						String flag = magnetic.substring(o.getCardNoExcusion()-delta+o.getCardNoLength(), o.getCardNoExcusion()-delta+o.getCardNoLength()+1);
						//后一位是否为D或者=
						if(flag.equalsIgnoreCase("d")||flag.equals("=")){
							//日期是否有效
							if(o.getEffectiveDatePos()!=null && o.getEffectiveDatePos()!=-1){
								String effectiveDate = magnetic.substring(o.getCardNoExcusion()-delta+o.getCardNoLength()+o.getEffectiveDatePos()+1,o.getCardNoExcusion()-delta+o.getCardNoLength()+o.getEffectiveDatePos()+1+4);
								o.effcetiveDate=effectiveDate;
								System.out.println(effectiveDate);
								effective.set(Integer.valueOf(yearPrefix+effectiveDate.substring(0, 2)),Integer.valueOf(effectiveDate.substring(2, 4))-1,1);
								if(now.after(effective)){
									o.returnCode="11";
									return o;
								}
							}
							o.returnCode="00";
							return o;
						}
					}
				}
			}
		}
		CardBO failBO = new CardBO();
		if("2".equals(track)){
			failBO.returnCode="10";
			String cardNum = magnetic.substring(0,16);
			String flag = magnetic.substring(16,17);
			failBO.setCardNum(cardNum);
			if(!flag.equalsIgnoreCase("d") && !flag.equals("=")){
				cardNum = magnetic.substring(0,19);
				failBO.setCardNum(cardNum);
			}
		}
		return failBO;
	}
	
	public static void main(String[] args) {
		//String magnetic="006210300011462731D49121204840320000000";
		//String magnetic="6210301192224670D19072010000083800000FFFFFFFFFFFFFFFFFFFFFFFFFFF";
		String magnetic=  "6226630200773983D49121200000000022670FFFFFFFFFFF996226630200773983D1561560500000000000013000000240000049120D000000000000D000000000000D000000000000000000FFFFFFFF";
		List<CardBO> result = new ArrayList<CardBO>();
		CardBO o1= new CardBO();
		o1.setCardSign("402791");
		o1.setStartingPos((short) 2);
		o1.setCardBinLen((short) 6);
		o1.setEffectiveDatePos((short)0);
		o1.setCardNoExcusion((short) 2);
		o1.setCardNoLength((short) 16);
		o1.setCardSignTrack("2");
		result.add(o1);
		
		CardBO o2= new CardBO();
		o2.setCardSign("622660");
		o2.setStartingPos((short) 2);
		o2.setCardBinLen((short) 6);
		o2.setCardSignTrack("2");
		o2.setCardNoExcusion((short) 2);
		o2.setCardNoLength((short) 16);
		o2.setEffectiveDatePos((short)-1);
		result.add(o2);
		CardBO bo = CardBinUtils.validate(magnetic, result);
		System.out.println(bo.getReturnCode());
		System.out.println(bo.cardNum);
		System.out.println(bo.getTrackTwo());
	}
}
