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

import com.groot.module.domain.GrootSystemLookupType;
import com.groot.service.LookUpTypeService;
import com.groot.service.LookupCdeService;
import com.groot.service.SelectService;
import com.groot.service.SystemMonitor;
import com.groot.validation.LookUpTypeValidation;
import com.groot.vo.Constant;
import com.groot.vo.DataSet;
import com.web.bo.AjaxMessage;
import com.web.utils.lang.ArrayUtils;
import com.web.utils.lang.StringUtils;

@Controller
public class LookUpTypeController {

	@Autowired
	public SelectService selectService;
	@Autowired
	public LookUpTypeService lookUpTypeService;
	@Autowired
	public LookupCdeService lookupCdeService;
	@Autowired
	public LookUpTypeValidation lookUpTypeValidation;

	/**
	 * 用户配置页面跳转
	 * 
	 * @return
	 */
	@RequiresPermissions("LookupTypeManager:menu")
	@RequestMapping(value = "/lookup_type_manager")
	public ModelAndView lookup_type_manager() {
		return new ModelAndView("jsp/lookup_type_manager");
	}

//	/**
//	 * 用户配置页面跳转
//	 * 
//	 * @return
//	 */
//	@RequiresPermissions("LookupServiceManager:menu")
//	@RequestMapping(value = "/lookup_service_manager")
//	public ModelAndView lookup_service_manager() {
//		return new ModelAndView("jsp/lookup_service_manager");
//	}

	/**
	 * 查询字典列表(分页)
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getLookUpTypeListForPage", method = RequestMethod.POST)
	public DataSet<GrootSystemLookupType> getLookUpTypeListForPage(
			@RequestParam Map mapParam) {
		int pageNumber = Integer.valueOf(ObjectUtils.toString(mapParam
				.get("currentPage")));
		String countSqlId = "SYSTEM.getLookUpTypeCount";
		String listSqlId = "SYSTEM.getLookUpTypeListForPage";
		DataSet<GrootSystemLookupType> dataSet = this.selectService.getDataSet(countSqlId, listSqlId, mapParam, pageNumber, Constant.DEFAULT_PAGE_SIZE);
		return dataSet;
	}

//	/**
//	 * 查询字典列表(分页)
//	 * 
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping(value = "/getLookUpTypeServiceListForPage", method = RequestMethod.POST)
//	public DataSet<GrootSystemLookupType> getLookUpTypeServiceListForPage(
//			@RequestParam Map mapParam) {
//		mapParam.put("type", "10B");
//		int pageNumber = Integer.valueOf(ObjectUtils.toString(mapParam
//				.get("currentPage")));
//		String countSqlId = "SYSTEM.getLookUpTypeCount";
//		String listSqlId = "SYSTEM.getLookUpTypeListForPage";
//		DataSet<GrootSystemLookupType> dataSet = this.selectService.getDataSet(
//				countSqlId, listSqlId, mapParam, pageNumber,
//				Constant.DEFAULT_PAGE_SIZE);
//		return dataSet;
//	}

	/**
	 * 新增或修改字典域
	 * 
	 * @param lookupType
	 * @return
	 */
	@SystemMonitor(permission="LookupTypeManager:save")
	@RequiresPermissions("LookupTypeManager:save")
	@ResponseBody
	@RequestMapping(value = "/saveLookupType", method = RequestMethod.POST)
	public AjaxMessage saveLookupType(GrootSystemLookupType lookupType, BindingResult bresult) {
		AjaxMessage msg = new AjaxMessage();
		int result = 0;
		lookUpTypeValidation.validate(lookupType, bresult);
		if (bresult.hasErrors()) {
			msg.setContent(bresult.getFieldError().getDefaultMessage());
			return msg;
        }
		GrootSystemLookupType type = lookupCdeService.getLookupCdeByCode(lookupType.getTypeCode());
		if (type != null) {
			if (!type.getId().equals(lookupType.getId())) {
				msg.setCode("01");
				msg.setIsSuccess(false);
				msg.setContent("编号"+lookupType.getTypeCode()+"已经存在，请重新输入 ！");
				return msg;
			}
		}
		try{
			if (org.apache.commons.lang.StringUtils.isBlank(lookupType.getId())) {
				lookupType.setId(StringUtils.randomUUID());
				result = lookUpTypeService.insert(lookupType);
			} else {
				result = lookUpTypeService.update(lookupType);
			}
		}catch (Exception e) {
			e.printStackTrace();
			msg.setCode("01");
			msg.setIsSuccess(false);
			msg.setContent("操作异常！");
			return msg;
		}
		if(result > 0){
			msg.setIsSuccess(true);
			msg.setContent("操作成功！");
			//监控
			msg.setCode("00");
			Object[] objArray = null;
			objArray = (Object[]) ArrayUtils.addElement(Object.class, objArray, lookupType, true);
			msg.setObjArray(objArray);
		}else{
			msg.setCode("01");
			msg.setIsSuccess(false);
			msg.setContent("操作失败！");
		}
		return msg;
	}

	/*
	 * 删除记录
	 */
	@SystemMonitor(permission="LookupTypeManager:remove")
	@RequiresPermissions("LookupTypeManager:remove")
	@ResponseBody
	@RequestMapping(value = "/removeLookupType", method = RequestMethod.POST)
	public AjaxMessage removeLookupType(GrootSystemLookupType lookupType) {
		AjaxMessage message = new AjaxMessage();
		message.setCode("01");
		message.setValue("01");
		if(lookupType==null){
			message.setContent("非法操作");
		}
		int isLookupCde = lookupCdeService.selectByExample(lookupType);
		if (isLookupCde == 0) {
			message.setContent("请先删除该字典域的子级数据");
		}
		if (isLookupCde == 1) {
			int rows = lookUpTypeService.removeByPrimaryKey(lookupType.getId());
			if (rows > 0) {
				message.setCode("00");
				message.setValue("00");
				message.setContent("删除成功");
				Object[] objArray = null;
				objArray = (Object[]) ArrayUtils.addElement(Object.class, objArray, lookupType, true);
				message.setObjArray(objArray);
			} else {
				message.setContent("删除失败");
			}
		}
		return message;
	}

	/**
	 * 根据类型获得字典域
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getLookUpTypeList", method = RequestMethod.POST)
	public List<GrootSystemLookupType> getLookUpTypeList(String type) {
		return lookUpTypeService.getLookUpTypeList(type);
	}

	/**
	 * 获取全部字典
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getALLLookUpTypeList", method = RequestMethod.POST)
	public List<GrootSystemLookupType> getALLLookUpTypeList() {
		return lookUpTypeService.getAllLookUpTypeList();
	}


}
