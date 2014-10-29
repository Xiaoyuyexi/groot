package com.groot.busidao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ibatis.sqlmap.client.SqlMapExecutor;

public abstract class BatchBaseDAOImpl extends SqlMapClientDaoSupport implements BatchBaseDAO {

	public void batchExecute(final List<? extends Object> parameterList, final String sql, final int submitCount) throws DataAccessException {
		this.getSqlMapClientTemplate().execute(new SqlMapClientCallback<Object>() {
			public Object doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException {
				executor.startBatch();
				int batch = 0;
				for (Object parameter : parameterList) {
					
					operate(executor, sql, parameter);
					
					batch++;
					if (batch == submitCount) {
						executor.executeBatch();
						batch = 0;
					}
				}
				executor.executeBatch();
				return null;
			}
		});
	}
	
	public abstract void operate(SqlMapExecutor executor, String sql, Object parameter) throws SQLException;
	
	
}
