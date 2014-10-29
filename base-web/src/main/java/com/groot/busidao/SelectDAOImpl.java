package com.groot.busidao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.groot.vo.DataSet;
import com.groot.vo.StatisticalDataVO;

public abstract class SelectDAOImpl  extends SqlMapClientDaoSupport {

	public int count(String sqlId, Map<String, Object> conditions) {
		Integer integerCount = (Integer) this.getSqlMapClientTemplate().queryForObject(sqlId, conditions);
		if(integerCount != null){
			return integerCount.intValue();
		}
		return 0;
	}
	public String amount(String sqlId, Map<String, Object> conditions) {
		String amountStr = (String) this.getSqlMapClientTemplate().queryForObject(sqlId, conditions);
		return amountStr;	
	}
	public StatisticalDataVO QueryRowsAndAmount(String sqlId, Map<String, Object> conditions) {
		StatisticalDataVO count = (StatisticalDataVO) this.getSqlMapClientTemplate().queryForObject(sqlId, conditions);
		return count;
	}
	
	/**
	 * 查询分页的beginNum
	 */
	public abstract long getBeginNum(int pageNumber , int pageSize);
	/**
	 * 查询分页的endNum
	 */
	public abstract long getEndNum(int pageNumber , int pageSize);
	
	public List<Object> list(String sqlId, Map<String, Object> conditions, int pageNumber , int pageSize) {
		if(conditions == null){
			conditions = new HashMap<String, Object>();
		}
		conditions.put("begin", this.getBeginNum(pageNumber, pageSize));
		conditions.put("end", this.getEndNum(pageNumber , pageSize));
		
		@SuppressWarnings("unchecked")
		List<Object> list = this.getSqlMapClientTemplate().queryForList(sqlId, conditions);
		return list;
	}
	
	public DataSet getDataSet(String countSqlId, String listSqlId, Map<String, Object> conditions, int pageNumber, int pageSize) {
		int count = this.count(countSqlId, conditions);
		List list = this.list(countSqlId, conditions, pageNumber, pageSize);
		DataSet dataSet = new DataSet(pageNumber, pageSize, count, list);
		return dataSet;
	}
	
	
	
	
	
	
	
	
	public List list(String sqlId) {
		return this.getSqlMapClientTemplate().queryForList(sqlId);
	}
	public List list(String sqlId, String condition) {
		return this.getSqlMapClientTemplate().queryForList(sqlId, condition);
	}
	public List list(String sqlId, Map<String, Object> conditions) {
		return this.getSqlMapClientTemplate().queryForList(sqlId, conditions);
	}
	public Map map(String sqlId,String mapKey,String id) {
		return this.getSqlMapClientTemplate().queryForMap(sqlId,id,mapKey);
	}
	public Object object(String sqlId) {
		return this.getSqlMapClientTemplate().queryForObject(sqlId);
	}
	public Object object(String sqlId, String condition) {
		return this.getSqlMapClientTemplate().queryForObject(sqlId, condition);
	}
	public Object object(String sqlId, Map<String, Object> conditions) {
		return this.getSqlMapClientTemplate().queryForObject(sqlId, conditions);
	}
	
	public Object selectSql(Map<String, Object> conditions){
		return this.getSqlMapClientTemplate().queryForList("SYSTEM.selectBySql",conditions);
	}
	
	public Object selectSqlCount(Map<String, Object> conditions){
		return this.getSqlMapClientTemplate().queryForObject("SYSTEM.selectBySqlCount",conditions);
	}
	
	public Object selectSqlPage(Map<String, Object> conditions){
		return this.getSqlMapClientTemplate().queryForList("SYSTEM.selectBySqlPage",conditions);
	}
	
	public Object updateSql(Map<String, String> conditions){
		return this.getSqlMapClientTemplate().update("SYSTEM.updateBySql",conditions);
	}
	public Object updateSql(String sql, Map<String, String> conditions) {
		return this.getSqlMapClientTemplate().update(sql,conditions);
	}
	
}
