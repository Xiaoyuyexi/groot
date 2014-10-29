package com.groot.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.groot.module.domain.GrootSystemDepartment;
import com.groot.service.DepartmentService;
import com.groot.service.SelectService;
import com.groot.validation.DepartmentValidation;
import com.web.bo.AjaxMessage;

@Controller
public class DepartmentController {
	@Autowired
	public SelectService<GrootSystemDepartment> selectService;
	@Autowired
	public DepartmentService departmentService;
	@Autowired
	public DepartmentValidation departmentValidation;
	/**
	 *  用户配置页面跳转
	 * 
	 * @return
	 */
	@RequiresPermissions("DepartmentManager:menu")
	@RequestMapping(value = "/department_manager")
	public ModelAndView lookUpCdeManager() {
		return new ModelAndView("jsp/department_manager");
	}
	/**
	 * 获取全部组织机构
	 */
	@ResponseBody
	@RequestMapping(value = "/getAllDepartment", method = RequestMethod.POST)
	public List<GrootSystemDepartment> getAllDepartment() {
		return departmentService.getAllDepartment();
	}
	
	/**
	 * 更新或添加一个组织结构
	 * @param department
	 * @return
	 */
	@RequiresPermissions("DepartmentManager:save")
	@ResponseBody
	@RequestMapping(value = "/saveDepartment", method = RequestMethod.POST)
	public AjaxMessage saveDepartment(GrootSystemDepartment department, BindingResult bresult) {
		AjaxMessage msg = new AjaxMessage();
		msg.setIsSuccess(false);
		int departmentNum = 0;
		
		departmentValidation.validate(department, bresult);
		if (bresult.hasErrors()) {
			msg.setContent(bresult.getFieldError().getDefaultMessage());
			return msg;
        }
		
		if(departmentService.isUserId(department.getUserId(),department.getId())){
			msg.setContent("此用户已使用请重新选择");
			return msg;
		}
		List<GrootSystemDepartment> list = departmentService.getDepartmentByCode(department.getDepartmentCode());
		departmentNum = list.size();
		
		try{
			if(StringUtils.isBlank(department.getId())){
				
				if(departmentNum > 0){
					msg.setContent("code已存在");
					return msg;
				}
				department = departmentService.insert(department);
			}else{
				if(departmentNum > 1){
					msg.setContent("code重复");
					return msg;
				}
				department = departmentService.update(department);
			}
			msg.setIsSuccess(true);
			msg.setContent("保存成功");
			msg.setObj(department);
		}catch (Exception e) {
			e.printStackTrace();
			msg.setContent("保存异常");
		}
		return msg;
	}
	/**
	 * 删除一个组织结构
	 * @param id
	 * @return
	 */
	@RequiresPermissions("DepartmentManager:remove")
	@ResponseBody
	@RequestMapping(value = "/removeDepartment", method = RequestMethod.POST)
	public int removeDepartment(String id){
		return departmentService.removeByPrimaryKey(id);
	}
	
	/**
	 * 根据父级查询所有的子级
	 * @param parentId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getDepartmentByParentId", method = RequestMethod.POST)
	public List<GrootSystemDepartment> getDepartmentByParentId(String parentId){
		return departmentService.getDepartmentByParentId(parentId);
	}
	
	
	
	@ResponseBody
	@RequestMapping(value = "/getDepartmentByCode", method = RequestMethod.POST)
	public GrootSystemDepartment getDepartmentByCode(String code) {
		if (StringUtils.isEmpty(code)) {
			return null;
		}
		List<GrootSystemDepartment> list = departmentService.getDepartmentByCode(code);
		if(list.size() > 0){
			return list.get(0);
		}
		return null;
	}
}
