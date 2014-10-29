package com.groot.busidao;

public class SelectDaoOracleImpl  extends SelectDAOImpl implements SelectDAO{

	/**
	 * 查询分页的beginNum
	 */
	public long getBeginNum(int pageNumber , int pageSize){
		int begin = (pageNumber - 1) * pageSize + 1;
		return begin;
	} 
	/**
	 * 查询分页的endNum
	 */
	public long getEndNum(int pageNumber , int pageSize){
		int end = pageNumber * pageSize;
		return end;
	} 
	
}
