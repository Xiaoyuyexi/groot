package com.groot.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.groot.module.domain.GrootSystemConfig;
import com.groot.service.ConfigService;
import com.groot.service.SelectService;
import com.groot.service.SystemMonitor;
import com.groot.validation.ConfigValidation;
import com.groot.vo.Constant;
import com.groot.vo.DataSet;
import com.web.bo.AjaxMessage;
import com.web.utils.lang.ArrayUtils;

@Controller
public class ConfigController {
	protected static Logger logger4J = Logger.getLogger(ConfigController.class.getSimpleName());
	@Autowired
	public ConfigService configService;
	@Autowired
	public SelectService selectService;
	@Autowired
	public ConfigValidation configValidation;
	/**
	 *  跑批设置页面
	 * 
	 * @return
	 */
	@RequiresPermissions("ConfigManager:menu")
	@RequestMapping(value = "/config_manager")
	public ModelAndView ScopeTypeManager() {
		return new ModelAndView("jsp/config_manager");
	}
	
	/**
	 * 查询页面
	 * @param mapParam
	 * @param name
	 * @param config
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getConfigListForPage", method = RequestMethod.POST)
	public DataSet<GrootSystemConfig> getConfigListForPage(@RequestParam Map<String, Object> mapParam) {
		int pageNumber = Integer.valueOf(ObjectUtils.toString(mapParam.get("currentPage")));
		String countSqlId = "SYSTEM.getSystemConfigCount";
		String listSqlId = "SYSTEM.getSystemConfigListForPage";
		@SuppressWarnings("unchecked")
		DataSet<GrootSystemConfig> dataSet = this.selectService.getDataSet(countSqlId, listSqlId, mapParam, pageNumber, Constant.DEFAULT_PAGE_SIZE);
		return dataSet;
	}
	
	/**
	 * 新增或修改一条用户记录
	 * @param priceSpiderConfig
	 * @return
	 */
	@SystemMonitor(permission="ConfigManager:save")
	@RequiresPermissions("ConfigManager:save")
	@ResponseBody
	@RequestMapping(value = "/saveConfig", method = RequestMethod.POST)
	public AjaxMessage saveConfig(GrootSystemConfig systemConfig, BindingResult bresult) {
		AjaxMessage msg = new AjaxMessage();
		msg.setCode("01");
		
		configValidation.validate(systemConfig, bresult);
		if (bresult.hasErrors()) {
			msg.setContent(bresult.getFieldError().getDefaultMessage());
			return msg;
        }
		
		try {
			configService.saveOrUpdate(systemConfig);
			msg.setContent("保存成功!");
		} catch (Exception e) {
			e.printStackTrace();
			msg.setContent("保存异常!");
			return msg;
		}
		
		//监控
		msg.setCode("00");
		Object[] objArray = null;
		objArray = (Object[]) ArrayUtils.addElement(Object.class, objArray, systemConfig, true);
		msg.setObjArray(objArray);
		return msg;
	}
	
	

	/**
	 * 删除记录
	 */
	@SystemMonitor(permission="ConfigManager:remove")
	@RequiresPermissions("ConfigManager:remove")
	@ResponseBody
	@RequestMapping(value = "/removeConfig", method = RequestMethod.POST)
	public AjaxMessage removeConfig(GrootSystemConfig systemConfig) {
		AjaxMessage msg = new AjaxMessage();
		try{
			configService.removeByPrimaryKey(systemConfig.getId());
			msg.setContent("删除成功");
			//监控
			msg.setCode("00");
			Object[] objArray = null;
			objArray = (Object[]) ArrayUtils.addElement(Object.class, objArray, systemConfig, true);
			msg.setObjArray(objArray);
		}catch (Exception e) {
			e.printStackTrace();
			msg.setCode("01");
			msg.setContent("删除异常");
		}
		return msg;
	}
	
	/**
	 * 根据code获得配置
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/getConfigByCode")
	@ResponseBody
	public GrootSystemConfig getSystemConfig(String code){
		return configService.getSystemConfig(code);
	}
	
	/**
	 * 根据code获得配置
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/getVersionNumber")
	@ResponseBody
	public GrootSystemConfig getVersionSystemConfig(HttpSession session,String code){
		//放置版本号
		GrootSystemConfig ver = getSystemConfig(code);
		if(null != ver){
			session.setAttribute("version", ver.getConfigValue());
		}
		return ver;
	}
	
	
	
}
