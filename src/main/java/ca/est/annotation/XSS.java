package ca.est.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import ca.est.annotation.impl.XSSValidationImpl;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * This annotation used for XSS Protection
 * How to use: 
 * @XSS(message = "INVALID_CRITERIA")
 * private Object criteria;
 * @since Feb 07, 2023
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@Constraint(validatedBy = XSSValidationImpl.class)
public @interface XSS {
	String message() default "";
	Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}