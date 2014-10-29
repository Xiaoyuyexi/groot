package com.groot.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.groot.module.domain.GrootSystemLookupType;

public class LookUpTypeValidation implements Validator {

	@Override
	public boolean supports(Class<?>  c) {
		return GrootSystemLookupType.class.isAssignableFrom(c);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "typeCode", "lookupType.typeCode.required","编号不能为空");
		ValidationUtils.rejectIfEmpty(errors, "typeName", "lookupType.typeName.required","名称不能为空");
	}

}
