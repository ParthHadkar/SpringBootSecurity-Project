package com.studentcrm.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.studentcrm.validation.RolesConstraintValidator;

@Constraint(validatedBy = RolesConstraintValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidRoles {

	// define default value
	public String[] value() default {};
	// define default error
	public String message() default "";
	// define default groups
	public Class<?>[] groups() default {};
	// define default payload
	public Class<? extends Payload>[] payload() default {};

}
