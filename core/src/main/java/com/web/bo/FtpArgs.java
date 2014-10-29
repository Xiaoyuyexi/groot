package com.web.bo;

import java.io.Serializable;

/**
 * ftp配置参数
 * @author admin
 *
 */
public class FtpArgs implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	public String host="";
	public String port="";
	public String user="";
	public String pw="";
	public String bufferSize="";
	public String path="";
	public String temp="";
}
