package ca.est.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.Setter;
/**
 * @author Estevam Meneses
 */
@Getter
@Setter
public class Pagination {
	
	public int page_index;
	public int page_size;
	public sort sort;
	
	public Pagination(){
		super();
	}
	
	/**
	 * field_name & direction should be add on Pageable.class
	 */
	@Getter
	@Setter
	class sort {
		/**
		 * db column
		 */
		String field_name;
		/**
		 * 'desc' or 'asc'
		 */
		String direction;
	}

	@Getter
	@Setter
	class filter {

		/**
		 * db column
		 */
		String field_name;
		/**
		 * Operator can be arithmetic, comparison or logical
		 * 
		 */
		String operator;

		String criteria_1;

		String criteria_2;

		/**
		 * Create SQL string base on field_name,operator,criteria_1, criteria_2
		 * 
		 * @return
		 */
		public String build(Object object, String field_name, String operator, String criteria_1, String criteria_2) {
			StringBuilder sb = new StringBuilder();
			if(ObjectUtils.isEmpty(object) || !doesObjectContainField(object, field_name)) {
				return "";
			}
			
			if(StringUtils.isEmpty(criteria_1)) {
				return "";
			}
			
			if(!operatorType.getInstance().contains(operator)) {
				return "";
			}
			
			if(StringUtils.isEmpty(criteria_2)) {
				sb.append(" ").append(field_name).append(" ").append(operator).append(" ").append(criteria_1);
				return sb.toString();
			}
			
			sb.append(" ").append(field_name).append(" ").append(criteria_1).append(" ").append(operator).append(" ").append(criteria_2);
			
			return sb.toString();
		}
		
		/**
		 * Check if field exist on the class
		 * 
		 * @param object
		 * @param fieldName
		 * @return
		 */
		private boolean doesObjectContainField(Object object, String fieldName) {
			return Arrays.stream(object.getClass().getFields()).anyMatch(f -> f.getName().equals(fieldName));
		}
	}

	/**
	 * Cache filter operators
	 */
	private static class operatorType {

		private static operatorType instance = null;
		private static Set<String> loadArithmetic = null;
		private static Set<String> loadComparison = null;
		private static Set<String> loadLogical = null;

		private operatorType() {

		}

		public static operatorType getInstance() {
			if (instance == null) {
				instance = new operatorType();
				loadArithmetic = new HashSet<String>(Arrays.asList("+", "-", "*", "/", "%"));
				loadComparison = new HashSet<String>(Arrays.asList("=", ">", "<", ">=", "<=", "<>", "!=", "!>", "!<"));
				loadLogical = new HashSet<String>(
						Arrays.asList("ALL", "END", "SOME", "LIKE", "IN", "BETWEEN", "NOT", "EXISTS", "OR", "NULL"));
			}

			return instance;
		}

		private boolean contains(String operator) {
			if (loadArithmetic.contains(operator)) {
				return true;
			}
			if (loadComparison.contains(operator)) {
				return true;
			}
			if (loadLogical.contains(operator.toUpperCase())) {
				return true;
			}
			return false;
		}
		
	}
}
