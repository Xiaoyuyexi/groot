package com.groot.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.groot.module.domain.GrootSystemLookupCde;
import com.groot.service.LookupCdeService;
import com.groot.service.SelectService;
import com.groot.service.SystemMonitor;
import com.groot.validation.LookUpCdeValidation;
import com.groot.vo.Constant;
import com.groot.vo.DataSet;
import com.web.bo.AjaxMessage;
import com.web.utils.lang.ArrayUtils;
@Controller
public class LookUpCdeController {
	@Autowired
	public SelectService selectService;
	@Autowired
	public LookupCdeService  lookupCdeService;
	@Autowired
	public LookUpCdeValidation  lookUpCdeValidation;
	
	
	/**
	 *  用户配置页面跳转
	 * 
	 * @return
	 */
	@RequiresPermissions("LookupCdeManager:menu")
	@RequestMapping(value = "/lookup_cde_manager")
	public ModelAndView lookUpCdeManager() {
		return new ModelAndView("jsp/lookup_type_manager");
	}
	
	/**
	 * 查询字典列表(分页)
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getLookUpCdeListForPage", method = RequestMethod.POST)
	public DataSet<GrootSystemLookupCde> getLookUpCdeListForPage(@RequestParam Map<String, Object> mapParam) {
		int pageNumber = Integer.valueOf(ObjectUtils.toString(mapParam.get("currentPage")));
		String countSqlId = "SYSTEM.getLookUpCdeCount";
		String listSqlId = "SYSTEM.getLookUpCdeList";
		DataSet<GrootSystemLookupCde> dataSet = this.selectService.getDataSet(countSqlId, listSqlId, mapParam, pageNumber, Constant.DEFAULT_PAGE_SIZE);
		return dataSet;
	}
	
	/**
	 * 更新或者添加一条数据字典
	 * @param lookupCde
	 * @return
	 */
	@SystemMonitor(permission="LookupCdeManager:save")
	@RequiresPermissions("LookupCdeManager:save")
	@ResponseBody
	@RequestMapping(value = "/saveLookupCde", method = RequestMethod.POST)
	public AjaxMessage saveLookupCde(GrootSystemLookupCde lookupCde, BindingResult bresult) {
		AjaxMessage msg = new AjaxMessage();
		lookUpCdeValidation.validate(lookupCde, bresult);
		if (bresult.hasErrors()) {
			msg.setContent(bresult.getFieldError().getDefaultMessage());
			return msg;
        }
		try{
			if(lookupCde.getId() == null){
				lookupCde.setId(com.web.utils.lang.StringUtils.randomUUID());
				lookupCdeService.insert(lookupCde);
			}else{
				lookupCdeService.update(lookupCde);
			}
			msg.setIsSuccess(true);
			msg.setContent("保存成功！");
		}catch (Exception e) {
			e.printStackTrace();
			msg.setCode("01");
			msg.setIsSuccess(false);
			msg.setContent("保存异常！");
			return msg;
		}
		//监控
		msg.setCode("00");
		Object[] objArray = null;
		objArray = (Object[]) ArrayUtils.addElement(Object.class, objArray, lookupCde, true);
		msg.setObjArray(objArray);
		return msg;
	}
	
	/*
	 * 删除记录
	 */
	@SystemMonitor(permission="LookupCdeManager:remove")
	@RequiresPermissions("LookupCdeManager:remove")
	@ResponseBody
	@RequestMapping(value = "/removeLookupCde", method = RequestMethod.POST)
	public AjaxMessage removeLookupCde(GrootSystemLookupCde lookupCde) {
		AjaxMessage msg = new AjaxMessage();
		int result = lookupCdeService.removeByPrimaryKey(lookupCde.getId());
		msg.setValue(String.valueOf(result));
		//监控
		if(result > 0){
			msg.setCode("00");
			Object[] objArray = null;
			objArray = (Object[]) ArrayUtils.addElement(Object.class, objArray, lookupCde, true);
			msg.setObjArray(objArray);
		}else{
			msg.setCode("01");
		}
		return msg;
	}
	
	/**
	 *  获取某个域的字典
	 */
	@ResponseBody
	@RequestMapping(value = "/getLookupCdeList", method = RequestMethod.POST)
	public List<GrootSystemLookupCde> getLookupCdeList(@RequestParam Map<String, Object> mapParam) {
		return lookupCdeService.getLookupCdeList(mapParam);
		
	}
	
}
