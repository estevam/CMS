/**
 * 
 */
package ca.est.annotation.impl;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ca.est.annotation.ScriptProtection;

/**
 * @author Estevam Meneses May 11, 2023
 *
 */
@Component
public class ScriptProtectionImpl implements ConstraintValidator<ScriptProtection, Object> {
	private static final Logger log = LoggerFactory.getLogger(ScriptProtectionImpl.class);

	@Autowired
	private XSSValidationImpl xssValidationImpl;

	@Autowired
	private SqlInjectionImpl sqlInjectionImpl;

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		try {
			if (xssValidationImpl.isValid(value, context)) {
				if (sqlInjectionImpl.isValid(value)) {
					return true;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.error("Error on validate SQL Injection or XSS:{} ",e);
		}
		return false;
	}
}