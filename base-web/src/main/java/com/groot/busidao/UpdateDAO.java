package com.groot.busidao;

import java.util.Map;

public interface UpdateDAO {

	public int update(String sqlId);
	
	public int update(String sqlId, String condition);
	
	public int update(String sqlId, Map condition);
}
