package com.groot.module.dao;

import com.groot.module.domain.GrootOrderPayment;
import com.groot.module.domain.GrootOrderPaymentExample;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class GrootOrderPaymentDAOImpl extends SqlMapClientDaoSupport implements GrootOrderPaymentDAO {

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table GROOT_ORDER_PAYMENT
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	public GrootOrderPaymentDAOImpl() {
		super();
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table GROOT_ORDER_PAYMENT
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	public int countByExample(GrootOrderPaymentExample example) {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(
				"GROOT_ORDER_PAYMENT.ibatorgenerated_countByExample", example);
		return count.intValue();
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table GROOT_ORDER_PAYMENT
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	public int deleteByExample(GrootOrderPaymentExample example) {
		int rows = getSqlMapClientTemplate().delete(
				"GROOT_ORDER_PAYMENT.ibatorgenerated_deleteByExample", example);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table GROOT_ORDER_PAYMENT
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	public int deleteByPrimaryKey(String id) {
		GrootOrderPayment key = new GrootOrderPayment();
		key.setId(id);
		int rows = getSqlMapClientTemplate().delete(
				"GROOT_ORDER_PAYMENT.ibatorgenerated_deleteByPrimaryKey", key);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table GROOT_ORDER_PAYMENT
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	public void insert(GrootOrderPayment record) {
		getSqlMapClientTemplate().insert(
				"GROOT_ORDER_PAYMENT.ibatorgenerated_insert", record);
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table GROOT_ORDER_PAYMENT
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	public void insertSelective(GrootOrderPayment record) {
		getSqlMapClientTemplate().insert(
				"GROOT_ORDER_PAYMENT.ibatorgenerated_insertSelective", record);
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table GROOT_ORDER_PAYMENT
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	public List selectByExample(GrootOrderPaymentExample example) {
		List list = getSqlMapClientTemplate().queryForList(
				"GROOT_ORDER_PAYMENT.ibatorgenerated_selectByExample", example);
		return list;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table GROOT_ORDER_PAYMENT
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	public GrootOrderPayment selectByPrimaryKey(String id) {
		GrootOrderPayment key = new GrootOrderPayment();
		key.setId(id);
		GrootOrderPayment record = (GrootOrderPayment) getSqlMapClientTemplate()
				.queryForObject(
						"GROOT_ORDER_PAYMENT.ibatorgenerated_selectByPrimaryKey",
						key);
		return record;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table GROOT_ORDER_PAYMENT
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	public int updateByExampleSelective(GrootOrderPayment record,
			GrootOrderPaymentExample example) {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = getSqlMapClientTemplate().update(
				"GROOT_ORDER_PAYMENT.ibatorgenerated_updateByExampleSelective",
				parms);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table GROOT_ORDER_PAYMENT
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	public int updateByExample(GrootOrderPayment record,
			GrootOrderPaymentExample example) {
		UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
		int rows = getSqlMapClientTemplate().update(
				"GROOT_ORDER_PAYMENT.ibatorgenerated_updateByExample", parms);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table GROOT_ORDER_PAYMENT
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	public int updateByPrimaryKeySelective(GrootOrderPayment record) {
		int rows = getSqlMapClientTemplate()
				.update("GROOT_ORDER_PAYMENT.ibatorgenerated_updateByPrimaryKeySelective",
						record);
		return rows;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table GROOT_ORDER_PAYMENT
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	public int updateByPrimaryKey(GrootOrderPayment record) {
		int rows = getSqlMapClientTemplate().update(
				"GROOT_ORDER_PAYMENT.ibatorgenerated_updateByPrimaryKey",
				record);
		return rows;
	}

	/**
	 * This class was generated by Apache iBATIS ibator. This class corresponds to the database table GROOT_ORDER_PAYMENT
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	private static class UpdateByExampleParms extends GrootOrderPaymentExample {
		private Object record;

		public UpdateByExampleParms(Object record,
				GrootOrderPaymentExample example) {
			super(example);
			this.record = record;
		}

		public Object getRecord() {
			return record;
		}
	}
}