package com.groot.controller;

import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.groot.module.domain.GrootBusheetPermission;
import com.groot.service.BusheetPermissionService;
import com.groot.service.SelectService;
import com.groot.vo.Constant;
import com.groot.vo.DataSet;
import com.web.utils.lang.StringUtils;


@Controller
public class BusheetPermissionController {
	@Autowired
	public BusheetPermissionService busheetPermissionService;
	
	@Autowired
	public SelectService selectService;
	
	
	/**
	 * 单据权限列表(分页)
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getBusheetPermissionListForPage", method = RequestMethod.POST)
	public DataSet<GrootBusheetPermission> getBusheetPermissionListForPage(@RequestParam Map<String, Object> mapParam) {
		int pageNumber = Integer.valueOf(ObjectUtils.toString(mapParam.get("currentPage")));
		String countSqlId = "SYSTEM.getBusheetPermissionCount";
		String listSqlId = "SYSTEM.getBusheetPermissionListForPage";
		DataSet<GrootBusheetPermission> dataSet = this.selectService.getDataSet(countSqlId, listSqlId, mapParam, pageNumber, Constant.DEFAULT_PAGE_SIZE);
		return dataSet;
	}
	
	/**
	 * 更新或者添加一条记录
	 * @param busheet
	 * @return
	 */
	@RequiresPermissions("BusheetPermissionManager:save")
	@ResponseBody
	@RequestMapping(value = "/saveBusheetPermission", method = RequestMethod.POST)
	public int saveBusheetPermission(GrootBusheetPermission busheet) {
		if(busheet.getUserId()==null || "".equals(busheet.getUserId())||busheet.getBusheetId()==null || "".equals(busheet.getBusheetId())){
			return -1;
		}
		if(busheetPermissionService.getBusheetPermissionByuserId(busheet)){
			return -2;
		}
		if(busheet.getId() == null || "".equals(busheet.getId())){
			busheet.setId(StringUtils.randomUUID());
			return busheetPermissionService.insert(busheet);
		} else {
			return busheetPermissionService.update(busheet);
		}
	}
	
	
	/*
	 * 删除记录
	 */
	@RequiresPermissions("BusheetPermissionManager:remove")
	@ResponseBody
	@RequestMapping(value = "/removeBusheetPermission", method = RequestMethod.POST)
	public int removeBusheetPermission(String  id) {
		return busheetPermissionService.removeByPrimaryKey(id);
	}
	
}
