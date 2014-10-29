package com.groot.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.groot.service.SelectService;

@Controller
public class DataBaseController {

	@Autowired
	private SelectService   service;
	
	/**
	 *  用户配置页面跳转
	 * 
	 * @return
	 */
	@RequiresPermissions("DataBase:menu")
	@RequestMapping(value = "/database")
	public ModelAndView bankRouteManager() {
		return new ModelAndView("jsp/database");
	}
	
	@RequestMapping(value = "/databaseQuery")
	@ResponseBody
	public Object getResult(String sql,int currentPage,int pageSize){
		return service.processSql(sql,currentPage,pageSize);
	}
	
	@RequestMapping(value = "/databaseQueryAll")
	@ResponseBody
	public Object databaseQueryAll(String sql){
		return service.processSqlAll(sql);
	}
	
}
