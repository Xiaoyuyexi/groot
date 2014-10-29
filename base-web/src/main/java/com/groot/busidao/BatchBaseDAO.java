package com.groot.busidao;

import java.util.List;

import org.springframework.dao.DataAccessException;

public interface BatchBaseDAO { 

	public void batchExecute(final List<? extends Object> parameterList, final String sql, final int submitCount) throws DataAccessException;
	
}
