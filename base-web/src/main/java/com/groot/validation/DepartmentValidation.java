package com.groot.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.groot.module.domain.GrootSystemDepartment;

public class DepartmentValidation implements Validator {

	@Override
	public boolean supports(Class<?>  c) {
		return GrootSystemDepartment.class.isAssignableFrom(c);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "departmentName", "lookupCde.departmentName.required","名称不能为空");
		ValidationUtils.rejectIfEmpty(errors, "departmentCode", "lookupCde.departmentCode.required","编码不能为空");
		ValidationUtils.rejectIfEmpty(errors, "parentId", "lookupCde.parentId.required","父节点不能为空");
		
	}

}
