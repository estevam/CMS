package ca.est.util;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 */
public class XssSanitizerUtil {
	
	private static final Logger log = LoggerFactory.getLogger(XssSanitizerUtil.class);

	private static List<Pattern> XSS_INPUT_PATTERNS = new ArrayList<>();
    
	static {
			// Avoid anything between script tags
			XSS_INPUT_PATTERNS.add(Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE));

			// avoid iframes
			XSS_INPUT_PATTERNS.add(Pattern.compile("<iframe(.*?)>(.*?)</iframe>", Pattern.CASE_INSENSITIVE));

			// Avoid anything in a src='...' type of expression
			XSS_INPUT_PATTERNS.add(Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));

			XSS_INPUT_PATTERNS.add(Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));

			XSS_INPUT_PATTERNS.add(Pattern.compile("src[\r\n]*=[\r\n]*([^>]+)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));

			// Remove any lonesome </script> tag
			XSS_INPUT_PATTERNS.add(Pattern.compile("</script>", Pattern.CASE_INSENSITIVE));

			// Remove any lonesome <script ...> tag
			XSS_INPUT_PATTERNS.add(Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));

			// Avoid eval(...) expressions
			XSS_INPUT_PATTERNS.add(Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));

			// Avoid expression(...) expressions
			XSS_INPUT_PATTERNS.add(Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));

			// Avoid javascript:... expressions
			XSS_INPUT_PATTERNS.add(Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE));

			// Avoid vbscript:... expressions
			XSS_INPUT_PATTERNS.add(Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE));

			// Avoid onload= expressions
			XSS_INPUT_PATTERNS.add(Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			
			XSS_INPUT_PATTERNS.add(Pattern.compile("onabort(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			
			XSS_INPUT_PATTERNS.add(Pattern.compile("onactivate(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			
			XSS_INPUT_PATTERNS.add(Pattern.compile("onafterprint(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			
			XSS_INPUT_PATTERNS.add(Pattern.compile("onafterupdate(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			
			XSS_INPUT_PATTERNS.add(Pattern.compile("onbeforeactivate(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			
			XSS_INPUT_PATTERNS.add(Pattern.compile("onbeforecopy(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			
			XSS_INPUT_PATTERNS.add(Pattern.compile("onbeforedeactivate(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			
			XSS_INPUT_PATTERNS.add(Pattern.compile("onbeforeeditfocus(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			
			XSS_INPUT_PATTERNS.add(Pattern.compile("onbeforepaste(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			
			XSS_INPUT_PATTERNS.add(Pattern.compile("onbeforeprint(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			
			XSS_INPUT_PATTERNS.add(Pattern.compile("onbeforeunload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			
			XSS_INPUT_PATTERNS.add(Pattern.compile("onbeforeupdate(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			
			XSS_INPUT_PATTERNS.add(Pattern.compile("onblur(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			
			XSS_INPUT_PATTERNS.add(Pattern.compile("onbounce(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			
			XSS_INPUT_PATTERNS.add(Pattern.compile("oncellchange(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			
			XSS_INPUT_PATTERNS.add(Pattern.compile("onchange(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			
			XSS_INPUT_PATTERNS.add(Pattern.compile("onclick(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			
			XSS_INPUT_PATTERNS.add(Pattern.compile("oncontextmenu(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			
			XSS_INPUT_PATTERNS.add(Pattern.compile("oncontrolselect(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			
			XSS_INPUT_PATTERNS.add(Pattern.compile("oncopy(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			
			XSS_INPUT_PATTERNS.add(Pattern.compile("oncut(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			
			XSS_INPUT_PATTERNS.add(Pattern.compile("ondataavailable(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			
			XSS_INPUT_PATTERNS.add(Pattern.compile("ondatasetchanged(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			
			XSS_INPUT_PATTERNS.add(Pattern.compile("ondatasetcomplete(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			
			XSS_INPUT_PATTERNS.add(Pattern.compile("ondblclick(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			
			XSS_INPUT_PATTERNS.add(Pattern.compile("ondeactivate(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			
			XSS_INPUT_PATTERNS.add(Pattern.compile("ondrag(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			
			XSS_INPUT_PATTERNS.add(Pattern.compile("ondragend(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			
			XSS_INPUT_PATTERNS.add(Pattern.compile("ondragenter(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL))
			;
			XSS_INPUT_PATTERNS.add(Pattern.compile("ondragleave(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			
			XSS_INPUT_PATTERNS.add(Pattern.compile("ondragover(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			
			XSS_INPUT_PATTERNS.add(Pattern.compile("ondragstart(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			
			XSS_INPUT_PATTERNS.add(Pattern.compile("ondrop(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			XSS_INPUT_PATTERNS.add(Pattern.compile("onerror(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			XSS_INPUT_PATTERNS.add(Pattern.compile("onerrorupdate(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			XSS_INPUT_PATTERNS.add(Pattern.compile("onfilterchange(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			XSS_INPUT_PATTERNS.add(Pattern.compile("onfinish(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			XSS_INPUT_PATTERNS.add(Pattern.compile("onfocus(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			XSS_INPUT_PATTERNS.add(Pattern.compile("onfocusin(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			XSS_INPUT_PATTERNS.add(Pattern.compile("onfocusout(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			XSS_INPUT_PATTERNS.add(Pattern.compile("onhelp(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			XSS_INPUT_PATTERNS.add(Pattern.compile("onkeydown(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			XSS_INPUT_PATTERNS.add(Pattern.compile("onkeypress(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			XSS_INPUT_PATTERNS.add(Pattern.compile("onkeyup(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			XSS_INPUT_PATTERNS.add(Pattern.compile("onlayoutcomplete(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			XSS_INPUT_PATTERNS.add(Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			XSS_INPUT_PATTERNS.add(Pattern.compile("onlosecapture(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			XSS_INPUT_PATTERNS.add(Pattern.compile("onmousedown(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			XSS_INPUT_PATTERNS.add(Pattern.compile("onmouseenter(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			
			XSS_INPUT_PATTERNS.add(Pattern.compile("onmouseleave(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			XSS_INPUT_PATTERNS.add(Pattern.compile("onmousemove(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			XSS_INPUT_PATTERNS.add(Pattern.compile("onmouseout(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			
			XSS_INPUT_PATTERNS.add(Pattern.compile("onmouseover(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			XSS_INPUT_PATTERNS.add(Pattern.compile("onmouseup(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			XSS_INPUT_PATTERNS.add(Pattern.compile("onmousewheel(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			XSS_INPUT_PATTERNS.add(Pattern.compile("onmove(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			XSS_INPUT_PATTERNS.add(Pattern.compile("onmoveend(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			XSS_INPUT_PATTERNS.add(Pattern.compile("onmovestart(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			XSS_INPUT_PATTERNS.add(Pattern.compile("onpaste(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			XSS_INPUT_PATTERNS.add(Pattern.compile("onpropertychange(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			XSS_INPUT_PATTERNS.add(Pattern.compile("onreadystatechange(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			XSS_INPUT_PATTERNS.add(Pattern.compile("onreset(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			XSS_INPUT_PATTERNS.add(Pattern.compile("onresize(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			
			XSS_INPUT_PATTERNS.add(Pattern.compile("onresizeend(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			XSS_INPUT_PATTERNS.add(Pattern.compile("onresizestart(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			
			XSS_INPUT_PATTERNS.add(Pattern.compile("onrowenter(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			XSS_INPUT_PATTERNS.add(Pattern.compile("onresizestart(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			XSS_INPUT_PATTERNS.add(Pattern.compile("onrowsdelete(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			XSS_INPUT_PATTERNS.add(Pattern.compile("onrowsinserted(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			XSS_INPUT_PATTERNS.add(Pattern.compile("onscroll(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			XSS_INPUT_PATTERNS.add(Pattern.compile("onselect(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			XSS_INPUT_PATTERNS.add(Pattern.compile("onselectionchange(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			XSS_INPUT_PATTERNS.add(Pattern.compile("onstart(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			XSS_INPUT_PATTERNS.add(Pattern.compile("onstop(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			XSS_INPUT_PATTERNS.add(Pattern.compile("onsubmit(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
			XSS_INPUT_PATTERNS.add(Pattern.compile("onunload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
	}

	/**
	 * This method takes a string and strips out any potential script injections.
	 *
	 * @param value
	 * @return true if an XSS attack was detected and false if not
	 */
	public static boolean isXSSAttack(String value) {

		try {

			if (value != null) {
				
				boolean result = false;

				// test against known XSS input patterns
				for (Pattern xssInputPattern : XSS_INPUT_PATTERNS) {
					result = xssInputPattern.matcher(value).find();
					
					if (result) {
						return true;
					}
				}
			}

		} catch (Exception ex) {
			log.error("Could not strip XSS from value = {} | ex = {} ",value,  ex.getMessage());
		}

		return false;
	}

}

 


 
