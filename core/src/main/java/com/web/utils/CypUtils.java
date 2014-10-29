package com.web.utils;

import com.hkrt.cyp.bean.RetData;
import com.hkrt.cyp01.service.ZftQPosChlCypSrv;
import com.hkrt.cyp01.service.ZftQPosCypSrv;
import com.web.bo.AjaxMessage;
import com.web.utils.lang.StringUtils;
/**
 * 加解密，PIN转加密
 * @author xiejingjing
 *
 */
public class CypUtils {
	
	/**
	 * 加解密
	 * @param keyStr
	 * @param Dps1Str
	 * @param Dps2Str
	 * @param Dps3Str
	 * @param SdataStr
	 * @return
	 */
	public static String QPos3Des(String address,int port,byte[] flag ,String keyStr,String Dps1Str,String Dps2Str,String Dps3Str,String SdataStr){
		String result = null;
		try{
			byte[] key = keyStr.getBytes();
			byte[] Dps1 = Dps1Str.getBytes();
			byte[] Dps2 = Dps2Str.getBytes();
			byte[] Dps3 = Dps3Str.getBytes();
			byte[] Sdata = SdataStr.getBytes();
			ZftQPosCypSrv z = new ZftQPosCypSrv(address, port);
			RetData retData = z.QPos3Des(flag, key, Dps1, Dps2, Dps3, Sdata);
			if(retData == null){
				return null;
			}
			int retCode = retData.getRetCode();
			byte[] retByte = retData.getRetBytes();
			
			if(retCode==1){
				result = new String(retByte);
			}
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return result;
	} 
	/**
	 * 转加密
	 * @param SzpkStr
	 * @param Dps1Str
	 * @param Dps2Str
	 * @param Dps3Str
	 * @param dzmkStr
	 * @param dzpkStr
	 * @param panStr
	 * @param pinStr
	 * @return
	 */
	public static String QPosDataChange(String address,int port,String SzpkStr,String Dps1Str,String Dps2Str,String Dps3Str,String dzmkStr,String dzpkStr,String panStr,String pinStr){
		String result = null;
		try{
			byte[] Szpk = SzpkStr.getBytes();
			byte[] Dps1 = Dps1Str.getBytes();
			byte[] Dps2 = Dps2Str.getBytes();
			byte[] Dps3 = Dps3Str.getBytes();
			byte[] dzmk = dzmkStr.getBytes();
			byte[] dzpk = dzpkStr.getBytes();
			byte[] pin = pinStr.getBytes();
			ZftQPosCypSrv z = new ZftQPosCypSrv(address, port);
			RetData retData = z.QPosDataChange(Szpk, Dps1, Dps2, Dps3, dzmk, dzpk, panStr, pin);
			if(retData == null){
				return null;
			}
			int retCode = retData.getRetCode();
			byte[] retByte = retData.getRetBytes();
			
			if(retCode==1){
				result = StringUtils.binaryToHexString(retByte);
			}
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return result;
		
	}
	/**
	 * 生成pinkey,mackey,tckey密文以及数字信封
	 * @param terminalNo
	 * @param pubKeyStr
	 * @param disKeyStr
	 * @param facKeyStr
	 * @return
	 */
	public static String QPosInitTMK(String address,int port,String terminalNo,String pubKeyStr,String disKeyStr,String facKeyStr){
		String result = null;
		try{
			byte[] pubKey = pubKeyStr.getBytes();
			byte[] disKey = disKeyStr.getBytes();
			byte[] facKey = facKeyStr.getBytes();
			ZftQPosCypSrv z = new ZftQPosCypSrv(address, port);
			RetData retData = z.QPosInitTMK(terminalNo, pubKey, disKey, facKey);
			if(retData == null){
				return null;
			}
			int retCode = retData.getRetCode();
			byte[] retByte = retData.getRetBytes();
			
			if(retCode==1){
				result = new String(retByte);
			}
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return result;
	}
	/**
	 * tck母密钥生成工作tck
	 * @param douleKeyStr
	 * @param tcKeyStr
	 * @param dps1Str
	 * @param dps2Str
	 * @param dps3Str
	 * @return
	 */
	public static String QPosGenTck(String address,int port,String douleKeyStr, String tcKeyStr, String dps1Str,String dps2Str,String dps3Str){
		String result = null;
		try{
			byte[] doubleKey = douleKeyStr.getBytes();
			byte[] tcKey = tcKeyStr.getBytes();
			byte[] dps1 = dps1Str.getBytes();
			byte[] dps2 = dps2Str.getBytes();
			byte[] dps3 = dps3Str.getBytes();
			ZftQPosCypSrv z = new ZftQPosCypSrv(address, port);
			RetData retData = z.QPosGenTck(doubleKey, tcKey, dps1, dps2, dps3);
			if(retData == null){
				return null;
			}
			int retCode = retData.getRetCode();
			byte[] retByte = retData.getRetBytes();
			
			if(retCode==1){
				result = new String(retByte);
			}
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return result;
	}
	/**
	 * 百富终端签到
	 * @param address
	 * @param port
	 * @param tmk
	 * @return
	 */
	public static String d180QPosChlSignIn(String address,int port,String tmk){
		String result = null;
		try{
			byte[] tmkByte = StringUtils.hexStringToBinary(tmk);
			ZftQPosChlCypSrv z = new ZftQPosChlCypSrv(address, port);
			RetData retData = z.QPosChl_SignIn_Ext(tmkByte);
			if(retData == null){
				return null;
			}
			int retCode = retData.getRetCode();
			byte[] retByte = retData.getRetBytes();
			
			if(retCode==1){
				result = StringUtils.binaryToHexString(retByte);
			}
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return result;
	}
	/**
	 * MAC校验
	 * @param flag
	 * @param keyStr
	 * @param Dps1Str
	 * @param Dps2Str
	 * @param Dps3Str
	 * @param sdataStr
	 * @param macStr
	 * @return
	 */
	public static int QPosMacChk(String address,int port,byte[] flag ,String keyStr,String Dps1Str,String Dps2Str,String Dps3Str,String sdataStr,String macStr){
		try{
			byte[] key = keyStr.getBytes();
			byte[] Dps1 = Dps1Str.getBytes();
			byte[] Dps2 = Dps2Str.getBytes();
			byte[] Dps3 = Dps3Str.getBytes();
			byte[] Sdata = sdataStr.getBytes();
			byte[] MAC = macStr.getBytes();
			ZftQPosCypSrv z = new ZftQPosCypSrv(address, port);
			RetData retData = z.QPosMacChk(flag, key, Dps1, Dps2, Dps3, Sdata, MAC);
			if(retData == null){
				return 99;
			}
			int retCode = retData.getRetCode();
			return retCode;
		}catch (Exception e) {
			e.printStackTrace();
			return 99;
		}
	} 
	
	/**
	 * 百富解卡磁解密
	 * @param address
	 * @param port
	 * @param flag
	 * @param tmk
	 * @param datakey
	 * @param sdataStr
	 * @return
	 */
	public static String d180QPos3Des(String address,int port,byte[] flag ,String tmk,String datakey,String sdataStr){
		String result = null;
		try{
			byte[] tmkByte = StringUtils.hexStringToBinary(tmk);
			byte[] datakeyByte = StringUtils.hexStringToBinary(datakey);
			byte[] sdataStrByte = StringUtils.hexStringToBinary(sdataStr);
			ZftQPosChlCypSrv z = new ZftQPosChlCypSrv(address, port);
			RetData retData = z.QPosChl_3Des_Ext(flag, tmkByte, datakeyByte, sdataStrByte);
			if(retData == null){
				return null;
			}
			int retCode = retData.getRetCode();
			byte[] retByte = retData.getRetBytes();
			
			if(retCode==1){
				result = StringUtils.binaryToHexString(retByte);
			}
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return result;
	} 
	
	/**
	 * 百富 转加密
	 * @param address
	 * @param port
	 * @param tmk
	 * @param pinKey
	 * @param cardPwd
	 * @return
	 */
	public static String d180QPosDataChange(String address,int port,String tmk,String pinKey,String cardPwd){
		String result = null;
		try{
			byte[] tmkByte = StringUtils.hexStringToBinary(tmk);
			byte[] pinKeyByte = StringUtils.hexStringToBinary(pinKey);
			byte[] cardPwdByte = StringUtils.hexStringToBinary(cardPwd);
			ZftQPosChlCypSrv z = new ZftQPosChlCypSrv(address, port);
			RetData retData = z.QPosChl_PinBlock_Ext(tmkByte, pinKeyByte, cardPwdByte);
			if(retData == null){
				return null;
			}
			int retCode = retData.getRetCode();
			byte[] retByte = retData.getRetBytes();
			
			if(retCode==1){
				result = StringUtils.binaryToHexString(retByte);
			}
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return result;
		
	}
	
	/**
	 * 百富MAC校验
	 * @param address
	 * @param port
	 * @param tmk
	 * @param macKey
	 * @param sdata
	 * @return
	 */
	public static int d180QPosMacChk(String address,int port,String tmk,String macKey,String sdata){
		try{
			byte[] tmkByte = StringUtils.hexStringToBinary(tmk);
			byte[] macKeyByte = StringUtils.hexStringToBinary(macKey);
			byte[] sdataByte = StringUtils.hexStringToBinary(sdata);
			ZftQPosChlCypSrv z = new ZftQPosChlCypSrv(address, port);
			RetData retData = z.QPosChl_Mac_Ext(tmkByte, macKeyByte, sdataByte);
			if(retData == null){
				return 99;
			}
			int retCode = retData.getRetCode();
			return retCode;
		}catch (Exception e) {
			e.printStackTrace();
			return 99;
		}
	} 
	
	
	/**
	 * IC芯片卡交易--加解密
	 * @param keyStr
	 * @param Dps1Str
	 * @param Dps2Str
	 * @param Dps3Str
	 * @param SdataStr
	 * @return
	 */
	public static AjaxMessage QPos3Des55F(String address,int port,byte[] flag ,String keyStr,String Dps1Str,String Dps2Str,String Dps3Str,String SdataStr,String field55Str){
		AjaxMessage ajx = new AjaxMessage();
		try{
			byte[] key = keyStr.getBytes();
			byte[] Dps1 = Dps1Str.getBytes();
			byte[] Dps2 = Dps2Str.getBytes();
			byte[] Dps3 = Dps3Str.getBytes();
			byte[] Sdata = SdataStr.getBytes();
			byte[] field55 = field55Str.getBytes();
			ZftQPosCypSrv z = new ZftQPosCypSrv(address, port);
			RetData retData = z.QPos3Des55F(flag, key, Dps1, Dps2, Dps3, Sdata, field55);
			if(retData == null){
				return null;
			}
			int retCode = retData.getRetCode();
			byte[] rSdata = (byte[])retData.getRetObj("Sdata");
			byte[] rField55 = (byte[])retData.getRetObj("Field55");
			if(retCode==1){
				String rSdataStr = new String(rSdata);
				String rField55Str = new String(rField55);
				ajx.setContent(rSdataStr);
				ajx.setValue(rField55Str);
			}else{
				return null;
			}
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return ajx;
	} 
	
	/**
	 * 百富IC芯片卡交易--加解密
	 * @param keyStr
	 * @param Dps1Str
	 * @param Dps2Str
	 * @param Dps3Str
	 * @param SdataStr
	 * @return
	 */
	public static AjaxMessage d180QPos3Des55F(String address,int port,byte[] flag ,String tmk,String datakey,String sdataStr,String field55Str){
		AjaxMessage ajx = new AjaxMessage();
		try{
			byte[] tmkByte = StringUtils.hexStringToBinary(tmk);
			byte[] datakeyByte = StringUtils.hexStringToBinary(datakey);
			byte[] sdataStrByte = StringUtils.hexStringToBinary(sdataStr);
			byte[] field55 =  StringUtils.hexStringToBinary(field55Str);
			ZftQPosChlCypSrv z = new ZftQPosChlCypSrv(address, port);
			RetData retData = z.QPosChl_3Des_55F_Ext(flag, tmkByte, datakeyByte, sdataStrByte,field55);
			if(retData == null){
				return null;
			}
			int retCode = retData.getRetCode();
			byte[] rSdata = (byte[])retData.getRetObj("Sdata");
			byte[] rField55 = (byte[])retData.getRetObj("Field55");
			if(retCode==1){
				String rSdataStr = StringUtils.binaryToHexString(rSdata);
				String rField55Str = StringUtils.binaryToHexString(rField55);
				ajx.setContent(rSdataStr);
				ajx.setValue(rField55Str);
			}else{
				return null;
			}
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return ajx;
	} 
}
