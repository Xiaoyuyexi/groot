package com.web.bo;

import java.io.File;
import java.io.Serializable;

/**
 * 邮件信息类
 * @author YaoYuan
 *
 */
public class Mail implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private String senter;//发件邮箱
	private String senterHost;//发件邮箱host,例：smtp.126.com
	private String senterUserName;//发件邮箱用户名
	private String senterPasword;//发件邮箱密码
	private String subject;//主题
	private String addressee;//收件邮箱
	private String message;//内容
	private File[] fileArray;//附件
	
	public Mail() {
	}
		
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 获取邮件主题
	 * @return
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * 设置邮件主题
	 * @param subject
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	/**
	 * 获取收件人地址
	 * @return
	 */
	public String getAddressee() {
		return addressee;
	}
	/**
	 * 设置收件人地址
	 * @param addressee
	 */
	public void setAddressee(String addressee) {
		this.addressee = addressee;
	}
	/**
	 * 获取邮件内容
	 * @return
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * 设置邮件内容
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	public File[] getFileArray() {
		return fileArray;
	}
	/**
	 * 设置邮件附件
	 * @param message
	 */
	public void setFileArray(File[] fileArray) {
		this.fileArray = fileArray;
	}

	public String getSenter() {
		return senter;
	}

	public void setSenter(String senter) {
		this.senter = senter;
	}

	public String getSenterHost() {
		return senterHost;
	}

	public void setSenterHost(String senterHost) {
		this.senterHost = senterHost;
	}

	public String getSenterUserName() {
		return senterUserName;
	}

	public void setSenterUserName(String senterUserName) {
		this.senterUserName = senterUserName;
	}

	public String getSenterPasword() {
		return senterPasword;
	}

	public void setSenterPasword(String senterPasword) {
		this.senterPasword = senterPasword;
	}



	
	
}
