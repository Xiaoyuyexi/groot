package com.groot.controller;

import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
public class PermissionTagController {
	@ResponseBody
	@RequestMapping(value = "/isPermitted", method = RequestMethod.GET)
	public boolean isPermitted(String permissionName) {
		return SecurityUtils.getSubject() != null && SecurityUtils.getSubject().isPermitted(permissionName);
    }
}
