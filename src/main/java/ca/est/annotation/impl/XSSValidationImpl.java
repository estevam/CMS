package ca.est.annotation.impl;

import org.apache.commons.lang3.StringUtils;
import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import ca.est.annotation.XSS;
import ca.est.util.XssSanitizerUtil;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * @apiNote Annotation to implement Cross Site Scripting(XSS) validation
 * @author Estevam Meneses - Use OWASP Java HTML to string sanitization
 * @see <a href="ttps://owasp.org/www-project-java-html-sanitizer">OWASP
 *      documentation</a>
 * @since Feb 07, 2023
 */
@Component
public class XSSValidationImpl implements ConstraintValidator<XSS, Object> {


	private static final Logger log = LoggerFactory.getLogger(XSSValidationImpl.class);

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {

		String str = String.valueOf(value);
		try {

			if (StringUtils.isEmpty(str)) {
				return true;
			}
			PolicyFactory policy = new HtmlPolicyBuilder()
					.toFactory();
			String sanitize = asciiToString(policy.sanitize(str));
			if (sanitize.equals(str)) {
				return true;
			}
			
			//GenericUser genericUser = portalUserUtil.getAuthenticatedUser();
			if (!XssSanitizerUtil.isXSSAttack(str)) {
				return true;
			}

			/**
			if (ObjectUtils.isEmpty(genericUser)) {
				String ip = networkUtil.getCLientIp(networkUtil.getHttpServletRequest());
				log.warn("XSS attack by unauthenticated user with IP address {} string: {}", ip, str);
			} else {
				log.warn("XSS injection attack by generic user id:{}. Rejected Sring: {}", genericUser.getId(), str);
			}
            */
		} catch (Exception e) {
			log.error("Error on string sanitization. String:{} - Error:", str, e);
		}

		return false;
	}

	/**
	 * Replace ASCII character to respective value.
	 *
	 * @param str
	 * @return
	 */
	private String asciiToString(String str) {
		String[] search = new String[] { "&#39;", "&#64;", "&amp;", "&#43;", "&#96;", "&#61;", "&#39;", "&gt;", "&lt;",
				"&#34;" };
		String[] replacement = new String[] { "'", "@", "&", "+", "`", "=", "'", ">", "<", "\"" };
		return StringUtils.replaceEach(str, search, replacement);

	}
}