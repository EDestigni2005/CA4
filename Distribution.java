/**
 * Class to represent a (random variable) probability distribution.
 *
 * The alphabet of symbols is not stored, since assuming the alphabet is the 7-bit ASCII characters (i.e. ASCII
 * characters in range 0-127)
 */
public class Distribution {

	final static int	RANGE	= 128;	// Only interested in 7-bit ASCII characters

	double[]			p;				// probability distribution

	public Distribution() {
		p = new double[RANGE];
	}

	/**
	 * Factory method to build a probability distribution from observed frequencies in a string.
	 *
	 * @param source
	 *            String contain text (including line brake, etc)
	 *
	 * @return Probability distribution.
	 */
	public static Distribution fromString(String source) {

		Distribution result = new Distribution();

		// first pass, over string -> get character frequencies
		int[] frequencies = new int[RANGE];
		int total = 0;

		for (int i = 0; i < source.length(); i++) {
			char c = source.charAt(i);
			if (c < RANGE) {
				frequencies[c]++;
				total++;
			}
		}

		// second pass, over frequencies -> scale to get probabilities
		for (int i = 0; i < RANGE; i++) {
			if (frequencies[i] > 0) {
				result.p[i] = (double) frequencies[i] / total;
			}
		}

		return result;
	}

	/**
	 * Calculate the entropy of a probability distribution.
	 *
	 * @return entropy (in bits)
	 */
	public double entropy() {

		double result = 0.0;

		for (int i = 0; i < RANGE; i++) {
			if (p[i] > 0) {
				result -= p[i] * (Math.log(p[i]) / Math.log(2));
			}
		}

		return result;
	}
}