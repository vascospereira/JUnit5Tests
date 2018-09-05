package statistics;

import numerics.*;

public class NormalDistribution implements ProbabilityDistribution, Function {

	private static final double PRECISION = 1e-10;
	private double mean;
	private double stdDev;
	private String name;

	public NormalDistribution(double mean, double stdDev) {
		if (mean == 0 && stdDev == 0)
			throw new IllegalArgumentException();
		this.mean = mean;
		this.stdDev = stdDev;
	}

	public NormalDistribution() {
		this(0, 1);
	}

	public NormalDistribution(String name, double mean, double stdDev) {
		this(mean, stdDev);
		this.name = name;
		ProbabilityDistribution.add(name, this);
	}

	public double getMean() {
		return mean;
	}

	public double getStddev() {
		return stdDev;
	}

	public String getName() {
		return name;
	}

	public double probabilityDensityFunction(double x) {
		return 1 / (stdDev * Math.sqrt(2 * Math.PI)) * Math.exp(-(Math.pow((x - mean), 2) / 2 * Math.pow(stdDev, 2)));
	}

	public double evaluate(double x) {
		return probabilityDensityFunction(x);
	}

	public double calcRangeProbability(int a, int b) {
		return SimpsonMethod.calcIntegral(this, a, b, PRECISION);
	}

	public double calcLeftProbability(double b) {
		if (b == mean)
			return 0.5;
		else if (b > mean) 
			return 0.5 + SimpsonMethod.calcIntegral(this, mean, b, PRECISION);
		return 0.5 - SimpsonMethod.calcIntegral(this, b, mean, PRECISION);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(mean);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(stdDev);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NormalDistribution other = (NormalDistribution) obj;
		if (Double.doubleToLongBits(mean) != Double.doubleToLongBits(other.mean))
			return false;
		if (Double.doubleToLongBits(stdDev) != Double.doubleToLongBits(other.stdDev))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "N(" + mean + ", " + stdDev + ")";
	}

}
