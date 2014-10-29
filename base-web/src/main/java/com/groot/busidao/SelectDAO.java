package com.groot.busidao;

import java.util.List;
import java.util.Map;

import com.groot.vo.DataSet;
import com.groot.vo.StatisticalDataVO;

public interface SelectDAO {
	
	public int count(String sqlId, Map<String,Object> conditions);
	public String amount(String sqlId, Map<String, Object> conditions);
	public StatisticalDataVO QueryRowsAndAmount(String sqlId, Map<String,Object> conditions);
	public List list(String sqlId, Map<String,Object> conditions, int pageNumber, int pageSize);
	public DataSet getDataSet(String countSqlId, String listSqlId, Map<String,Object> conditions, int pageNumber, int pageSize);
	
	public List list(String sqlId);
	public List list(String sqlId, String condition);
	public List list(String sqlId, Map<String,Object> conditions);
	
	public Object object(String sqlId);
	public Object object(String sqlId, String condition);
	public Object object(String sqlId, Map<String, Object> conditions);
	
	public Object selectSqlPage(Map<String, Object> conditions);
	public Object selectSqlCount(Map<String, Object> conditions);
	public Object selectSql(Map<String, Object> conditions);
	public Object updateSql(Map<String, String> conditions);
	public Object updateSql(String sql,Map<String, String> conditions);
	public Map map(String sqlId,String mapKey,String id);
	public long getBeginNum(int pageNumber , int pageSize);
	public long getEndNum(int pageNumber , int pageSize);
}
