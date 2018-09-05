import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class CalculatorTest {

	@Test
	public void testCalculatorShouldAddTwoValues() {
		assertEquals(6, Calculator.add(2, 4));
	}

	@Test
	public void testCalculatorShouldSubtractAndMultiply() {
		assertAll("Calculator Arithmetic", () -> assertEquals(-2, Calculator.subtract(4, 6)),
				() -> assertEquals(27, Calculator.multiply(3, 9)));
	}

	@DisplayName("Should calculate the correct sum")
	@ParameterizedTest(name = "{index} => x={0}, y={1}, expected={2}")
	@CsvFileSource(resources = "/test-data.csv")
	void additionTest(int x, int y, int expected) {
		int actual = Calculator.add(x, y);
		assertEquals(expected, actual);
	}

	@DisplayName("Should calculate the correct subtraction")
	@ParameterizedTest(name = "{index} => x={0}, y={1}, expected={2}")
	@CsvSource({ "2, 5, -3", "7, 3, 4" })
	void subtractionTest(int x, int y, int expected) {
		assertEquals(expected, Calculator.subtract(x, y));
	}

	@DisplayName("Should calculate the correct multiplication")
	@ParameterizedTest(name = "{index} => x={0}, y={1}, expected={2}")
	@MethodSource("multiplierValuesProvider")
	void multiplyTest(int x, int y, int expected) {
		assertEquals(expected, Calculator.multiply(x, y));
	}

	private static Stream<Arguments> multiplierValuesProvider() {
		return Stream.of(Arguments.of(3, -5, -15), Arguments.of(3, 9, 27));
	}

}
