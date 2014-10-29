package com.web.utils.lang;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import sun.misc.BASE64Encoder;

public class StringUtils {
    private static String[] binaryArray =   
        {"0000","0001","0010","0011",  
        "0100","0101","0110","0111",  
        "1000","1001","1010","1011",  
        "1100","1101","1110","1111"};  
    
	public static String randomUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replace("-", "").toUpperCase();
	}
	public static String toAllUpperCase(String uuid) {
		StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < uuid.length(); i++) {
			char c = uuid.charAt(i);
			if (Character.isLowerCase(c)) {
				buffer.append(Character.toUpperCase(c));
			} else {
				buffer.append(c);
			}
		}
		return buffer.toString();
	}
	
	public static String getDurationMessage(long start ,long end){
		StringBuffer message = new StringBuffer();
		message.append("耗时:");
		message.append(org.apache.commons.lang.time.DurationFormatUtils.formatPeriod(start,end,"HH小时mm分钟ss秒SSS毫秒"));
		return message.toString();
	}
	/** 
     *  
     * @param str 
     * @return 转换为二进制字符串 
     */  
    public static String bytes2BinaryStr(byte[] bArray){  
        String outStr = "";  
        int pos = 0;  
        for(byte b:bArray){  
            //高四位  
            pos = (b&0xF0)>>4;  
            outStr+=binaryArray[pos];  
            //低四位  
            pos=b&0x0F;  
            outStr+=binaryArray[pos];  
        }  
        return outStr;  
    }
    /**将二进制转换成16进制
  　　* @param buf
  　　* @return
  　　*/
    public static String binaryToHexString(byte[] bytes) {
   		StringBuffer sb = new StringBuffer();
   		for (int i = 0; i < bytes.length; i++) {
   			String hex = Integer.toHexString(bytes[i] & 0xFF);
    	 	if (hex.length() == 1) {
    	 		hex = '0' + hex;
    	 	}
    	 	sb.append(hex.toUpperCase());
   		}
   		return sb.toString();
    }
    /**将16进制转换为二进制
  　　* @param hexStr
  　　* @return
  　　*/
	public static byte[] hexStringToBinary(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length()/2];
		for (int i = 0;i< hexStr.length()/2; i++) {
			int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);
			int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}
   
	/**
	 * 获取动态订单号
	 * @param orderSequence 后台获取序列号  获取序列号的DAO：terminalDAO.getSequenceOnMerchantOrderNo()
	 * @return
	 * add by xiejingjing 2013-05-21
	 */
	public static String getRandomOrderNumber(String orderSequence){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");  
		String nowdate = sdf.format(new Date());
		String orderNumber = nowdate.concat(orderSequence);
		return orderNumber;
	}
	
	/**
	 * 判断字符串为空 为空时返回true,否则返回false
	 * @param str
	 * @return
	 * add by xiejingjing 2013-05-22
	 */
	public static boolean isEmpty(String str){
		if("".equals(str) || str==null){
			return true;
		}
		return false;
	}
	
	/**
	 * 根据传入的对象替换oldStr中的占位符,eg:a=$object.name, return a=xxx;xxx为对象中name的值
	 * @param oldStr
	 * @param object
	 * @return
	 */
	public static String replace(String oldStr,Object object){
		try {
			return VelocityUtil.getContentByStringTemplate(oldStr,"replace",object,null);
		} catch (Exception e) {
			return oldStr;
		}
	}
	//十六进制转为字符
	public static String hexStringToString(String s) {
		byte[] baKeyword = new byte[s.length() / 2];
		for (int i = 0; i < baKeyword.length; i++) {
			try {
				baKeyword[i] = (byte) (0xff & Integer.parseInt(
						s.substring(i * 2, i * 2 + 2), 16));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			s = new String(baKeyword, "utf-8");// UTF-16le:Not
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return s;
	}

	/**
	 * 给某个日期加几天后的日期 eg:2013-06-28号+1天是 2013-06-29 ，+3天是2013-07-01
	 * @param date 日期
	 * @param dump 数字
	 * @return
	 * add by xiejingjing
	 */
	public static String getDateByAddSomeDay(Date date,int dump){
		Calendar ca=Calendar.getInstance();
        SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");  //构造任意格式
        String today = sm.format(date);
        String[] timeArray= today.split("-");
        ca.set(Calendar.YEAR,Integer.parseInt(timeArray[0]));
        ca.set(Calendar.MONTH, Integer.parseInt(timeArray[1])-1);
        ca.set(Calendar.DAY_OF_MONTH,Integer.parseInt(timeArray[2]));
        ca.add(Calendar.DAY_OF_MONTH, dump);
        String someDay = sm.format(ca.getTime());
        return someDay;
	}
	/**
	 * 纯数字校验
	 * @param str
	 * @return
	 * add by xiejingjing
	 */
	public static boolean isDigit(String str) {
		char[] charArray = str.toCharArray();
		boolean flag = true;
		for (int i = 0; i < charArray.length; i++) {
			if (!Character.isDigit(charArray[i])) { // 判断是否为数字
				flag = false;
				break;
			}
		}
		return flag;
	}
	
	/**
	 * 生成公钥
	 * @param pubkey
	 * @return
	 * add by yaoyuan
	 */
	public static String generatePublicKey(String pubkey) {
		BASE64Encoder base64en = new BASE64Encoder();
		String encode = base64en.encode(hexStringToBinary(pubkey));
		encode = "-----BEGIN RSA PUBLIC KEY-----" + encode + "-----END RSA PUBLIC KEY-----";
		if (encode.getBytes().length < 256) {
			encode = org.apache.commons.lang.StringUtils.rightPad(encode, 256, "0");
		}
		return encode;
	}
	
	/**
	 * 获取当前时间 精确到毫秒
	 */
	public static String getCurrentTime(){
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		 String currentTime = sdf.format(new Date());
		 return currentTime;
	}
	/** 
     * @功能: BCD码转为10进制串(阿拉伯数据) 
     * @参数: BCD码 
     * @结果: 10进制串 
     */  
    public static String bcd2Str(byte[] bytes) {  
        StringBuffer temp = new StringBuffer(bytes.length * 2);  
        for (int i = 0; i < bytes.length; i++) {  
            temp.append((byte) ((bytes[i] & 0xf0) >>> 4));  
            temp.append((byte) (bytes[i] & 0x0f));  
        }  
        return temp.toString().substring(0, 1).equalsIgnoreCase("0") ? temp  
                .toString().substring(1) : temp.toString();  
    }  
  
    /** 
     * @功能: 10进制串转为BCD码 
     * @参数: 10进制串 
     * @结果: BCD码 
     */  
    public static byte[] str2Bcd(String asc) {  
        int len = asc.length();  
        int mod = len % 2;  
        if (mod != 0) {  
            asc = "0" + asc;  
            len = asc.length();  
        }  
        byte abt[] = new byte[len];  
        if (len >= 2) {  
            len = len / 2;  
        }  
        byte bbt[] = new byte[len];  
        abt = asc.getBytes();  
        int j, k;  
        for (int p = 0; p < asc.length() / 2; p++) {  
            if ((abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {  
                j = abt[2 * p] - '0';  
            } else if ((abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {  
                j = abt[2 * p] - 'a' + 0x0a;  
            } else {  
                j = abt[2 * p] - 'A' + 0x0a;  
            }  
            if ((abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {  
                k = abt[2 * p + 1] - '0';  
            } else if ((abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {  
                k = abt[2 * p + 1] - 'a' + 0x0a;  
            } else {  
                k = abt[2 * p + 1] - 'A' + 0x0a;  
            }  
            int a = (j << 4) + k;  
            byte b = (byte) a;  
            bbt[p] = b;  
        }  
        return bbt;  
    }
    public static String[] split(String source ,int length){
    	return split(source ,length,null);
    }
    public static String[] split(String source ,int length,String pad){
    	String[] array = null;
    	if(source.length() < length){
    		array = (String[]) org.apache.commons.lang.ArrayUtils.add(array, source);
    	}
    	int count = source.length()/length;
    	for(int i = 0 ; i < count;i++){
    		String sub = source.substring(i*length, length+i*length);
    		array = (String[]) org.apache.commons.lang.ArrayUtils.add(array, sub);
    	}
    	if(source.length()%length != 0){
    		String last = source.substring(source.length()/length * length);
    		if(pad != null){
    			last = org.apache.commons.lang.StringUtils.rightPad(last, length, pad);
    		}
    		array = (String[]) org.apache.commons.lang.ArrayUtils.add(array, last);
    	}
    	return array; 
    }
    
    public static void main(String[] args) {
    	String mac="7B276164647244657461696C273A27E58C97E4BAACE5B882E6B5B7E6B780E58CBAE4BABAE5A4A7E58C97E8B7AF3333E58FB7E5A4A7E8A18CE59FBAE4B89AE5A4A7E58EA63137E5B182272C276167656E744964273A274131303030316552272C2762616E6B4163636F756E74273A2736323232303230323030303438353135303938272C2762616E6B4163636F756E744E616D65273A27E59586E9B8BFE6968C272C2762616E6B436974794964273A27272C2762616E6B44657461696C273A27E4B8ADE59BBDE5B7A5E59586E993B6E8A18CE58C97E4BAACE6B885E58D8EE59BADE694AFE8A18C272C2762616E6B4469737472696374273A27272C2762616E6B4E616D65273A27313032272C2762616E6B50726F76696E6365273A27272C27636974794964273A27313130313038272C27636C656172696E6742616E6B4163636F756E7454797065273A27313041272C27636F6F70657261746554797065273A27272C27636F72706F72617465273A27E59586E9B8BFE6968C272C2763726564656E7469616C436F6465273A27343132373234313939313037313530363330272C276469737472696374273A27313130313030272C27656D61696C273A277368616E67686F6E6762696E40686B72742E636E272C276C696E6B506572736F6E273A27E59586E9B8BFE6968C272C276C696E6B50686F6E65273A273135313130313939373736272C276D6363273A2737353132272C276D65726368616E74436E4E616D65273A27E59586E9B8BFE6968CE79A84E4BEBFE588A9E5BA97272C276D65726368616E74536F75726365273A27313044272C276D65726368616E7454797065273A27313041272C2770686F6E65273A273135313130313939373736272C2770726F76696E6365273A27313130303030272C2772617465436F6465273A273130303030303031272C277365727669636553636F70654E616D65273A27E6B1BDE8BDA6E38081E887AAE8A18CE8BDA62DE4BBA3E9A9BE2FE999AAE7BB832FE7A79FE8BDA6272C27736574746C654379636C65273A2731272C277465726D696E616C4D6F64656C273A27272C277465726D696E616C4E6F273A27272C277465726D696E616C4E756D273A2733272C27747261646554797065273A273136272C27776F726B4B6579273A2735363132383842363835373145453831453530363039303232424446323735434231364336363742413746363746423742344446383445313541463331323245334341464632453038363731394437453938393530453334323134413338393344443341443837363643323345373033272C277A6D6B273A273632384230453441383945363541454431443443464632373939364133463835277D";
    	System.out.println(mac.length());
    	System.out.println(split(mac,16,"0").length);
    	
    	/*String[] array = null;
    	array = (String[]) org.apache.commons.lang.ArrayUtils.add(array, "123");
    	array = (String[]) org.apache.commons.lang.ArrayUtils.add(array, "123");
    	
    	System.out.println(array.length);*/
	}
}
