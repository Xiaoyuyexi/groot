package com.web.utils.io;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

public class PrintUtil {
	public String contentType;
	public HttpServletResponse response;
	public PrintUtil(HttpServletResponse response,String contentType){
		this.response=response;
		this.response.setContentType(contentType);
	}
	public PrintUtil(HttpServletResponse response){
		this.response=response;
	}
	public void print(Object msg){
		try {
			if(null != response){
				response.getWriter().print(msg);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
