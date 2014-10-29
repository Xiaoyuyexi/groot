package com.groot.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.groot.module.domain.GrootSystemUser;

public class UserValidation implements Validator{

	@Override
	public boolean supports(Class<?> user) {
		return GrootSystemUser.class.isAssignableFrom(user);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "loginName", "user.loginName.required","用户名不能为空");
		ValidationUtils.rejectIfEmpty(errors, "roleId", "user.roleId.required","角色不能为空");
		ValidationUtils.rejectIfEmpty(errors, "status", "user.status.required","状态不能为空");
	}

}

