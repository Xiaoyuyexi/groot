package com.groot.busidao;

import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class UpdateDAOImpl extends SqlMapClientDaoSupport implements UpdateDAO {

	public int update(String sqlId){
		return this.getSqlMapClientTemplate().update(sqlId);
	}
	
	public int update(String sqlId, String condition){
		return this.getSqlMapClientTemplate().update(sqlId, condition);
	}
	
	public int update(String sqlId, Map condition){
		return this.getSqlMapClientTemplate().update(sqlId, condition);
	}
	
}
