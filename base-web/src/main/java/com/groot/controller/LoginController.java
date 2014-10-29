package com.groot.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.groot.module.domain.GrootSystemRole;
import com.groot.module.domain.GrootSystemUser;
import com.groot.service.PermissionManageService;
import com.groot.service.RoleManagerService;
import com.groot.service.SystemConfigService;
import com.groot.validation.LoginValidation;
import com.groot.vo.Constant;
import com.web.bo.AjaxMessage;
@Controller
public class LoginController{
	@Autowired
	public RoleManagerService  roleService;
	@Autowired
	public SystemConfigService systemConfigService;
	@Autowired
	public PermissionManageService permissionService;
	
	@Autowired
    private LoginValidation loginValidation;
	
	@RequestMapping(value="/welcome")
	public ModelAndView welcome(String msg,String loginName) throws UnsupportedEncodingException{
		AjaxMessage message = new AjaxMessage();
		message.setStatus(systemConfigService.getSystemBooleanValue("10000003"));
		if (msg != null) {
			msg = URLDecoder.decode(msg, "utf8");
			message.setValue(msg);
		}
		if(loginName != null){
			loginName = URLDecoder.decode(loginName, "utf8");
			message.setContent(loginName);
		}
		return new ModelAndView("jsp/login","MSG",message);
	}
	
	@RequestMapping(value="/index")
	public ModelAndView main(){
		GrootSystemUser systemUser = (GrootSystemUser)SecurityUtils.getSubject().getSession().getAttribute(Constant.USER_SESSION_KEY);
		GrootSystemRole role = roleService.getRoleByPrimaryKey(systemUser.getRoleId());
		systemUser.setRoleId(role.getName());
		return new ModelAndView("jsp/index","systemUser",systemUser);
	}
	
	
	@RequestMapping(value="/main")
	public ModelAndView login(@ModelAttribute("systemUser") GrootSystemUser systemUser,BindingResult result, HttpSession session,String kaptcha){
		AjaxMessage message = new AjaxMessage();
		Map<String, String> map = new HashMap<String, String>();
		message.setStatus(systemConfigService.getSystemBooleanValue("10000003"));
		message.setObj(systemUser);
		String loginName = systemUser.getLoginName();
//		try {
//			 loginName= URLEncoder.encode(systemUser.getLoginName(),"utf8");
//		} catch (UnsupportedEncodingException e2) {
//			e2.printStackTrace();
//		}
		loginValidation.validate(systemUser, result);
		if (result.hasErrors()) {
			message.setValue(result.getFieldError().getDefaultMessage());
			try {
				if(StringUtils.isNotEmpty(message.getValue())){
					map.put("msg", URLEncoder.encode(message.getValue(), "utf8"));
				}
				if(StringUtils.isNotEmpty(loginName)){
					map.put("loginName", URLEncoder.encode(loginName, "utf8"));
				}
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			return new ModelAndView(new RedirectView("welcome.jhtml"),map);
        }
//		if(systemUser.getLoginName() == null && systemUser.getPassword()==null){
//			return new ModelAndView("jsp/login","E_MSG","请输入用户名及密码");
//		}
		UsernamePasswordToken token= new UsernamePasswordToken(systemUser.getLoginName(),
				com.web.utils.lang.SecurityUtils.EncoderByMd5(systemUser.getPassword()));
		try{
			GrootSystemUser system=roleService.selectSystemUser(systemUser.getLoginName(),com.web.utils.lang.SecurityUtils.EncoderByMd5(systemUser.getPassword()));
			if(system!=null){
				systemUser.setRoleId(system.getRoleId());
			}
			if (permissionService.getPermissionCountByRule(system.getRoleId()) <= 0) {
				message.setValue("无任何权限,禁止登录");
				try {
					map.put("msg", URLEncoder.encode(message.getValue(), "utf8"));
					map.put("loginName", URLEncoder.encode(loginName, "utf8"));
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}
				return new ModelAndView(new RedirectView("welcome.jhtml"),map);
			}
			if(!systemConfigService.validateKaptchaImage(session, kaptcha, message)){
				try {
					map.put("msg", URLEncoder.encode(message.getValue(), "utf8"));
					map.put("loginName", URLEncoder.encode(loginName, "utf8"));
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}
				return new ModelAndView(new RedirectView("welcome.jhtml"),map);
			}
			Subject user = SecurityUtils.getSubject();
			user.login(token);
			return new ModelAndView(new RedirectView("index.jhtml"));
		}catch(Exception e){
			System.out.println(e);
			message.setValue("用户名或密码不正确");
			try {
				map.put("msg", URLEncoder.encode(message.getValue(), "utf8"));
				map.put("loginName", URLEncoder.encode(loginName, "utf8"));
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			return new ModelAndView(new RedirectView("welcome.jhtml"),map);
		}
	}
	@RequestMapping(value="/logout")
	public String logout(HttpServletRequest request,  
            HttpServletResponse response,HttpSession session){
//		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate, max-age=0, proxy-revalidate, no-transform, private");
//		response.setHeader("Pragma", "no-cache");
		SecurityUtils.getSubject().logout();
		return "redirect:welcome.jhtml";  
	}
	
	@RequestMapping(value="/validateLogoff")
	@ResponseBody
	public Object validateLogoff(HttpSession session, HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		AjaxMessage  msg = new AjaxMessage("true");
		return msg;
	}
	
}
