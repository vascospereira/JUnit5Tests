package numerics;

/**
 * Interface to be implemented by objects that represent real functions
 * of real values. 
 * 
 */
public interface Function {

	/**
	 * Calculates the value of the function for a given argument. 
	 * Must be implemented by any class that implement this interface.
	 * 
	 * @param x function argument
	 * @return the function value
	 */
	double evaluate(double x);

}