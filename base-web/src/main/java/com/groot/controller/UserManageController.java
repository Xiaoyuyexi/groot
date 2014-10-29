package com.groot.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

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

import com.groot.module.domain.GrootSystemRole;
import com.groot.module.domain.GrootSystemUser;
import com.groot.service.RoleManagerService;
import com.groot.service.SelectService;
import com.groot.service.ShiroDbRealm;
import com.groot.service.UserManageService;
import com.groot.validation.UserValidation;
import com.groot.vo.Constant;
import com.groot.vo.DataSet;
import com.web.bo.AjaxMessage;


@Controller
public class UserManageController {

	@Autowired
	public SelectService selectService;
	@Autowired
	public UserManageService userManageService;
	@Autowired
	public RoleManagerService roleService;
	@Autowired
	public ShiroDbRealm shiroDbRealm;
	@Autowired
	public UserValidation userValidation;
	/**
	 *  用户配置页面跳转
	 * 
	 * @return
	 */
	@RequiresPermissions("UserManager:menu")
	@RequestMapping(value = "/user_manager")
	public ModelAndView userManager() {
		return new ModelAndView("jsp/user_manager");
	}
	
	/**
	 * 查询用户列表(分页)
	 * @return
	 */
	/*@RequiresPermissions("UserManager:view")*/
	@ResponseBody
	@RequestMapping(value = "/getUserListForPage", method = RequestMethod.POST)
	/*@Cacheable(cacheName = "messagesCache")*/
	public DataSet<GrootSystemUser> getUserListForPage(@RequestParam Map<String, Object> mapParam) {
		int pageNumber = Integer.valueOf(ObjectUtils.toString(mapParam.get("currentPage")));
		String loginName = ObjectUtils.toString(mapParam.get("loginName"));
		mapParam.put("loginName", loginName);
		String countSqlId = "SYSTEM.getUserCount";
		String listSqlId = "SYSTEM.getUserListForPage";
		@SuppressWarnings("unchecked")
		DataSet<GrootSystemUser> dataSet = this.selectService.getDataSet(countSqlId, listSqlId, mapParam, pageNumber, Constant.DEFAULT_PAGE_SIZE);
		return dataSet;
	}
	

	/**
	 * 新增或修改一条用户记录
	 * @param priceSpiderUser
	 * @return
	 */
	@RequiresPermissions("UserManager:save")
	@ResponseBody
	@RequestMapping(value = "/saveUser", method = RequestMethod.POST)
	/*@TriggersRemove(cacheName = "messagesCache", when = When.AFTER_METHOD_INVOCATION, removeAll = true)*/
	public Object saveUser(GrootSystemUser systemUser, HttpSession session, BindingResult bresult) {
		AjaxMessage msg = new AjaxMessage();
		msg.setCode("01");

		userValidation.validate(systemUser, bresult);
		if (bresult.hasErrors()) {
			msg.setContent(bresult.getFieldError().getDefaultMessage());
			return msg;
        }
		
		if(!userManageService.validateUser(msg,systemUser))return msg;
		
		try{
			GrootSystemUser currentUser = (GrootSystemUser)session.getAttribute("USER_SESSION_KEY");
			if(systemUser.getId() == null){
				userManageService.insert(systemUser, currentUser);
			}else{
				userManageService.update(systemUser, currentUser);
				//删除shiro缓存中该用户的权限缓存
				shiroDbRealm.clearCachedAuthorizationInfo(systemUser.getLoginName());
			}
			msg.setCode("00");
			msg.setContent("保存成功！");
		}catch (Exception e) {
			e.printStackTrace();
			msg.setContent("保存异常！");
		}
		
		return msg;
	}

	/*
	 * 删除记录
	 */
	@RequiresPermissions("UserManager:remove")
	@ResponseBody
	@RequestMapping(value = "/removeUser", method = RequestMethod.POST)
	/*@TriggersRemove(cacheName = "messagesCache", when = When.AFTER_METHOD_INVOCATION, removeAll = true)*/
	public int removeUser(GrootSystemUser systemUser) {
		//先删除此用户权限表配置的权限
	//	userManageService.deletePurviewUserRefByUserId(priceSpiderUser.getId());
		//删除shiroEhcache缓存中该用户的权限缓存
		shiroDbRealm.clearCachedAuthorizationInfo(systemUser.getLoginName());
		//再删除此用户
		return userManageService.removeByPrimaryKey(systemUser.getId());
	}
	
	@ResponseBody
	@RequestMapping(value = "/getAllSystemUser")
	public List<GrootSystemUser>  getAllSystemUser(){
		return userManageService.getAllSystemUser();
	}
	@ResponseBody
	@RequestMapping(value = "/getUserByState")
	public List<GrootSystemUser>  getUserByState(){
		return userManageService.getUserByState();
	}

	@ResponseBody
	@RequestMapping(value="/getRoleNameByUser" , method = RequestMethod.POST)
	public AjaxMessage getRoleNameByUser(String id) {
		AjaxMessage message = new AjaxMessage();
		message.setCode("01");
		GrootSystemUser user = userManageService.selectUserById(id);
		if (user == null) {
			return message;
		}
		GrootSystemRole role = roleService.getRoleByPrimaryKey(user.getRoleId());
		if (role == null) {
			return message;
		}
		message.setCode("00");
		message.setValue(role.getName());
		return message;
	}
	
}
