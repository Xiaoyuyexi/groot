package com.groot.busidao;

public class SelectDaoMySqlImpl  extends SelectDAOImpl implements SelectDAO{

	/**
	 * 查询分页的beginNum
	 */
	@Override
	public long getBeginNum(int pageNumber , int pageSize){
		long begin = (pageNumber - 1) * pageSize;
		return begin;
	} 
	/**
	 * 查询分页的endNum
	 */
	@Override
	public long getEndNum(int pageNumber , int pageSize){
		return pageSize;
	} 
	
	
}
