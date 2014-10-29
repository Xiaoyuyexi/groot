package com.web.utils;

import com.web.bo.ServerArgs;




public class InitUtils {
	/*//private static ResourceBundle resource = ResourceBundle.getBundle("init");
	private static String requestType = "";//resource.getString("cts.requestType");
	private static String realmName  = "";// resource.getString("cts.realmName");
	private static String port  = "";// resource.getString("cts.port");
	private static String name  = "";// resource.getString("cts.name");
	private static String mps_requestType  = "";// resource.getString("mps.requestType");
	private static String mps_realmName  = "";// resource.getString("mps.realmName");
	private static String mps_port  = "";// resource.getString("mps.port");
	private static String mps_name  = "";// resource.getString("mps.name");
	private static String mts_requestType  = "";// resource.getString("mts.requestType");
	private static String mts_realmName  = "";// resource.getString("mts.realmName");
	private static String mts_port  = "";// resource.getString("mts.port");
	private static String mts_name  = "";// resource.getString("mts.name");
*/	
	public static ServerArgs serverArgs=null;
	
	public static void init(ServerArgs args){
		if(null == serverArgs){
			serverArgs = args;
		}
	}
	
	/**
	 * 公共方法抽取
	 * @param url
	 * @return
	 */
	public static String getMpsUrl(){
		String trueUrl = serverArgs.mpsRequestType.concat(serverArgs.mpsRealmName).concat(serverArgs.mpsPort).concat(serverArgs.mps_name);
		return trueUrl;
	}
	/**
	 * 公共方法抽取
	 * @param url
	 * @return
	 */
	public static String getCtsUrl(){
		String trueUrl = serverArgs.ctsRequestType.concat(serverArgs.ctsRealmName).concat(serverArgs.ctsPort).concat(serverArgs.ctsName);
		return trueUrl;
	}
	/**
	 * 公共方法抽取
	 * @param url
	 * @return
	 */
	public static String getMtsUrl(){
		String trueUrl = serverArgs.mtsRequestType.concat(serverArgs.mtsRealmName).concat(serverArgs.mtsPort).concat(serverArgs.mtsName);
		return trueUrl;
	}
	public static void main(String[] args) {
		System.out.println(InitUtils.getMpsUrl());
	}
}
