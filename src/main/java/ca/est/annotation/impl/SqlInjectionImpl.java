package ca.est.annotation.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ca.est.util.SqlInjectionUtil;


/**
 *
 * Implementation for the for SQL Injection protection for object data type 
 * @author Estevam Meneses May 10, 2023
 *
 */
@Component
public class SqlInjectionImpl{

	private static final Logger log = LoggerFactory.getLogger(SqlInjectionImpl.class);

	// pre-build the Pattern objects for faster validation
	private static final List<Pattern> validationPatterns = buildPatterns(SqlInjectionUtil.SQL_REGEX);

	/**
	 * 
	 * @author Estevam.Meneses May 10, 2023
	 * 
	 * @param pattern
	 * @param dataString
	 * @return
	 */
	private static boolean matches(Pattern pattern, String dataString) {
		Matcher matcher = pattern.matcher(dataString);
		return matcher.matches();
	}

	/**
	 * 
	 * @author Estevam.Meneses May 10, 2023
	 *
	 * @param expressionStrings
	 * @return
	 */
	private static List<Pattern> buildPatterns(String[] expressionStrings) {
		List<Pattern> patterns = new ArrayList<Pattern>();
		for (String expression : expressionStrings) {
			patterns.add(getPattern(expression));
		}
		return patterns;
	}

	/**
	 * 
	 * @author Estevam.Meneses May 10, 2023
	 *
	 * @param regEx
	 * @return
	 */
	private static Pattern getPattern(String regEx) {
		return Pattern.compile(regEx, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
	}

	/**
	 * 
	 * @author Estevam.Meneses May 12, 2023
	 *
	 * @param value
	 * @param context
	 * @return
	 */
	public boolean isValid(Object value) {
		String str = String.valueOf(value);
		try {

			if (StringUtils.isEmpty(str)) {
				return true;
			}

			for (Pattern pattern : validationPatterns) {
				if (matches(pattern, str)) {
					/*
					GenericUser genericUser = portalUserUtil.getAuthenticatedUser();
					if(ObjectUtils.isEmpty(genericUser)) {
						String ip = networkUtil.getCLientIp(networkUtil.getHttpServletRequest());
						log.warn("SQL injection attack by unauthenticated user with IP address {} string: {}", ip, str);
					}else {
						String genericUserStr = new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(genericUser);
						log.warn("SQL injection attack by generic user:{}  string:{}",genericUserStr, str);
					}
					*/
					return false;
				}
			}

		} catch (Exception e) {
			log.error("Error on SQL injection annotation validating string:{} Exception:{}",str, e);
		}

		return true;
	}

	/**
	 * Method used only for test
	 * 
	 * @author Estevam.Meneses
	 *
	 * @param dataString
	 * @return
	 */
	public boolean isSqlInjection(String dataString) {
		if (StringUtils.isEmpty(dataString)) {
			return true;
		}

		for (Pattern pattern : validationPatterns) {
			if (matches(pattern, dataString)) {
				return false;
			}
		}
		return true;
	}
}