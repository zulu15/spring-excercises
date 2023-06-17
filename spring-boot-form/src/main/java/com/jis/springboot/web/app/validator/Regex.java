package com.jis.springboot.web.app.validator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = RegexValidator.class)
@Retention(RUNTIME)
@Target({ FIELD, METHOD })
public @interface Regex {
	
	String message() default "El id ingresado no respeta el formato válido";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
}
