package com.web.bo;

import java.io.Serializable;

public class SMS implements Serializable{

	private static final long serialVersionUID = 1L;
	private String id;
	private String PHONE;
	private String MERNO;
	private String RSPCOD;
	private String MSG_TXT;
	private String RSPMSG;

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPhone() {
		return PHONE;
	}

	public void setPhone(String phone) {
		this.PHONE = phone;
	}

	public String getMerno() {
		return MERNO;
	}

	public void setMerno(String merno) {
		this.MERNO = merno;
	}

	public String getRepcod() {
		return RSPCOD;
	}

	public void setRepcod(String repcod) {
		this.RSPCOD = repcod;
	}

	public String getMsgTxt() {
		return MSG_TXT;
	}

	public void setMsgTxt(String msgTxt) {
		this.MSG_TXT = msgTxt;
	}

	public String getRepmsg() {
		return RSPMSG;
	}

	public void setRepmsg(String repmsg) {
		this.RSPMSG = repmsg;
	}

}
