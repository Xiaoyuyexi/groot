package com.groot.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.groot.module.domain.GrootSystemUser;

public class LoginValidation implements Validator{

	@Override
	public boolean supports(Class<?> user) {
		return GrootSystemUser.class.isAssignableFrom(user);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "loginName", "user.loginName.required","用户名不能为空");
		ValidationUtils.rejectIfEmpty(errors, "password", "user.password.required","用户密码不能为空");
		
	}

}

