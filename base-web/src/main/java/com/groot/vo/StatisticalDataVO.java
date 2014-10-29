package com.groot.vo;

import java.math.BigDecimal;

public class StatisticalDataVO {
	private int count;
	private BigDecimal amount;
	
	//新增统计金额
	private int tcount;//交易总笔数
	private BigDecimal tsettleAmount;//结算总金额
	private BigDecimal tfee;//交易手续费
	
	
	private int trxCount;//消费总笔数
	private BigDecimal trxAmount;//消费总金额
	private BigDecimal trxFee;//消费手续费
	
	
	private int tfbCount;//退货总笔数
	private BigDecimal tfbAmount;//退货总金额
	private BigDecimal tfbFee;//退货手续费
	
	//新增商户绑定数量 终端绑定数
	private int terminalBindCount;
	private int merchantBindCount;
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getTcount() {
		return tcount;
	}
	public void setTcount(int tcount) {
		this.tcount = tcount;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public BigDecimal getTsettleAmount() {
		return tsettleAmount;
	}
	public void setTsettleAmount(BigDecimal tsettleAmount) {
		this.tsettleAmount = tsettleAmount;
	}
	public BigDecimal getTfee() {
		return tfee;
	}
	public void setTfee(BigDecimal tfee) {
		this.tfee = tfee;
	}
	public int getTfbCount() {
		return tfbCount;
	}
	public void setTfbCount(int tfbCount) {
		this.tfbCount = tfbCount;
	}
	public BigDecimal getTfbAmount() {
		return tfbAmount;
	}
	public void setTfbAmount(BigDecimal tfbAmount) {
		this.tfbAmount = tfbAmount;
	}
	public BigDecimal getTfbFee() {
		return tfbFee;
	}
	public void setTfbFee(BigDecimal tfbFee) {
		this.tfbFee = tfbFee;
	}
	public int getTerminalBindCount() {
		return terminalBindCount;
	}
	public void setTerminalBindCount(int terminalBindCount) {
		this.terminalBindCount = terminalBindCount;
	}
	public int getMerchantBindCount() {
		return merchantBindCount;
	}
	public void setMerchantBindCount(int merchantBindCount) {
		this.merchantBindCount = merchantBindCount;
	}
	public int getTrxCount() {
		return trxCount;
	}
	public void setTrxCount(int trxCount) {
		this.trxCount = trxCount;
	}
	public BigDecimal getTrxAmount() {
		return trxAmount;
	}
	public void setTrxAmount(BigDecimal trxAmount) {
		this.trxAmount = trxAmount;
	}
	public BigDecimal getTrxFee() {
		return trxFee;
	}
	public void setTrxFee(BigDecimal trxFee) {
		this.trxFee = trxFee;
	}
}
