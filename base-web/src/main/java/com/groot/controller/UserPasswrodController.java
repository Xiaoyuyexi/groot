package com.groot.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.groot.module.domain.GrootSystemUser;
import com.groot.service.SystemMonitor;
import com.groot.service.UserManageService;
import com.groot.vo.UserVO;
import com.web.bo.AjaxMessage;
import com.web.utils.lang.ArrayUtils;
import com.web.utils.lang.SecurityUtils;

@Controller
public class UserPasswrodController {

	@Autowired
	public UserManageService userManageService;
//	@Autowired
//	public UserPasswordService userPasswordService;
	
	/**
	 *  用户配置页面跳转
	 * 
	 * @return
	 */
	@RequiresPermissions("PasswordManager:menu")
	@RequestMapping(value = "/user_password_reset")
	public ModelAndView userManager() {
		return new ModelAndView("jsp/user_password_reset");
	}
	
	@ResponseBody
	@RequestMapping(value = "/getUserInfo")
	public UserVO getUserInfo(String userName) {
		List<UserVO> users = userManageService.getUser(userName);
		if (users != null && users.size() > 0) {
			return users.get(0);
		}
		return null;
	}
	
	@SystemMonitor(permission="UserPassword:update")
	@RequiresPermissions("UserPassword:update")
	@ResponseBody
	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	public AjaxMessage saveChinaArea(UserVO user, HttpSession session) {
		AjaxMessage message = new AjaxMessage();
		message.setCode("01");
		message.setValue("0");
		if (user != null) {
			GrootSystemUser systemUser = new GrootSystemUser();
			systemUser.setId(user.getUserId());
			systemUser.setPassword(SecurityUtils.EncoderByMd5(user.getUserPassword()));
			GrootSystemUser currentUser = (GrootSystemUser)session.getAttribute("USER_SESSION_KEY");
			int result = userManageService.update(systemUser, currentUser);
			message.setValue(String.valueOf(result));
			//监控程序
			if(result > 0){
				message.setCode("00");
				Object[] objArray = null;
				objArray = (Object[]) ArrayUtils.addElement(Object.class, objArray, systemUser, true);
				message.setObjArray(objArray);
			}
			
		}
		return message;
	}
}
