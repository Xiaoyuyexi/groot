package com.groot.vo;

import java.io.Serializable;
import java.util.Date;

public class MonitorMessage implements Serializable{

	private static final long serialVersionUID = 287162721460370957L;
	
	public Date accessTime;
	public String accessUser;
	public String permission;
	public String controllerName;
	public Object[] objectArray;
	
	
	public Date getAccessTime() {
		return accessTime;
	}
	public void setAccessTime(Date accessTime) {
		this.accessTime = accessTime;
	}
	public String getAccessUser() {
		return accessUser;
	}
	public void setAccessUser(String accessUser) {
		this.accessUser = accessUser;
	}
	public Object[] getObjectArray() {
		return objectArray;
	}
	public void setObjectArray(Object[] objectArray) {
		this.objectArray = objectArray;
	}
	public String getControllerName() {
		return controllerName;
	}
	public void setControllerName(String controllerName) {
		this.controllerName = controllerName;
	}
	public String getPermission() {
		return permission;
	}
	public void setPermission(String permission) {
		this.permission = permission;
	}
	
	
}
