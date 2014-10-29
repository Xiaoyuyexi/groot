package com.groot.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.groot.module.domain.GrootSystemConfig;


public class ConfigValidation implements Validator{

	@Override
	public boolean supports(Class<?> config) {
		return GrootSystemConfig.class.isAssignableFrom(config);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "configCode", "user.configCode.required","变量代码不能为空");
		ValidationUtils.rejectIfEmpty(errors, "configName", "user.configName.required","变量名称不能为空");
		ValidationUtils.rejectIfEmpty(errors, "configValue", "user.configValue.required","变量值不能为空");
		
		
	}

}

