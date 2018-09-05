package statistics;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.junit.jupiter.api.Test;

import numerics.Function;

public class TestNormalDistribution {

	private static final double prob2sigma = 0.477250;
	private static final double precision = 5E-7;
	private static final double DELTA = 1E-15;

	/**
	 * Test if each class fields are protected or private and if we should apply
	 * some abstract classes or interfaces in the program design following OOP
	 * principles.
	 * 
	 * @param classes
	 */
	// Auxiliary method used by some test methods.
	private void fieldsArePrivateOrProtected(Class<?>... classes) {
		for (Class<?> c : classes)
			for (Field f : c.getDeclaredFields())
				assertTrue(Modifier.isPrivate(f.getModifiers()) || Modifier.isProtected(f.getModifiers()));
	}

	// For Interface test
	private void isInterface(Class<?> i) {
		assertTrue(Modifier.isInterface(i.getModifiers()));
	}

	@Test
	public void testConstructor() {
		NormalDistribution n = new NormalDistribution(1.0, 3.0);
		assertEquals(1.0, n.getMean(), DELTA);
		assertEquals(3.0, n.getStddev(), DELTA);
		fieldsArePrivateOrProtected(NormalDistribution.class);
	}

	@Test
	public void testDefaultConstructor() {
		NormalDistribution n = new NormalDistribution();
		assertEquals(0.0, n.getMean(), DELTA);
		assertEquals(1.0, n.getStddev(), DELTA);
	}

	@Test
	public void testInvallidStddev() {
		assertThrows(IllegalArgumentException.class, () -> new NormalDistribution(0, 0));
	}

	@Test
	public void testProbabilityDistribution() {
		ProbabilityDistribution d = new NormalDistribution(0, 1);
		assertEquals(0.0, d.getMean(), DELTA);
		assertEquals(1.0, d.getStddev(), DELTA);
		isInterface(ProbabilityDistribution.class);
	}

	@Test
	public void testProbabilityDensityFunction() {
		// See formula in http://en.wikipedia.org/wiki/Normal_distribution
		ProbabilityDistribution d = new NormalDistribution(0.0, 1.0);
		assertEquals(0.3989423, d.probabilityDensityFunction(0.0), precision);
		assertEquals(0.2419707, d.probabilityDensityFunction(1.0), precision);
		assertEquals(0.2419707, d.probabilityDensityFunction(-1.0), precision);
		isInterface(Function.class);
	}

	@Test
	public void testCalcRangeProbability() {
		// calcRangeProbability(a,b) : probability of random variable
		// being in the interval [a, b]
		ProbabilityDistribution n = new NormalDistribution(0, 1);
		assertEquals(prob2sigma, n.calcRangeProbability(0, 2), precision);
		assertEquals(prob2sigma, n.calcRangeProbability(-2, 0), precision);
		assertEquals(2 * prob2sigma, n.calcRangeProbability(-2, 2), precision);
	}

	@Test
	public void testCalcLeftProbability() {
		// calcLeftProbability(x) : probability of random variable <= x
		ProbabilityDistribution n = new NormalDistribution(1, 1);
		assertEquals(0.5, n.calcLeftProbability(1.0), precision);
		assertEquals(0.5 + prob2sigma, n.calcLeftProbability(3.0), precision);
		assertEquals(0.5 - prob2sigma, n.calcLeftProbability(-1.0), precision);
	}

	@Test
	public void testEquals() {
		NormalDistribution d1 = new NormalDistribution(0, 1);
		NormalDistribution d2 = new NormalDistribution(0, 1);
		NormalDistribution d3 = d1;
		// Checks the object reference using the == operator.
		assertNotSame(d1, d2);
		assertSame(d1, d3);
		// d1.equals(d2)
		assertEquals(d1, d2);
		assertEquals(d1, d3);
	}

	@Test
	public void testAsString() {
		NormalDistribution d1 = new NormalDistribution(2.0, 5.0);
		assertEquals("N(2.0, 5.0)", d1 + "");
	}

	@Test
	public void testFindDistribution() {
		NormalDistribution X = new NormalDistribution("X", 2.0, 5.0);
		new NormalDistribution("Y", 2.0, 5.0);
		assertSame(X, ProbabilityDistribution.find("X"));
		assertNull(ProbabilityDistribution.find("Z"));
	}

}
