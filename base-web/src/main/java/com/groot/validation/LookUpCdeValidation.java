package com.groot.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.groot.module.domain.GrootSystemLookupCde;

public class LookUpCdeValidation implements Validator {

	@Override
	public boolean supports(Class<?>  c) {
		return GrootSystemLookupCde.class.isAssignableFrom(c);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "cdeName", "lookupCde.cdeName.required","名称不能为空");
//		ValidationUtils.rejectIfEmpty(errors, "cdeCode", "lookupCde.cdeCode.required","编码不能为空");
		ValidationUtils.rejectIfEmpty(errors, "typeId", "lookupCde.typeId.required","类型不能为空");
	}

}
