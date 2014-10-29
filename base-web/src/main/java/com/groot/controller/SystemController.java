package com.groot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.groot.service.SystemConfigService;

/**
 * 系统控制器
 * @author admin
 *
 */
@Controller
public class SystemController {

	@Autowired
	private SystemConfigService systemConfigService;
	
	/**
	 * 获取功能展示状态
	 * @param code
	 * @return
	 */
	@RequestMapping(value="/getSystemConfig")
	@ResponseBody
	public Object getSystemConfig(String code){
		return systemConfigService.getSystemBooleanValue(code);
	}
}
