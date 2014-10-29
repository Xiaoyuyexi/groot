package com.groot.busidao;

import java.sql.SQLException;

import com.ibatis.sqlmap.client.SqlMapExecutor;


public class BatchUpdateDAOImpl extends BatchBaseDAOImpl implements BatchBaseDAO{

	@Override
	public void operate(SqlMapExecutor executor, String sql, Object parameter)
			throws SQLException {
		executor.update(sql, parameter);
		
	}

	
}
