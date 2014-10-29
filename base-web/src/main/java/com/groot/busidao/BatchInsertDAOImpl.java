package com.groot.busidao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.ibatis.sqlmap.client.SqlMapExecutor;

public class BatchInsertDAOImpl extends BatchBaseDAOImpl implements BatchBaseDAO{

	@Override
	public void operate(SqlMapExecutor executor, String sql, Object parameter)
			throws SQLException {
		executor.insert(sql, parameter);
		
	}

}
