package statistics;

import java.util.HashMap;

public interface ProbabilityDistribution {

	static HashMap<String, ProbabilityDistribution> pd = new HashMap<>();
	
	double getMean();

	double getStddev();

	double probabilityDensityFunction(double x);

	double calcRangeProbability(int a, int b);

	double calcLeftProbability(double b);
	
	static void add(String name, ProbabilityDistribution proDis) {
		pd.put(name, proDis);
	}

	static ProbabilityDistribution find(String name) {
		return pd.get(name);
	}

}
