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

import com.groot.module.domain.GrootSystemRole;
import com.groot.module.domain.GrootSystemUser;
import com.groot.service.RoleManagerService;
import com.groot.service.SelectService;
import com.groot.service.UserManageService;
import com.groot.vo.DataSet;
import com.groot.vo.RolePermissionVO;
import com.web.bo.AjaxMessage;


@Controller
public class RoleManagerController {
//
	@Autowired
	public SelectService selectService;
	@Autowired
	public RoleManagerService roleManagerService;
	@Autowired
	public UserManageService userManageService;

	/**
	 * 页面跳转
	 * 
	 * @return
	 */
	@RequiresPermissions("RoleManager:menu")
	@RequestMapping(value = "/role_manager")
	public ModelAndView role_manager() {
		return new ModelAndView("jsp/role_manager");
	}
	/*
	@RequiresPermissions("RoleManager:view")*/
	@ResponseBody
	@RequestMapping(value = "/getRoleListForPage", method = RequestMethod.POST)
	public DataSet<GrootSystemRole> getUserListForPage(@RequestParam Map mapParam) {
		int currentPage = Integer.valueOf(ObjectUtils.toString(mapParam.get("currentPage")));
		int size = 10;
		String countSqlId = "SYSTEM.getRoleCount";
		String listSqlId = "SYSTEM.getRoleListForPage";
		DataSet<GrootSystemRole> dataSet = this.selectService.getDataSet(countSqlId, listSqlId, mapParam, currentPage, size);
		return dataSet;
	}
	/**
	 * 角包列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getRoleList", method = RequestMethod.POST)
	public List<GrootSystemRole> getRoleList(@RequestParam Map mapParam) {
		return roleManagerService.getRoleList();
	}
	/**
	 * 保存角色
	 * @param priceSpiderRole
	 * @return
	 */
	@RequiresPermissions("RoleManager:save")
	@ResponseBody
	@RequestMapping(value = "/saveRole", method = RequestMethod.POST)
	public AjaxMessage insertOrUpdateRole(GrootSystemRole systemRole) {
		AjaxMessage msg = new AjaxMessage();
		msg.setCode("01");
		if (roleManagerService.checkRoleNameIsExetis(systemRole)) {
			msg.setContent("角色名称已存在");
			return msg;
		}
		int result = 0;
		GrootSystemRole role = roleManagerService.getRoleByPrimaryKey(systemRole.getId());
		if (role != null) {
			//修改
			role.setId(systemRole.getId());
			if(systemRole.getId() == null){
				role.setId("");
			}else{
				role.setId(systemRole.getId());
			}
			role.setName(systemRole.getName());
			result = roleManagerService.update(role);
		} else {
			//新增
			role = new GrootSystemRole();
			role.setId(com.web.utils.lang.StringUtils.randomUUID());
			//新增一条记录，密码默认为加密后的用户名
			role.setName(systemRole.getName());
			result = roleManagerService.insert(role);
		}
		if (result > 0) {
			msg.setCode("00");
			msg.setContent("保存成功");
		} else {
			msg.setContent("保存失败");
		}
		return msg;
	}
	/*
	 *删除记录
	 */
	@RequiresPermissions("RoleManager:remove")
	@ResponseBody
	@RequestMapping(value = "/removeRole", method = RequestMethod.POST)
	public int removeRole(GrootSystemRole systemRole) {
		//删除角色前先判断该角色是否被用户绑定，如果绑定提示不能删除 update by xiejingjing 2013-05-20
		List<GrootSystemUser> userList = null;
		try {
			userList = userManageService.selectSystemUserByRoleId(systemRole.getId());
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		if(userList!=null&&userList.size()>0){
			return 0;
		}
		//先删除此角色权限表配置的权限
	//	roleManagerService.deletePurviewRoleRefByRoleId(priceSpiderRole.getId());
		//再删除此角色
		return roleManagerService.removeRoleById(systemRole.getId());
	}
	/**
	 * 查询角色及其配置的权限
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getRolePermissionList", method = RequestMethod.POST)
	public List<RolePermissionVO> getRolePermissionList(String roleId) {
		Map conditions = new java.util.HashMap();
		conditions.put("roleId", roleId);
		return roleManagerService.getRolePermissionList(conditions);
	}

	/**
	 * 根据roleId查询该用户所有没有配置的权限
	 */
	@ResponseBody
	@RequestMapping(value = "/getPermissionListNoSitedByRoleId", method = RequestMethod.POST)
	public List<GrootSystemRole> getPermissionListNoSitedByRoleId(String roleId) {
		Map conditions = new java.util.HashMap();
		conditions.put("roleId", roleId);
		return roleManagerService.getPermissionListNoSitedByRoleId(conditions);
	}


	/**
	 * 保存角色配置的权限
	 * @param ref
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveRolePermission", method = RequestMethod.POST)
	public String saveRolePermission(RolePermissionVO rolePermissionVO) {
		
		this.roleManagerService.insertRolePermission(rolePermissionVO);
		/*PriceSpiderRole record = null;
		if(StringUtils.isNotBlank(rolePermissionVO.getRolePermissionId())){
			record = this.roleManagerService.selectRoleById(ref.getId());
		}
		try{
			if (record == null) {
				ref.setId(Utils.randomUUID());
				this.roleManagerService.insertRolePurview(ref);
			} else {
				this.roleManagerService.updateRolePurview(ref);
			}
		}catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return 1;*/
		return rolePermissionVO.getRolePermissionId();
	}
	
	/**
	 * 保存角色配置的权限
	 * @param ref
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/saveRolePermissionBatch", method = RequestMethod.POST)
	public Object saveRolePermissionBatch(String roleId,String permissionId) {
		return roleManagerService.batchInsert(roleId, permissionId);
	}
	
	/**
	 * 保存角色配置的权限
	 * @param ref
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/removeRolePermissionBatch", method = RequestMethod.POST)
	public Object removeRolePermissionBatch(String rolePermissionId) {
		return roleManagerService.batchDelete(rolePermissionId);
	}
	
	/**
	 * 删除角色配置的权限
	 * @param ref
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/removeRolePermission", method = RequestMethod.POST)
	public int removeRolePermission(RolePermissionVO rolePermissionVO) {
		return roleManagerService.removeRolePermission(rolePermissionVO);
	}
}
