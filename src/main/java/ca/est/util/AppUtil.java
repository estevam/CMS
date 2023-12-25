/**
 * 
 */
package ca.est.util;

/**
 * 
 * @author Estevam Meneses
 */
public class AppUtil {

	public static String API="/api";
	public enum TokenType {

		ACCESS_TOKEN("access_token"), REFRESH_TOKEN("refresh_token");

		private String item;

		TokenType(String str) {
			this.item = str;
		}

		public String getType() {
			return item;
		}
	}
}