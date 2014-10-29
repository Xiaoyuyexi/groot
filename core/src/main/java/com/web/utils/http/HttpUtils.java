package com.web.utils.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;

public class HttpUtils {
	public static String httpGet(String url,int connectionTimeout,int soTimeout) throws ClientProtocolException, UnsupportedEncodingException, IOException{
		String responseContent = null;
		DefaultHttpClient httpClient=new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		if(0 == connectionTimeout){
			connectionTimeout = 60000;
		}
		if(0 == soTimeout){
			soTimeout = 60000;
		}
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, connectionTimeout);
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, soTimeout);
		httpGet.addHeader("Content","text/html,charset=utf-8");	
        HttpResponse response;
			response = httpClient.execute(httpGet);
			responseContent =new String(EntityUtils.toByteArray(response.getEntity()),"UTF-8");
//			responseContent = EntityUtils.toString(response.getEntity());
			 httpGet.abort();
		     httpClient.getConnectionManager().shutdown();
        return responseContent;
	}
	public static String httpGet(String url) throws ClientProtocolException, UnsupportedEncodingException, IOException{
        return httpGet(url,0,0);
	}
	public static String httpsGet(String url){
		return httpsGet(url,0,0);
	}
	public static String httpsGet(String url,int connectionTimeout,int soTimeout){
		String responseContent = null;
		DefaultHttpClient httpClient=new DefaultHttpClient();
		if(0 == connectionTimeout){
			connectionTimeout = 60000;
		}
		if(0 == soTimeout){
			soTimeout = 60000;
		}
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, connectionTimeout);
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, soTimeout);
		HttpGet httpGet = new HttpGet(url);
//		httpClient.getParams().setParameter("content", "text/html; charset=utf-8");
		httpGet.addHeader("Content","text/html,charset=utf-8");
        HttpResponse response;
		try {
			response = httpClient.execute(httpGet);
			responseContent =new String(EntityUtils.toByteArray(response.getEntity()),"UTF-8");
//			responseContent = EntityUtils.toString(response.getEntity(),"UTF-8");
			 httpGet.abort();
		     httpClient.getConnectionManager().shutdown();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        return responseContent;
	}
	public static <T> T httpGet(String url,Class<T> classOfT){
		return httpGet(url,classOfT,0,0);
	}
	
	public static <T> T httpGet(String url,Class<T> classOfT,int connectionTimeout,int soTimeout){
		String responseContent = null;
		DefaultHttpClient httpClient=new DefaultHttpClient();
		if(0 == connectionTimeout){
			connectionTimeout = 60000;
		}
		if(0 == soTimeout){
			soTimeout = 60000;
		}
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, connectionTimeout);
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, soTimeout);
		HttpGet httpGet = new HttpGet(url);
        HttpResponse response;
		try {
			response = httpClient.execute(httpGet);
			responseContent = EntityUtils.toString(response.getEntity());
			 httpGet.abort();
		     httpClient.getConnectionManager().shutdown();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Gson gson = new Gson();
        return gson.fromJson(responseContent,classOfT);
	}
	/**
	 * 将对象转成&连接的参数
	 * @param object
	 * @return
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public static String  objectToUri(Object object) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		Map map = BeanUtils.describe(object);
		Object[] params =null;
		 Iterator entries = map.entrySet().iterator();
	        while (entries.hasNext()) {
	        	java.util.Map.Entry entry = (java.util.Map.Entry)entries.next();
	        	if(entry.getValue() != null){
	        		if("class".equals(entry.getKey())){
	        			continue;
	        		}
	        		//params = ArrayUtils.add(params, entry.getKey()+"="+entry.getValue());
	        		params = ArrayUtils.add(params, entry.getKey()+"="+URLEncoder.encode((String) entry.getValue()));
	        	}
	        }
	    return StringUtils.join(params,"&");
	}
	/**
	 * 将url中的参数封装到对象中
	 * @param url
	 * @param object
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static void uriToObject(String url,Object object) throws IllegalAccessException, InvocationTargetException{
		String urlReplace = url.replace("?", "\\?");
		String[] str1 = urlReplace.split("\\?");
		String[] str2 = str1[1].split("&");
		Map<String, Object> map = new HashMap<String, Object>();
		for (int i = 0; i < str2.length; i++) {
			String[] str3 = str2[i].split("=");
			if(str3.length==2){
				map.put(str3[0], str3[1]);
			}else{
				map.put(str3[0], null);
			}
			
		}
		BeanUtils.populate(object, map);
	}
	
	/**
	 * @param args
	 *//*
	public static void main(String[] args) {
		String url ="http://www.taobao.com/index.php?cardTrack=00&cardPwd=11&terminalNo=&tradeTime=22";
		ConsumerBO bo = new ConsumerBO();
		try {
			uriToObject(url, bo);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("cardTrack="+bo.getCardTrack()+" -- cardPwd="+bo.getCardPwd()+" -- terminalNo= "+bo.getTerminalNo()+" --tradeTime ="+bo.getTradeTime());
	}*/
}
