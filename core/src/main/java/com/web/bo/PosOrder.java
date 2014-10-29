package com.web.bo;

import java.io.File;

public class PosOrder {
	private String trxAmt; // 交易金额
	private String trxTime; // 交易时间
	private String InvoicePrintName; // 交易商户
	private String bankName;  // 发卡行
	private String cardNo;    // 卡号
	private String orderType; // 交易类型
	private String orderNo;   // 交易编号
	private File signImage;   // 签名图片
	private String merchantNo;
	private String terminalNo;
	private String authorizationCode;//授权码
	
	public String getTrxAmt() {
		return trxAmt;
	}
	public void setTrxAmt(String trxAmt) {
		this.trxAmt = trxAmt;
	}
	public String getTrxTime() {
		return trxTime;
	}
	public void setTrxTime(String trxTime) {
		this.trxTime = trxTime;
	}
	public String getInvoicePrintName() {
		return InvoicePrintName;
	}
	public void setInvoicePrintName(String invoicePrintName) {
		InvoicePrintName = invoicePrintName;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public File getSignImage() {
		return signImage;
	}
	public void setSignImage(File signImage) {
		this.signImage = signImage;
	}
	public String getMerchantNo() {
		return merchantNo;
	}
	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}
	public String getTerminalNo() {
		return terminalNo;
	}
	public void setTerminalNo(String terminalNo) {
		this.terminalNo = terminalNo;
	}
	public String getAuthorizationCode() {
		return authorizationCode;
	}
	public void setAuthorizationCode(String authorizationCode) {
		this.authorizationCode = authorizationCode;
	}
	
}
