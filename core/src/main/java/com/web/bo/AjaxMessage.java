package com.web.bo;

import java.io.Serializable;

public class AjaxMessage implements Serializable{
	private String content;
	private String value;
	private String code;
	private String src;
	private String status;
	private String[] contentArray;
	private Object obj;
	private Object[] objArray;
	
	public String[] getContentArray() {
		return contentArray;
	}
	public void setContentArray(String[] contentArray) {
		this.contentArray = contentArray;
	}
	String title;
	boolean isSuccess;
	public AjaxMessage(){
		
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public AjaxMessage(String content,String title,boolean isSuccess){
		this.content=content;
		this.title=title;
		this.isSuccess=isSuccess;
	}
	public AjaxMessage(String value){
		setValue(value);
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public boolean getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	public Object[] getObjArray() {
		return objArray;
	}
	public void setObjArray(Object[] objArray) {
		this.objArray = objArray;
	}
	
}
