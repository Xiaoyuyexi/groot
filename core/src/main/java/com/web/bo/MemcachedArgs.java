package com.web.bo;

import java.io.Serializable;

/**
 * 缓存配置参数
 * @author admin
 *
 */
public class MemcachedArgs implements Serializable{

	private static final long serialVersionUID = 1L;
	public String connectionPoolSize=""; 
	public String failureMode=""; 
	public String host=""; 
	public String port="";
	public String ChlCd="";
}
