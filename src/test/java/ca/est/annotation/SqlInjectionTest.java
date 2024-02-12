package ca.est.annotation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.est.annotation.impl.SqlInjectionImpl;


/**
 * 
 * @author Estevam.Meneses
 * 
 */
@ExtendWith(MockitoExtension.class)
public class SqlInjectionTest {

	@InjectMocks
	SqlInjectionImpl sqlInjectionImpl = new SqlInjectionImpl();

	private static final String SPACES_REGEX = "[\\s|\\r|\\n|\\t]";
	private static final String EMPTY = "";

	@Test
	public void testWithBadData() {
		String[] maliciousDataSamples = { "select adf from abc", "insert into abcd", "update abcd", "delete from abcd",
				"upsert abcd", "call abcd", "rollback ", "create table abc", "drop table", "drop view",
				"alter table abc", "truncate table abc", "desc abc", "select id", "select 'abc'", };

		for (String maliciousPart : maliciousDataSamples) {
			testUnSafeWithAllVariations(maliciousPart);
		}

		String[] sqlDisruptiveDataSamples = { "--", "/*", "*/", ";", "someone -- abcd", "abcd /* adf */ adf", };

		for (String desruptivePart : sqlDisruptiveDataSamples) {
			testForPurelyUnSafeDataWithAllVariations(desruptivePart);
		}

	}

	@Test
	public void testWithGoodData() {
		String[] safeDataSamples = { "12", "abcd123", "123abcd", "abcd", " OR adfadfa adf column1 = COLUMN1",
				" and adfadfa adf column1 = COLUMN1", };
		for (String safeData : safeDataSamples) {
			assertTrue(sqlInjectionImpl.isSqlInjection(safeData),
					"Failed to qualify this as SQL-injection safe data : ".concat(safeData));
		}
	}

	@Test
	public void testForEqualsInjection() {
		String[] maliciousSamples = { " OR false ", " OR true ", " and true ", " OR equals true ",
				" OR not equals true ", " OR equals false ", " and equals false ", " OR 1=1", " and 1=1",
				" OR column1=COLUMN1", " OR column1 = COLUMN1", " OR column1!=COLUMN1", " OR column1<>COLUMN1",
				" OR colu_mn1=COL_UMN1", " OR 'A'='A'", " OR '1afA'='2fadfA'", " OR 1=1 OR 2=2", " OR 1=1 and 2=2",
				" and 1=1 and 2=2", " and 1=1 or 2=2", };
		for (String maliciousData : maliciousSamples) {
			testForPurelyUnSafeDataWithAllVariationsExcludeEmptySpacesCheck(maliciousData);
		}

	}

	private void testUnSafeWithAllVariations(String maliciousPart) {
		String prefix = "some-Data-prefix";
		String suffix = "some-Data-suffix";
		String space = " ";

		String maliciousData = "";
		String safeData = "";

		maliciousData = prefix.concat(space).concat(maliciousPart).concat(space).concat(suffix);

		assertFalse(sqlInjectionImpl.isSqlInjection(maliciousData),
				"Failed to detect SQL-unsafe data: ".concat(maliciousData));

		assertFalse(sqlInjectionImpl.isSqlInjection(maliciousData.toUpperCase()),
				"Failed to detect SQL-unsafe data: ".concat(maliciousData.toUpperCase()));

		safeData = prefix.concat(maliciousPart).concat(suffix);

		assertTrue(sqlInjectionImpl.isSqlInjection(safeData),
				"Failed to qualify this as SQL-injection safe data: ".concat(safeData));

		safeData = removeAllSpaces(maliciousData);
		assertTrue(sqlInjectionImpl.isSqlInjection(safeData),
				"Failed to qualify this as SQL-injection safe data: ".concat(safeData));

		prefix = "";
		suffix = "";
		maliciousData = prefix.concat(maliciousPart).concat(suffix);

		assertFalse(sqlInjectionImpl.isSqlInjection(maliciousData),
				"Failed to detect SQL-unsafe data: ".concat(maliciousData));

		safeData = removeAllSpaces(maliciousData);
		assertTrue(sqlInjectionImpl.isSqlInjection(safeData),
				"Failed to qualify this as SQL-injection safe data: ".concat(safeData));

	}

	private void testForPurelyUnSafeDataWithAllVariations(String maliciousPart, boolean emptySpaceCheckRequired) {
		String prefix = "some-Data-prefix";
		String suffix = "some-Data-suffix";
		String space = " ";
		String maliciousData = "";

		maliciousData = prefix + space + maliciousPart + space + suffix;

		assertFalse(sqlInjectionImpl.isSqlInjection(maliciousData),
				"Failed to detect SQL-unsafe data: ".concat(maliciousData));

		assertFalse(sqlInjectionImpl.isSqlInjection(maliciousData.toUpperCase()),
				"Failed to detect SQL-unsafe data: ".concat(maliciousData.toUpperCase()));

		if (emptySpaceCheckRequired) {
			assertFalse(sqlInjectionImpl.isSqlInjection(removeAllSpaces(maliciousData)),
					"Failed to detect SQL-unsafe data: ".concat(removeAllSpaces(maliciousData)));
		}

		prefix = "";
		suffix = "";
		maliciousData = prefix.concat(maliciousPart).concat(suffix);

		assertFalse(sqlInjectionImpl.isSqlInjection(maliciousData),
				"Failed to detect SQL-unsafe data: ".concat(maliciousData));

		if (emptySpaceCheckRequired) {
			assertFalse(sqlInjectionImpl.isSqlInjection(removeAllSpaces(maliciousData)),
					"Failed to detect SQL-unsafe data: ".concat(removeAllSpaces(maliciousData)));
		}
	}

	private void testForPurelyUnSafeDataWithAllVariations(String maliciousPart) {
		testForPurelyUnSafeDataWithAllVariations(maliciousPart, true);
	}

	private void testForPurelyUnSafeDataWithAllVariationsExcludeEmptySpacesCheck(String maliciousPart) {
		testForPurelyUnSafeDataWithAllVariations(maliciousPart, false);

	}

	private String removeAllSpaces(String str) {
		Pattern pattern = Pattern.compile(SPACES_REGEX);
		Matcher matcher = pattern.matcher(str);
		return matcher.replaceAll(EMPTY);
	}
}