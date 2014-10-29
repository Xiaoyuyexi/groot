package com.web.utils;


public class LookupUtils {

	/**
	 * 获取字典中的integerKey  首次插入字典传0
	 * 非首次插入字典传最大的integerKey
	 * @param key
	 * @return
	 */
	public static int getIntegerKey(int key)throws Exception{
		if(key == 0){
			return 1;
		}
		if(key>1){
			if(key%2!=0){
				throw new Exception("key不合法");
			}
		}
		
		return 2*key;
	}
	
	public static String getCode_number(String code) throws Exception{
		if(code==null||"".equals(code)){
			return "10A";
		}
		if(code.length()!=3){
			throw new Exception("code不合法");
		}
		if(code.equals("99Z")){
			throw new Exception("code不合法");
		}
		String code_num = code.substring(0,2);
		int num;
		try {
			num = Integer.parseInt(code_num);
		} catch (Exception e) {
			throw new Exception("code不合法");
		}
		if(num==99){
			return getCode_letters(code);
		}
		return (num+1)+code.substring(2,3);
	}
	public static String getCode_letters(String code) throws Exception{
		
		if(code==null||"".equals(code)){
			return "10A";
		}
		if(code.length()!=3){
			throw new Exception("code不合法");
		}
		if(code.equals("99Z")){
			throw new Exception("code不合法");
		}
		String let = code.substring(2,3);
		if(let.equals("Z")){
			return getCode_number(code);
		}
		String [] letters ={"A","B","C","D","E","F","G","H","I","G","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
		for (int i = 0; i < letters.length; i++) {
			if(let.equals(letters[i])){
				return code.substring(0,2)+letters[i+1];
			}
		}
		throw new Exception("code不合法");
	}
}
