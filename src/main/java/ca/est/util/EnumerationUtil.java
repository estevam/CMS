package ca.est.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
*
* @author Estevam.Meneses
* @since 01/01/2018
* @category Enumeration
* 
*/
public class EnumerationUtil {

	private final Logger log = LoggerFactory.getLogger(EnumerationUtil.class);

	/**
	 * Deployment Types PROD, DEV or QA
	 * 
	 * @author estevam.meneses
	 */
	public enum DeploymentTypes {

		PROD("PROD"), DEV("DEV"), QA("QA");

		private String item;

		DeploymentTypes(String str) {
			this.item = str;
		}

		public String getType() {
			return item;
		}
	}

	/**
	 * @category enum type for priority Ex: String str = priority.ONE.getValue();
	 *           String[] str = priority.values();
	 */
	public static enum priority {

		HIGH("HIGH"), MEDIUM("MEDIUM"), LOW("LOW");

		private final String name;

		private priority(String name) {
			this.name = name;
		}

		public String getValue() {
			return name;
		}
	}

	/**
	 * 
	 * @param priorityValue
	 * @return
	 */
	public boolean isValidPriority(String priorityValue) {
		try {
			priority.valueOf(priorityValue);
			return true;
		} catch (Exception e) {
			log.error("Invalid priority value {}", priorityValue, e);
			return false;
		}
	}
	
	/**
	 * enum language en or fr
	 */
	public static enum language {

		EN("EN"), FR("FR");

		private final String name;

		private language(String name) {
			this.name = name;
		}

		public String getValue() {
			return name;
		}
	}

	/**
	 * UserBlog Status - A for active, I for inactive and D for disabled
	 * 
	 * @author estevam.meneses
	 * @author alexei.gluscenov
	 */
	public enum UserStatus {

		ACTIVE("A"), PENDING_REGISTRATION("P"), INACTIVE("I"), DISABLED("D"), NEW("N");

		private String item;

		UserStatus(String str) {
			this.item = str;
		}

		public String getType() {
			return item;
		}
	}
	
	/**
	 * Password Status - A for active, R for needs to be reset and D for Locked
	 * 
	 * @author denes.martins
	 */
	public enum PasswordStatus {

		ACTIVE("A"), RESET("R"), LOCKED("L");

		private String item;

		PasswordStatus(String str) {
			this.item = str;
		}

		public String getType() {
			return item;
		}
	}

	/**
	 * Time in seconds 
	 * @author Estevam.Meneses
	 */
	public enum TimeSecond {
		THIRTY_MIN(1800), ONE_HOUR(3600), TWO_HOURS(7200), FOUR_HOURS(14400), THIRTY_DAYS(2592000), SIXTY_DAYS(5184000),
		ONE_YEARS(31556952), THREE_YEARS(94670856);

		private int time;

		TimeSecond(int num) {
			this.time = num;
		}

		public int getTime() {
			return time;
		}
	}
	/**
	 * Time millisecond
	 * @author Estevam.Meneses
	 */
	public enum TimeMillisecond {

		ONE_MIN(60000), TWO_MIN(120000), FIVE_MIN(300000), FIFTEEN_MIN(900000), THIRTY_MIN(1800000), ONE_HOUR(3600000),
		TWO_HOURS(7200000), FOUR_HOURS(14400000), EIGHT_HOURS(28800000);

		private int time;

		TimeMillisecond(int num) {
			this.time = num;
		}

		public int get() {
			return time;
		}
	}
}
