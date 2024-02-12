package ca.est.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import ca.est.annotation.impl.ScriptProtectionImpl;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;


/**
 * This annotation used for SQL Injection and XSS Protection
 * How to use: 
 * @ScriptProtection(message = "INVALID_CRITERIA")
 * private Object criteria; 
 * 
 * @author Estevam Meneses May 11, 2023
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@Constraint(validatedBy = ScriptProtectionImpl.class)
public @interface ScriptProtection {
	String message() default "";
	Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
