package com.jis.springboot.web.app.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RegexValidator implements ConstraintValidator<Regex, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		return value.matches("[0-9]{2}[.,][0-9]{3}[.,][0-9]{3}[-][A-Z]{1}");
	}

}
