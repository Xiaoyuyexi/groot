package com.web.utils.sms;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.web.bo.SMS;
import com.web.bo.ServerArgs;
import com.web.utils.http.HttpUtils;
import com.web.utils.lang.SecurityUtils;

public class SmsUtil {

	/**
	 * 发送短信发送请求
	 * @throws IOException 
	 * @throws UnsupportedEncodingException 
	 * @throws ClientProtocolException 
	 */
	public static SMS sendSmsRequest(SMS sms,ServerArgs args) throws ClientProtocolException, UnsupportedEncodingException, IOException {
		StringBuilder sb = new StringBuilder();
		sb.append(args.ip);//Global.SMS_ADDR;resource.getString("sms.addr")
		sb.append("Phone=");
		sb.append(sms.getPhone());
		sb.append("&Msg_txt=");
		sb.append(URLEncoder.encode(sms.getMsgTxt(),"UTF-8"));//Global.SMS_MERMSG
		sb.append("&Merno=");
		sb.append(args.port);//Global.SMS_MERCODEresource.getString("sms.mercode")
		sb.append("&Password=");
		sb.append(args.pw);//Global.SMS_MERPASSresource.getString("sms.merpass")
		String signVal = sms.getPhone() + "|" + sms.getMsgTxt() + "|" + args.port + "|" + args.pw + "|" + args.name + "|"; 
		//Global.SMS_MERMSG Global.SMS_MERCODE Global.SMS_MERPASS Global.SMS_MERSIGN
		sb.append("&SignVal=");
		sb.append(SecurityUtils.EncoderByMd5(signVal, true));
		String result="";
		result = HttpUtils.httpGet(sb.toString(),0,15000);
		Type listType = new TypeToken<List<SMS>>() {}.getType();
		List<SMS> adapters = new Gson().fromJson(result, listType);
		return adapters.get(0);
	}
	
	/*public static void main(String[] args){
		SMS tempSms = new SMS();
		tempSms.setMsgTxt("“test777” 您好,您的初始密码为 yaK4yF,您可根据 代理商简称及密码 登录代理商系统,注意登录时密码区分大小写,地址为:http://192.168.0.141:8080/mss/ 如有问题,请联系客服,客服电话为:400-668-6689");
		tempSms.setPhone("15810947411");
		try {
			sendSmsRequest(tempSms);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
}
