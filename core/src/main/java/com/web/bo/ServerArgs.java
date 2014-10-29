package com.web.bo;

import java.io.Serializable;

/**
 * 远程服务访问配置参数
 * @author admin
 *
 */
public class ServerArgs implements Serializable{

	private static final long serialVersionUID = 1L;
	public String ctsRequestType="";
	public String ctsRealmName="";
	public String ctsPort="";
	public String ctsName="";
	
	public String mpsRequestType="";
	public String mpsRealmName="";
	public String mpsPort="";
	public String mps_name="";
	
	public String mtsRequestType="";
	public String mtsRealmName="";
	public String mtsPort="";
	public String mtsName="";
	
	public String mssRequestType="";
	public String mssRealmName="";
	public String mssPort="";
	public String mssName="";
	
	public String mmsRequestType="";
	public String mmsRealmName="";
	public String mmsPort="";
	public String mmsName="";
	
	public String ip="";
	public String port="";
	public String pw="";
	public String name="";
	public String path="";
	public String note="";
}
