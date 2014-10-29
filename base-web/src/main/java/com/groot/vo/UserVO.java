package com.groot.vo;

import com.groot.module.domain.GrootSystemUser;

public class UserVO extends GrootSystemUser{

	private String userPassword;
	private String userId;
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
