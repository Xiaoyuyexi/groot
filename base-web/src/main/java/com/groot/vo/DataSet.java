package com.groot.vo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DataSet<T> {

	private int count;//  list.size()查询总笔数
	private BigDecimal amount;	//	金额
	private List<T> list;
	List<Colums> colums = new ArrayList<Colums>();
	private int pages;//

	private int pageSize; //

	private int pageNumber;//

	private String code = "00";
	
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
	
	private BigDecimal profitDataAmount;//分润总金额
	
	private BigDecimal wholeSaleCount;//批发类交易总笔数
	private BigDecimal wholeSaleAmount;//批发类交易总金额
	
	private BigDecimal feCount;//餐饮娱乐类交易总笔数 
	private BigDecimal feAmount;//餐饮娱乐类交易总金额
	
	public BigDecimal getFeCount(){
		return feCount;
	}
	public void setFeCount(BigDecimal feCount){
		this.feCount = feCount;
	}
	public BigDecimal getFeAmount(){
		return feAmount;
	}
	public void setFeAmount(BigDecimal feAmount){
		this.feAmount = feAmount;
	}
	
	public BigDecimal getWholeSaleCount(){
		return wholeSaleCount;
	}
	public void setWholeSaleCount(BigDecimal wholeSaleCount){
		this.wholeSaleCount = wholeSaleCount;
	}
	public BigDecimal getWholeSaleAmount(){
		return wholeSaleAmount;
	}
	public void setWholeSaleAmount(BigDecimal wholeSaleAmount){
		this.wholeSaleAmount = wholeSaleAmount;
	}
	
	public BigDecimal getProfitDataAmount(){
		return profitDataAmount;
	}
	public void setProfitDataAmount(BigDecimal profitDataAmount){
		this.profitDataAmount = profitDataAmount;
	}
	
	public int getTcount() {
		return tcount;
	}
	public void setTcount(int tcount) {
		this.tcount = tcount;
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
	public DataSet(){
		
	}
	public DataSet(int pageNumber, int pageSize, int count, List<T> list) {
		super();
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.count = count;
		this.list = list;
		this.pages = (this.count + this.pageSize - 1) / this.pageSize;
	}
	
	public DataSet(int pageNumber, int pageSize, int count, BigDecimal amount, List<T> list) {
		super();
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.count = count;
		this.amount = amount;
		this.list = list;
		this.pages = (this.count + this.pageSize - 1) / this.pageSize;
	}
	
	public DataSet(int pageNumber, int pageSize, StatisticalDataVO vo, List<T> list) {
		super();
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.count = vo.getCount();
		this.amount = vo.getAmount();
		
		this.tcount = vo.getTcount();
		this.tfbAmount = vo.getTfbAmount();
		this.tfbCount = vo.getTfbCount();
		this.tfbFee = vo.getTfbFee();
		this.tfee = vo.getTfee();
		this.tsettleAmount = vo.getTsettleAmount();
		this.terminalBindCount = vo.getTerminalBindCount();
		this.merchantBindCount = vo.getMerchantBindCount();
		
		this.list = list;
		this.pages = (this.count + this.pageSize - 1) / this.pageSize;
	}
	
	public List<Colums> getColums() {
		return colums;
	}

	public void setColums(List<Colums> colums) {
		this.colums = colums;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getCount() {
		return count;
	}

	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public void setCount(int count) {
		this.count = count;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
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
