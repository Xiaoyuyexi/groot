package com.web.bo;

import java.io.Serializable;

/**
 * 卡业务BO
 * @author admin
 *
 */
public class CardBO  implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public CardBO(){
		returnCode="404";
	}
	
	//卡BIN主键
	public String cardBinId;
	
	//银行id
	public String bankId;
//银行名称
	public String bankName;
	//卡类型
	public String cardType;
	//卡位
	public Short bitCode;
	//有效期
	public String effcetiveDate;
	//卡组织
	public String cardOrganization;
	//卡号
	public String cardNum;
	//卡BIN起始位置
	public Short startingPos;
	//卡BIN长度
	public Short cardBinLen;
	//卡号起始位置
	public Short cardNoExcusion;
	//卡号长度
	public Short cardNoLength;
	//日期起始位置
	public Short effectiveDatePos;
	//卡号标识
	public String cardSign;
	//返回带代码
	public String returnCode;
	//返回值
	public String returnValue;
	//磁道
	public String cardSignTrack;
	//发卡机构代码
	public String issBankCode;
	
	//一磁信息
	public String trackTwo;
	
	//二磁信息
	public String trackThree;
	
	public String getTrackTwo() {
		return trackTwo;
	}

	public void setTrackTwo(String trackTwo) {
		this.trackTwo = trackTwo;
	}

	public String getTrackThree() {
		return trackThree;
	}

	public void setTrackThree(String trackThree) {
		this.trackThree = trackThree;
	}

	public String getCardBinId() {
		return cardBinId;
	}

	public void setCardBinId(String cardBinId) {
		this.cardBinId = cardBinId;
	}

	public String getIssBankCode() {
		return issBankCode;
	}

	public void setIssBankCode(String issBankCode) {
		this.issBankCode = issBankCode;
	}

	public String getCardSignTrack() {
		return cardSignTrack;
	}

	public void setCardSignTrack(String cardSignTrack) {
		this.cardSignTrack = cardSignTrack;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public Short getBitCode() {
		return bitCode;
	}

	public void setBitCode(Short bitCode) {
		this.bitCode = bitCode;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnValue() {
		return returnValue;
	}

	public void setReturnValue(String returnValue) {
		this.returnValue = returnValue;
	}

	public Short getCardBinLen() {
		return cardBinLen;
	}

	public void setCardBinLen(Short cardBinLen) {
		this.cardBinLen = cardBinLen;
	}

	public Short getCardNoExcusion() {
		return cardNoExcusion;
	}

	public void setCardNoExcusion(Short cardNoExcusion) {
		this.cardNoExcusion = cardNoExcusion;
	}

	public Short getCardNoLength() {
		return cardNoLength;
	}

	public void setCardNoLength(Short cardNoLength) {
		this.cardNoLength = cardNoLength;
	}

	public void setStartingPos(Short startingPos) {
		this.startingPos = startingPos;
	}
	
	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getEffcetiveDate() {
		return effcetiveDate;
	}

	public void setEffcetiveDate(String effcetiveDate) {
		this.effcetiveDate = effcetiveDate;
	}

	public String getCardOrganization() {
		return cardOrganization;
	}

	public void setCardOrganization(String cardOrganization) {
		this.cardOrganization = cardOrganization;
	}

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public Short getStartingPos() {
		return startingPos;
	}

	public String getCardSign() {
		return cardSign;
	}

	public void setCardSign(String cardSign) {
		this.cardSign = cardSign;
	}
	public Short getEffectiveDatePos() {
		return effectiveDatePos;
	}

	public void setEffectiveDatePos(Short effectiveDatePos) {
		this.effectiveDatePos = effectiveDatePos;
	}
}
