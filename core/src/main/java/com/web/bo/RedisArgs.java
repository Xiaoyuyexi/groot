package com.web.bo;

import java.io.Serializable;

/**
 * redis配置参数
 * @author admin
 *
 */
public class RedisArgs implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public String maxActive="";
	public String maxIdle="";
	public String maxWait="";
	public boolean testOnBorrow;
	public boolean testOnReturn;
	public String ip="";
	public String port="";
}
