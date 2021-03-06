package com.groot.module.dao;

import com.groot.module.domain.GrootSystemMonitor;
import com.groot.module.domain.GrootSystemMonitorExample;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class GrootSystemMonitorDAOImpl extends SqlMapClientDaoSupport implements GrootSystemMonitorDAO {

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table GROOT_SYSTEM_MONITOR
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	public GrootSystemMonitorDAOImpl() {
		super();
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table GROOT_SYSTEM_MONITOR
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	public int countByExample(GrootSystemMonitorExample example) {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(
				"GROOT_SYSTEM_MONITOR.ibatorgenerated_countByExample", example);
		return count.intValue();
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table GROOT_SYSTEM_MONITOR
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	public int deleteByExample(GrootSystemMonitorExample example) {
		int rows = getSqlMapClientTemplate()
				.delete("GROOT_SYSTEM_MONITOR.ibatorgenerated_deleteByExample",
						example);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table GROOT_SYSTEM_MONITOR
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	public int deleteByPrimaryKey(String id) {
		GrootSystemMonitor key = new GrootSystemMonitor();
		key.setId(id);
		int rows = getSqlMapClientTemplate().delete(
				"GROOT_SYSTEM_MONITOR.ibatorgenerated_deleteByPrimaryKey", key);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table GROOT_SYSTEM_MONITOR
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	public void insert(GrootSystemMonitor record) {
		getSqlMapClientTemplate().insert(
				"GROOT_SYSTEM_MONITOR.ibatorgenerated_insert", record);
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table GROOT_SYSTEM_MONITOR
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	public void insertSelective(GrootSystemMonitor record) {
		getSqlMapClientTemplate().insert(
				"GROOT_SYSTEM_MONITOR.ibatorgenerated_insertSelective", record);
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table GROOT_SYSTEM_MONITOR
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	public List selectByExampleWithBLOBs(GrootSystemMonitorExample example) {
		List list = getSqlMapClientTemplate()
				.queryForList(
						"GROOT_SYSTEM_MONITOR.ibatorgenerated_selectByExampleWithBLOBs",
						example);
		return list;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table GROOT_SYSTEM_MONITOR
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	public List selectByExampleWithoutBLOBs(GrootSystemMonitorExample example) {
		List list = getSqlMapClientTemplate()
				.queryForList(
						"GROOT_SYSTEM_MONITOR.ibatorgenerated_selectByExample",
						example);
		return list;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table GROOT_SYSTEM_MONITOR
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	public GrootSystemMonitor selectByPrimaryKey(String id) {
		GrootSystemMonitor key = new GrootSystemMonitor();
		key.setId(id);
		GrootSystemMonitor record = (GrootSystemMonitor) getSqlMapClientTemplate()
				.queryForObject(
						"GROOT_SYSTEM_MONITOR.ibatorgenerated_selectByPrimaryKey",
						key);
		return record;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table GROOT_SYSTEM_MONITOR
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	public int updateByExampleSelective(GrootSystemMonitor record,
			GrootSystemMonitorExample example) {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = getSqlMapClientTemplate()
				.update("GROOT_SYSTEM_MONITOR.ibatorgenerated_updateByExampleSelective",
						parms);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table GROOT_SYSTEM_MONITOR
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	public int updateByExampleWithBLOBs(GrootSystemMonitor record,
			GrootSystemMonitorExample example) {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = getSqlMapClientTemplate()
				.update("GROOT_SYSTEM_MONITOR.ibatorgenerated_updateByExampleWithBLOBs",
						parms);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table GROOT_SYSTEM_MONITOR
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	public int updateByExampleWithoutBLOBs(GrootSystemMonitor record,
			GrootSystemMonitorExample example) {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = getSqlMapClientTemplate().update(
				"GROOT_SYSTEM_MONITOR.ibatorgenerated_updateByExample", parms);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table GROOT_SYSTEM_MONITOR
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	public int updateByPrimaryKeySelective(GrootSystemMonitor record) {
		int rows = getSqlMapClientTemplate()
				.update("GROOT_SYSTEM_MONITOR.ibatorgenerated_updateByPrimaryKeySelective",
						record);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table GROOT_SYSTEM_MONITOR
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	public int updateByPrimaryKeyWithBLOBs(GrootSystemMonitor record) {
		int rows = getSqlMapClientTemplate()
				.update("GROOT_SYSTEM_MONITOR.ibatorgenerated_updateByPrimaryKeyWithBLOBs",
						record);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table GROOT_SYSTEM_MONITOR
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	public int updateByPrimaryKeyWithoutBLOBs(GrootSystemMonitor record) {
		int rows = getSqlMapClientTemplate().update(
				"GROOT_SYSTEM_MONITOR.ibatorgenerated_updateByPrimaryKey",
				record);
		return rows;
	}

	/**
	 * This class was generated by Apache iBATIS ibator. This class corresponds to the database table GROOT_SYSTEM_MONITOR
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	private static class UpdateByExampleParms extends GrootSystemMonitorExample {
		private Object record;

		public UpdateByExampleParms(Object record,
				GrootSystemMonitorExample example) {
			super(example);
			this.record = record;
		}

		public Object getRecord() {
			return record;
		}
	}
}