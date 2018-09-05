package circuits;

import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

/**
 * The program simulates a combinatorial circuit that comprises a set of logic
 * gates (such as AND, OR and NOT gates) that 'compute' output variables as a
 * function of input variables. This program is tested with the JUnit5 framework
 * and some of its new features like lambda functions.
 */

public class TestLogicCircuits {

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

	// Auxiliary method used by some test methods.
	private void classIsAbstract(Class<?> c) {
		assertTrue(Modifier.isAbstract(c.getModifiers()));
	}

	/**
	 * Test LogicVariable class method's result
	 */
	@Test
	public void testLogicVariable() {
		LogicVariable x1 = new LogicVariable("x1", false);
		assertEquals("x1", x1.getName());
		assertEquals(false, x1.getValue());
		x1.setValue(true);
		assertEquals(true, x1.getValue());
		fieldsArePrivateOrProtected(LogicVariable.class);
	}

	/**
	 * Tests if LogicVariable objects are equal
	 */
	@Test
	public void testEquals() {
		LogicVariable a = new LogicVariable("y1");
		LogicVariable b = new LogicVariable("y1");
		assertTrue(a.equals(b)); // same name
		assertTrue(a.equals((Object) b));
	}

	/**
	 * Tests LogicVariable objects fields: input(s) and output; Enforces the access
	 * modifiers restrictive level to be private or protected; Enforces the
	 * LogicGate class to be an abstract class
	 * 
	 * @throws Exception
	 */
	@Test
	public void testLogicGates() throws Exception {
		LogicVariable x1 = new LogicVariable("x1", false); // input variable
		LogicVariable x2 = new LogicVariable("x2", false); // input variable
		LogicVariable x3 = new LogicVariable("x3", false); // input variable
		LogicVariable w1 = new LogicVariable("w1"); // 'internal' variable
		LogicVariable w2 = new LogicVariable("w2"); // 'internal' variable
		LogicVariable y1 = new LogicVariable("y1"); // output variable

		LogicGate p1 = new GateAnd(w1, x1, x2);
		assertSame(w1, p1.getOutput());
		assertTrue(Arrays.equals(new LogicVariable[] { x1, x2 }, p1.getInputs()));

		LogicGate p2 = new GateOr(w2, w1, x3);
		assertSame(w2, p2.getOutput());
		assertTrue(Arrays.equals(new LogicVariable[] { w1, x3 }, p2.getInputs()));

		LogicGate p3 = new GateNot(y1, w2);
		assertSame(y1, p3.getOutput());
		assertTrue(Arrays.equals(new LogicVariable[] { w2 }, p3.getInputs()));

		fieldsArePrivateOrProtected(LogicGate.class, GateAnd.class, GateOr.class, GateNot.class);
		classIsAbstract(LogicGate.class);
	}

	/**
	 * Tests LogicGate object symbols; Enforces the access modifiers restrictive
	 * level to be private or protected; Enforces the LogicGate class to be an
	 * abstract class
	 * 
	 * @throws Exception
	 */
	@Test
	public void testSymbols() throws Exception {
		LogicVariable x1 = new LogicVariable("x1", false);
		LogicVariable x2 = new LogicVariable("x2", false);
		LogicVariable x3 = new LogicVariable("x3", false);
		LogicVariable w1 = new LogicVariable("w1");
		LogicVariable w2 = new LogicVariable("w2");
		LogicVariable y1 = new LogicVariable("y1");

		LogicGate p1 = new GateAnd(w1, x1, x2);
		assertEquals("AND", p1.getSymbol());

		LogicGate p2 = new GateOr(w2, w1, x3);
		assertEquals("OR", p2.getSymbol());

		LogicGate p3 = new GateNot(y1, w2);
		assertEquals("NOT", p3.getSymbol());

		fieldsArePrivateOrProtected(LogicGate.class, GateAnd.class, GateOr.class, GateNot.class);
		classIsAbstract(LogicGate.class);
	}

	/**
	 * Tests if the LogicVariable objects are dependent or not on LogicGate objects
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCalculatedBy() throws Exception {
		LogicVariable x1 = new LogicVariable("x1", false);
		LogicVariable x2 = new LogicVariable("x2", false);
		LogicVariable y1 = new LogicVariable("y1");

		LogicGate p1 = new GateAnd(y1, x1, x2);

		assertSame(p1, y1.getCalculatedBy());
		assertSame(null, x1.getCalculatedBy());
		assertSame(null, x2.getCalculatedBy());
	}

	/**
	 * Tests if a LogicVariable object was calculated by multiple LogicGate objects
	 * 
	 * @throws Exception
	 */
	// A variable cannot be calculated by multiple gates
	@Test
	public void testColision() throws Exception {

		assertThrows(ColisionException.class, () -> {
			LogicVariable x1 = new LogicVariable("x1", false);
			LogicVariable x2 = new LogicVariable("x2", false);
			LogicVariable x3 = new LogicVariable("x3", false);
			LogicVariable x4 = new LogicVariable("x4", false);
			LogicVariable y1 = new LogicVariable("y1");

			new GateAnd(y1, x1, x2);
			new GateOr(y1, x3, x4);
		});

	}

	/**
	 * Test if string messages thrown by exceptions are equal
	 */
	@Test
	void collisionExceptionMessageTesting() {
		Throwable exception = assertThrows(ColisionException.class, () -> {
			throw new ColisionException("A variable cannot be calculated by multiple gates.");
		});
		assertEquals("A variable cannot be calculated by multiple gates.", exception.getMessage());
	}

	/**
	 * Tests strings results
	 * 
	 * @throws Exception
	 */
	@Test
	public void testFormulas() throws Exception {
		LogicVariable x1 = new LogicVariable("x1", false);
		LogicVariable x2 = new LogicVariable("x2", false);
		LogicVariable x3 = new LogicVariable("x3", false);
		LogicVariable x4 = new LogicVariable("x4", false);
		LogicVariable w1 = new LogicVariable("w1");
		LogicVariable w2 = new LogicVariable("w2");
		LogicVariable y1 = new LogicVariable("y1");
		LogicVariable y2 = new LogicVariable("y2");
		LogicVariable y3 = new LogicVariable("y3");

		new GateAnd(w1, x1, x2);
		new GateOr(w2, w1, x3);
		LogicGate p3 = new GateNot(y1, w2);

		new GateNot(y2, x4);
		new GateOr(y3, x1, x2);

		assertEquals("x1", x1.getFormula());
		assertEquals("NOT(x4)", y2.getFormula());
		assertEquals("OR(x1,x2)", y3.getFormula());
		assertEquals("NOT(OR(AND(x1,x2),x3))", y1.getFormula());
		assertEquals("NOT(OR(AND(x1,x2),x3))", p3.getFormula());
	}

	/**
	 * Tests the output values between one or more LogicGate objects connected
	 * 
	 * @throws Exception
	 */
	@Test
	public void testValues() throws Exception {
		LogicVariable x1 = new LogicVariable("x1", true);
		LogicVariable x2 = new LogicVariable("x2", false);
		LogicVariable x3 = new LogicVariable("x3", true);
		LogicVariable w1 = new LogicVariable("w1");
		LogicVariable w2 = new LogicVariable("w2");
		LogicVariable y1 = new LogicVariable("y1");
		LogicVariable y2 = new LogicVariable("y2");

		new GateAnd(w1, x1, x2);
		new GateOr(w2, w1, x3);
		new GateNot(y1, w2);

		new GateAnd(y2, x1, x3);

		assertEquals(true, x1.getValue());
		assertEquals(false, x2.getValue());
		assertEquals(true, x3.getValue());
		assertEquals(false, w1.getValue());
		assertEquals(true, w2.getValue());
		assertEquals(false, y1.getValue());
		assertEquals(true, y2.getValue());

		x3.setValue(false);
		assertEquals(true, y1.getValue());
	}

	/**
	 * Circular dependencies are not allowed between objects
	 * 
	 * @throws Exception
	 */
	@Test
	public void testCycles() throws Exception {

		assertThrows(CycleException.class, () -> {
			LogicVariable w1 = new LogicVariable("w1");
			LogicVariable w2 = new LogicVariable("w2");
			LogicVariable w3 = new LogicVariable("w3");
			// LogicVariable w4 = new LogicVariable("w4");
			// LogicVariable w5 = new LogicVariable("w5");

			// new GateOr(w1, w4, w2);
			new GateAnd(w3, w1, w2);
			new GateNot(w2, w3);

			// Own cycle
			// new GateOr(w1, w1, w5);
		});

	}

	/**
	 * Test for HashMap data structure. Avoid duplication name values (keys)
	 */
	@Test
	public void testCombinatorialCircuit() {
		CombinatorialCircuit c = new CombinatorialCircuit();
		LogicVariable a = new LogicVariable("x1");
		LogicVariable b = new LogicVariable("x1");
		assertEquals(true, c.addVariable(a));
		assertEquals(false, c.addVariable(b)); // duplicate names are not allowed
		assertSame(a, c.getVariableByName("x1"));
		assertSame(null, c.getVariableByName("x2"));
	}

}