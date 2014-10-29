package com.groot.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.groot.busidao.SelectDAO;
import com.groot.vo.Colums;
import com.groot.vo.DataSet;
import com.groot.vo.StatisticalDataVO;
@Service
public class SelectService<T> {
	
	String[] keys = {"SELECT","UPDATE","AS","FROM","DELETE","*","WHERE"};
	
	@Autowired
	private SelectDAO select;
	public DataSet<T> getDataSet(String countSqlId, String listSqlId, Map<String,Object> conditions, int pageNumber, int pageSize){
		int count = this.select.count(countSqlId, conditions);
		List<T> list = this.select.list(listSqlId, conditions, pageNumber, pageSize);
		DataSet<T> dataSet = new DataSet<T>(pageNumber, pageSize, count, list);
		return dataSet;
	}
	
	public DataSet<T> getDataSetOrAmount(String countSqlId, String listSqlId, Map<String,Object> conditions, int pageNumber, int pageSize) {
		StatisticalDataVO count = this.select.QueryRowsAndAmount(countSqlId, conditions);
		List<T> list = this.select.list(listSqlId, conditions, pageNumber, pageSize);
		DataSet<T> dataSet = new DataSet<T>(pageNumber, pageSize, count.getCount(),count.getAmount(), list);
		return dataSet;
	}
	
	public DataSet<T> getDataSetOrAmount(String countSqlId, String amountSqlId, String listSqlId, Map<String,Object> conditions, int pageNumber, int pageSize) {
		int count = this.select.count(countSqlId, conditions);
		String amount = this.select.amount(amountSqlId, conditions);
		List<T> list = this.select.list(listSqlId, conditions, pageNumber, pageSize);
		DataSet<T> dataSet = new DataSet<T>(pageNumber, pageSize, count, new BigDecimal(amount), list);
		return dataSet;
	}
	
	/**
	 * 获取交易和结算的费用
	 * @param countSqlId
	 * @param listSqlId
	 * @param conditions
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public DataSet<T> getDataSetAndAmount(String countSqlId, String listSqlId, Map<String,Object> conditions, int pageNumber, int pageSize) {
		StatisticalDataVO count = this.select.QueryRowsAndAmount(countSqlId, conditions);
		List<T> list = this.select.list(listSqlId, conditions, pageNumber, pageSize);
		DataSet<T> dataSet = new DataSet<T>(pageNumber, pageSize, count, list);
		dataSet.setTrxAmount(count.getTrxAmount());
		dataSet.setTrxCount(count.getTrxCount());
		dataSet.setTrxFee(count.getTrxFee());
		return dataSet;
	}
	
	/**
	 * 查询sql分页 
	 */
	public Object selectSql(String sql,int pageNumber, int pageSize){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("sql", sql);
//		int begin = (pageNumber - 1) * pageSize + 1;
//		int end = pageNumber * pageSize;
		if(0!=pageSize){
			map.put("begin", select.getBeginNum(pageNumber, pageSize));
			map.put("end", select.getEndNum(pageNumber, pageSize));
		}
		return this.select.selectSqlPage(map);
	}
	public Object selectSqlAll(String sql){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("sql", sql);
		return this.select.selectSqlPage(map);
	}
	/**
	 * 获取数量
	 * @param sql
	 * @return
	 */
	public Object selectSqlCount(String sql){
		sql = keys[0]+" count(*) " +sql.substring(sql.indexOf(keys[3])).trim();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("sql", sql);
		return this.select.selectSqlCount(map);
	}
	
	/**
	 * 获取数量
	 * @param sql
	 * @return
	 */
	public Object selectSqlCount(String sql,String source){
		sql = keys[0]+" count(*) " +source.substring(sql.indexOf(keys[3])).trim();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("sql", sql);
		return this.select.selectSqlCount(map);
	}
	
	/**
	 * 更新语句
	 * @param sql
	 * @return
	 */
	public Object updateSql(String sql){
		Map<String,String> map = new HashMap<String,String>();
		map.put("sql", sql);
		return this.select.updateSql(map);
	}
	/**
	 * 处理sql语句
	 * @param sql
	 * @return
	 */
	@Transactional
	public Object processSql(String sql,int pageNumber, int pageSize){
		//Grid grid = new Grid();
		if(StringUtils.isEmpty(sql)){
			DataSet dataSet = new DataSet();
			dataSet.setCode("01");
			return dataSet;
		}
		
		DataSet<T> dataSet=null;
		int count=0;
		List<T> list = new ArrayList<T>();
		List<Colums>  columList= new ArrayList<Colums>();
		try{
			//sql = sql.trim();
			sql = convert(sql);
			String sqlA = sql.toUpperCase();
			if(sqlA.startsWith(keys[0])){
				list = (List)selectSql(sql,pageNumber, pageSize);
				/*List<T> listCount = (List)selectSql(sql,1, 0);
				count = listCount.size();*/
				count = (Integer)selectSqlCount(sqlA,sql);
				columList = analyzeSql(sql,list);
				if(0==pageSize){
					pageSize = count==0?1:count;
				}
				dataSet = new DataSet<T>(pageNumber, pageSize, count, list);
				dataSet.setColums(columList);
			}else{
				Colums colum = new Colums();
				colum.setId("id");
				colum.setName("影响的行数");
				columList.add(colum);
				Colums data = new Colums();
				data.setId(updateSql(sql).toString());
				List<Colums> listStr = new ArrayList<Colums>();
				listStr.add(data);
				DataSet<Colums> dataStar = new DataSet<Colums>(pageNumber, pageSize, count, listStr);
				dataStar.setColums(columList);
				return dataStar;
			}
		}catch(Exception e){
			DataSet<String> dataException = new DataSet<String>();
			dataException.setCode("02");
			List<String> listStr = new ArrayList<String>();
			listStr.add(e.getMessage());
			dataException.setList(listStr);
			return dataException;
		}
		return dataSet;
	
	}
	
	public String convert(String sql){
		if(sql.endsWith(";")){
			sql = sql.substring(0, sql.length()-1);
		}
		sql = sql.trim();
		/*int where = sqlA.indexOf(keys[3]);
		if(-1 == where){
			return sql;
		}
		String sql1 = sqlA.substring(0,where+keys[3].length());
		String sql2 = sql.substring(where+keys[3].length());
		return sql1+sql2;*/
		return sql;
	}
	
	
	/**
	 * 分析查询sql
	 * @param sql
	 * @param object
	 * @return
	 */
	public List<Colums> analyzeSql(String sql,Object object){
		List<Colums>  columList= new ArrayList<Colums>();
		List list= (List)object;
			if(list.size()!=0){
				Object s = list.get(0);
				for(String f: ((Map<String, String>)s).keySet()){
					Colums c = new Colums();
					c.setId(f);
					c.setName(f);
					columList.add(c);
			}
		}
		
		/*String colum = sql.substring(keys[0].length(), sql.indexOf(keys[3])).trim();
		if(keys[5].equals(colum.trim())){
			List list= (List)object;
			if(list.size()!=0){
				Object s = list.get(0);
				for(String f: ((Map<String, String>)s).keySet()){
					Colums c = new Colums();
					c.setId(f);
					c.setName(f);
					columList.add(c);
				}
			}
		}else{
			String[] colums = colum.split(",");
			for(String o:colums){
				Colums c = new Colums();
				o=o.trim();
				if(o.contains(keys[2])){
					if(o.split(" ").length>2){
						o = o.split(" ")[2].trim();
					}
				}
				if(o.contains(".")){
					o = o.split("\\.")[1];
				}
				c.setId(o);
				c.setName(o);
				columList.add(c);
			}
		}*/
		return columList;
	}
	
	public Object processSqlAll(String sql){
		//Grid grid = new Grid();
		if(StringUtils.isEmpty(sql)){
			DataSet dataSet = new DataSet();
			dataSet.setCode("01");
			return dataSet;
		}
		
		DataSet<T> dataSet=null;
		int count=0;
		List<T> list = new ArrayList<T>();
		List<Colums>  columList= new ArrayList<Colums>();
		try{
			sql = convert(sql);
			if(sql.startsWith(keys[0])){
				list = (List)selectSqlAll(sql);
				Object object = selectSqlCount(sql);
				count = (Integer)((List)object).get(0);
				columList = analyzeSql(sql,list);
				dataSet = new DataSet<T>(1, columList.size(), count, list);
				dataSet.setColums(columList);
			}else{
				Colums colum = new Colums();
				colum.setId("id");
				colum.setName("影响的行数");
				columList.add(colum);
				Colums data = new Colums();
				data.setId(updateSql(sql).toString());
				List<Colums> listStr = new ArrayList<Colums>();
				listStr.add(data);
				DataSet<Colums> dataStar = new DataSet<Colums>(1, columList.size(), count, listStr);
				dataStar.setColums(columList);
				return dataStar;
			}
		}catch(Exception e){
			DataSet<String> dataException = new DataSet<String>();
			dataException.setCode("02");
			List<String> listStr = new ArrayList<String>();
			listStr.add(e.getMessage());
			dataException.setList(listStr);
			return dataException;
		}
		return dataSet;
	
	}
	public static void main(String[] args) {
		System.out.println(new SelectService().convert("select * from hkrt_system_user where login_name = 'admin'"));
	}
}	
