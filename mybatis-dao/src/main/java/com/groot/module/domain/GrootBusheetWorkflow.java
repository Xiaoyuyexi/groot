package com.groot.module.domain;

import java.util.Date;
import java.io.Serializable;

public class GrootBusheetWorkflow implements Serializable {

	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database column GROOT_BUSHEET_WORKFLOW.id
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	private String id;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database column GROOT_BUSHEET_WORKFLOW.workflow_name
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	private String workflowName;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database column GROOT_BUSHEET_WORKFLOW.status_code
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	private String statusCode;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database column GROOT_BUSHEET_WORKFLOW.BUSHEET_ID
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	private String busheetId;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database column GROOT_BUSHEET_WORKFLOW.status
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	private String status;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database column GROOT_BUSHEET_WORKFLOW.create_user_id
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	private String createUserId;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database column GROOT_BUSHEET_WORKFLOW.create_time
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	private Date createTime;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database column GROOT_BUSHEET_WORKFLOW.update_user_id
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	private String updateUserId;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database column GROOT_BUSHEET_WORKFLOW.update_time
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	private Date updateTime;
	/**
	 * This field was generated by Apache iBATIS ibator. This field corresponds to the database table GROOT_BUSHEET_WORKFLOW
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns the value of the database column GROOT_BUSHEET_WORKFLOW.id
	 * @return  the value of GROOT_BUSHEET_WORKFLOW.id
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	public String getId() {
		return id;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the value of the database column GROOT_BUSHEET_WORKFLOW.id
	 * @param id  the value for GROOT_BUSHEET_WORKFLOW.id
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns the value of the database column GROOT_BUSHEET_WORKFLOW.workflow_name
	 * @return  the value of GROOT_BUSHEET_WORKFLOW.workflow_name
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	public String getWorkflowName() {
		return workflowName;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the value of the database column GROOT_BUSHEET_WORKFLOW.workflow_name
	 * @param workflowName  the value for GROOT_BUSHEET_WORKFLOW.workflow_name
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	public void setWorkflowName(String workflowName) {
		this.workflowName = workflowName;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns the value of the database column GROOT_BUSHEET_WORKFLOW.status_code
	 * @return  the value of GROOT_BUSHEET_WORKFLOW.status_code
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	public String getStatusCode() {
		return statusCode;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the value of the database column GROOT_BUSHEET_WORKFLOW.status_code
	 * @param statusCode  the value for GROOT_BUSHEET_WORKFLOW.status_code
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns the value of the database column GROOT_BUSHEET_WORKFLOW.BUSHEET_ID
	 * @return  the value of GROOT_BUSHEET_WORKFLOW.BUSHEET_ID
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	public String getBusheetId() {
		return busheetId;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the value of the database column GROOT_BUSHEET_WORKFLOW.BUSHEET_ID
	 * @param busheetId  the value for GROOT_BUSHEET_WORKFLOW.BUSHEET_ID
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	public void setBusheetId(String busheetId) {
		this.busheetId = busheetId;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns the value of the database column GROOT_BUSHEET_WORKFLOW.status
	 * @return  the value of GROOT_BUSHEET_WORKFLOW.status
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the value of the database column GROOT_BUSHEET_WORKFLOW.status
	 * @param status  the value for GROOT_BUSHEET_WORKFLOW.status
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns the value of the database column GROOT_BUSHEET_WORKFLOW.create_user_id
	 * @return  the value of GROOT_BUSHEET_WORKFLOW.create_user_id
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	public String getCreateUserId() {
		return createUserId;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the value of the database column GROOT_BUSHEET_WORKFLOW.create_user_id
	 * @param createUserId  the value for GROOT_BUSHEET_WORKFLOW.create_user_id
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns the value of the database column GROOT_BUSHEET_WORKFLOW.create_time
	 * @return  the value of GROOT_BUSHEET_WORKFLOW.create_time
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the value of the database column GROOT_BUSHEET_WORKFLOW.create_time
	 * @param createTime  the value for GROOT_BUSHEET_WORKFLOW.create_time
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns the value of the database column GROOT_BUSHEET_WORKFLOW.update_user_id
	 * @return  the value of GROOT_BUSHEET_WORKFLOW.update_user_id
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	public String getUpdateUserId() {
		return updateUserId;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the value of the database column GROOT_BUSHEET_WORKFLOW.update_user_id
	 * @param updateUserId  the value for GROOT_BUSHEET_WORKFLOW.update_user_id
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method returns the value of the database column GROOT_BUSHEET_WORKFLOW.update_time
	 * @return  the value of GROOT_BUSHEET_WORKFLOW.update_time
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	/**
	 * This method was generated by Apache iBATIS ibator. This method sets the value of the database column GROOT_BUSHEET_WORKFLOW.update_time
	 * @param updateTime  the value for GROOT_BUSHEET_WORKFLOW.update_time
	 * @ibatorgenerated  Wed Oct 29 15:35:22 CST 2014
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}