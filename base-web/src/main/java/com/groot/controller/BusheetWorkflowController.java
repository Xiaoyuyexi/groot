package com.groot.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.groot.module.domain.GrootBusheetWorkflow;
import com.groot.service.BusheetWorkflowService;
import com.groot.service.SelectService;


@Controller
public class BusheetWorkflowController {
	@Autowired
	public SelectService selectService;
	@Autowired
	public BusheetWorkflowService busheetWorkflowService;
	/**
	 * 单据子表列表(分页)
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getBusheetWorkflowList", method = RequestMethod.POST)
	public List<GrootBusheetWorkflow> getBusheetWorkflowList(@RequestParam Map<String, Object> mapParam) {
		List<GrootBusheetWorkflow> workflow = busheetWorkflowService.getBusheetWorkflowList(mapParam);
		return workflow;
	}
	
	/**
	 * 更新或者添加一条记录
	 * @param busheet
	 * @return
	 */
	@RequiresPermissions("BusheetWorkflow:save")
	@ResponseBody
	@RequestMapping(value = "/saveBusheetWorkflow", method = RequestMethod.POST)
	public int saveBusheetWorkflow(GrootBusheetWorkflow busheetWorkflow) {
		if(busheetWorkflow.getId() == null || "".equals(busheetWorkflow.getId())){
			busheetWorkflow.setId(com.web.utils.lang.StringUtils.randomUUID());
			return busheetWorkflowService.insert(busheetWorkflow);
		} else {
			return busheetWorkflowService.update(busheetWorkflow);
		}
	}
	
	
	/*
	 * 删除记录
	 */
	@RequiresPermissions("BusheetWorkflow:remove")
	@ResponseBody
	@RequestMapping(value = "/removeBusheetWorkflow", method = RequestMethod.POST)
	public int removeBusheetWorkflow(GrootBusheetWorkflow busheetWorkflow) {
		return busheetWorkflowService.removeByPrimaryKey(busheetWorkflow.getId());
	}
	
	
}
