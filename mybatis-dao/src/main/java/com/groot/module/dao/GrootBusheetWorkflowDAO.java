package com.groot.module.dao;

import com.groot.module.domain.GrootBusheetWorkflow;
import com.groot.module.domain.GrootBusheetWorkflowExample;
import java.util.List;

public interface GrootBusheetWorkflowDAO {

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table GROOT_BUSHEET_WORKFLOW
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	int countByExample(GrootBusheetWorkflowExample example);

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table GROOT_BUSHEET_WORKFLOW
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	int deleteByExample(GrootBusheetWorkflowExample example);

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table GROOT_BUSHEET_WORKFLOW
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	int deleteByPrimaryKey(String id);

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table GROOT_BUSHEET_WORKFLOW
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	void insert(GrootBusheetWorkflow record);

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table GROOT_BUSHEET_WORKFLOW
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	void insertSelective(GrootBusheetWorkflow record);

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table GROOT_BUSHEET_WORKFLOW
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	List selectByExample(GrootBusheetWorkflowExample example);

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table GROOT_BUSHEET_WORKFLOW
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	GrootBusheetWorkflow selectByPrimaryKey(String id);

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table GROOT_BUSHEET_WORKFLOW
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	int updateByExampleSelective(GrootBusheetWorkflow record,
			GrootBusheetWorkflowExample example);

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table GROOT_BUSHEET_WORKFLOW
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	int updateByExample(GrootBusheetWorkflow record,
			GrootBusheetWorkflowExample example);

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table GROOT_BUSHEET_WORKFLOW
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	int updateByPrimaryKeySelective(GrootBusheetWorkflow record);

	/**
	 * This method was generated by Apache iBATIS ibator. This method corresponds to the database table GROOT_BUSHEET_WORKFLOW
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	int updateByPrimaryKey(GrootBusheetWorkflow record);
}