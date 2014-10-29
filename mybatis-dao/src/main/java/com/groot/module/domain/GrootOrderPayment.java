package com.groot.module.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;

public class GrootOrderPayment implements Serializable {

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database column GROOT_ORDER_PAYMENT.ID
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	private String id;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database column GROOT_ORDER_PAYMENT.ORDER_NO
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	private String orderNo;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database column GROOT_ORDER_PAYMENT.ORDER_TYPE
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	private String orderType;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database column GROOT_ORDER_PAYMENT.CARD_NO
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	private String cardNo;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database column GROOT_ORDER_PAYMENT.TRX_AMT
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	private BigDecimal trxAmt;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database column GROOT_ORDER_PAYMENT.MOBILE
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	private String mobile;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database column GROOT_ORDER_PAYMENT.STATUS
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	private String status;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database column GROOT_ORDER_PAYMENT.CREATE_TIME
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	private Date createTime;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database column GROOT_ORDER_PAYMENT.COMPLETE_TIME
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	private Date completeTime;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database column GROOT_ORDER_PAYMENT.NOTE
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	private String note;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database table GROOT_ORDER_PAYMENT
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns the value of the database column GROOT_ORDER_PAYMENT.ID
	 * @return  the value of GROOT_ORDER_PAYMENT.ID
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	public String getId() {
		return id;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the value of the database column GROOT_ORDER_PAYMENT.ID
	 * @param id  the value for GROOT_ORDER_PAYMENT.ID
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns the value of the database column GROOT_ORDER_PAYMENT.ORDER_NO
	 * @return  the value of GROOT_ORDER_PAYMENT.ORDER_NO
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	public String getOrderNo() {
		return orderNo;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the value of the database column GROOT_ORDER_PAYMENT.ORDER_NO
	 * @param orderNo  the value for GROOT_ORDER_PAYMENT.ORDER_NO
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns the value of the database column GROOT_ORDER_PAYMENT.ORDER_TYPE
	 * @return  the value of GROOT_ORDER_PAYMENT.ORDER_TYPE
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	public String getOrderType() {
		return orderType;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the value of the database column GROOT_ORDER_PAYMENT.ORDER_TYPE
	 * @param orderType  the value for GROOT_ORDER_PAYMENT.ORDER_TYPE
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns the value of the database column GROOT_ORDER_PAYMENT.CARD_NO
	 * @return  the value of GROOT_ORDER_PAYMENT.CARD_NO
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	public String getCardNo() {
		return cardNo;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the value of the database column GROOT_ORDER_PAYMENT.CARD_NO
	 * @param cardNo  the value for GROOT_ORDER_PAYMENT.CARD_NO
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns the value of the database column GROOT_ORDER_PAYMENT.TRX_AMT
	 * @return  the value of GROOT_ORDER_PAYMENT.TRX_AMT
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	public BigDecimal getTrxAmt() {
		return trxAmt;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the value of the database column GROOT_ORDER_PAYMENT.TRX_AMT
	 * @param trxAmt  the value for GROOT_ORDER_PAYMENT.TRX_AMT
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	public void setTrxAmt(BigDecimal trxAmt) {
		this.trxAmt = trxAmt;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns the value of the database column GROOT_ORDER_PAYMENT.MOBILE
	 * @return  the value of GROOT_ORDER_PAYMENT.MOBILE
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the value of the database column GROOT_ORDER_PAYMENT.MOBILE
	 * @param mobile  the value for GROOT_ORDER_PAYMENT.MOBILE
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns the value of the database column GROOT_ORDER_PAYMENT.STATUS
	 * @return  the value of GROOT_ORDER_PAYMENT.STATUS
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the value of the database column GROOT_ORDER_PAYMENT.STATUS
	 * @param status  the value for GROOT_ORDER_PAYMENT.STATUS
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns the value of the database column GROOT_ORDER_PAYMENT.CREATE_TIME
	 * @return  the value of GROOT_ORDER_PAYMENT.CREATE_TIME
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the value of the database column GROOT_ORDER_PAYMENT.CREATE_TIME
	 * @param createTime  the value for GROOT_ORDER_PAYMENT.CREATE_TIME
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns the value of the database column GROOT_ORDER_PAYMENT.COMPLETE_TIME
	 * @return  the value of GROOT_ORDER_PAYMENT.COMPLETE_TIME
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	public Date getCompleteTime() {
		return completeTime;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the value of the database column GROOT_ORDER_PAYMENT.COMPLETE_TIME
	 * @param completeTime  the value for GROOT_ORDER_PAYMENT.COMPLETE_TIME
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns the value of the database column GROOT_ORDER_PAYMENT.NOTE
	 * @return  the value of GROOT_ORDER_PAYMENT.NOTE
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	public String getNote() {
		return note;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the value of the database column GROOT_ORDER_PAYMENT.NOTE
	 * @param note  the value for GROOT_ORDER_PAYMENT.NOTE
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	public void setNote(String note) {
		this.note = note;
	}
}