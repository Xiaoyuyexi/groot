package com.groot.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.groot.module.domain.GrootSystemPermission;
import com.groot.module.domain.GrootSystemRolePermission;
import com.groot.service.PermissionManageService;
import com.groot.service.RoleManagerService;
import com.groot.service.SelectService;
import com.groot.vo.DataSet;
import com.web.utils.lang.StringUtils;


@Controller
public class PermissionManageController {
	@Autowired
	public PermissionManageService permissionManageService;
	@Autowired
	public SelectService selectSerive;
	@Autowired
	public RoleManagerService roleManagerService;
	
	public PermissionManageService getPermissionManageService() {
		return permissionManageService;
	}

	public void setPermissionManageService(
			PermissionManageService permissionManageService) {
		this.permissionManageService = permissionManageService;
	}

	/**
	 * 页面跳转
	 * 
	 * @return
	 */
	@RequiresPermissions("PermissionManager:menu")
	@RequestMapping(value = "/permission_manager")
	public ModelAndView purviewManager() {
		return new ModelAndView("jsp/permission_manager");
	}

	@ResponseBody
	@RequestMapping(value = "/getAllPermission")
	public List<GrootSystemPermission> getAllPermission() {
		return permissionManageService.getAllPermissionList();
	}
	@ResponseBody
	@RequestMapping(value = "/getotherPermissionList")
	public List<GrootSystemPermission> getotherPermissionList(String id) {
		return permissionManageService.getotherPermissionList(id);
	}
	
	@ResponseBody
	@RequestMapping(value = "/getPermissionListForPage", method = RequestMethod.POST)
	public DataSet getPurviewListForPage(@RequestParam Map mapParam) {
		int pageNumber = Integer.valueOf(ObjectUtils.toString(mapParam.get("currentPage")));
		int size = 10;
		String countSqlId = "SYSTEM.getPermissionCount";
		String listSqlId = "SYSTEM.getPermissionListForPage";
		DataSet <GrootSystemPermission> dataSet = this.selectSerive.getDataSet(countSqlId,listSqlId, mapParam, pageNumber, size);
		return dataSet;
	}
	
	@RequiresPermissions("PermissionManager:save")
	@ResponseBody
	@RequestMapping(value = "/savePermission", method = RequestMethod.POST)
	public GrootSystemPermission savePermission(GrootSystemPermission permission) {
		GrootSystemPermission record = this.permissionManageService.selectPermissionById(permission.getId());
		if(permissionManageService.isCode(permission.getPermissionCode(),permission.getId())){
			permission.setId("2000");
			return permission;
		}
		try {
			if (record == null) {
				if(permissionManageService.canInsert(permission.getParentId())){
					permission.setId(StringUtils.randomUUID());
					this.permissionManageService.insertPermission(permission);
				}else{
					permission.setId("1000");
					return permission;
				}
			} else {
				this.permissionManageService.updatePermission(permission);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return permission;
	}
	
	/*
	 *删除权限
	 */
	@RequiresPermissions("PermissionManager:remove")
	@ResponseBody
	@RequestMapping(value = "/removePermission", method = RequestMethod.POST)
	public String removePermission(GrootSystemPermission permission) {
		List<GrootSystemRolePermission> ls=roleManagerService.selectRoleNameByPermissionId(permission.getId());
		String result="";
		if(ls!=null&&ls.size()>0){
			for (int i = 0; i < ls.size(); i++) {
				if(i==0){
					result=ls.get(i).getRoleId();
				}else{
					result=result+","+ls.get(i).getRoleId();
				}
			}
			return result;
		}
		return permissionManageService.deletePermission(permission)+"";
	}
	/**
	 * 根据父级获取所有子级
	 * @param ParentId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getPermissionByParentId", method = RequestMethod.POST)
	public List<GrootSystemPermission> getPermissionByParentId(String ParentId){
		return permissionManageService.getPermissionByParentId(ParentId);
	}

}