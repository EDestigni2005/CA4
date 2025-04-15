/**
 * Class to represent a Markov process.
 * 
 * Only the steady state distribution <code>steady</code> and the transition probabilities <code>state</code> are stored, not
 * the symbols since alphabet is 7-bit ASCII characters
 */
public class Markov {
	
	Distribution	steady;				// steady state probability distribution
	Distribution[]	state;				// state transitional probabilities

	public Markov() {
		state = new Distribution[Distribution.RANGE];
		for (int k = 0; k < Distribution.RANGE; k++)
			state[k] = new Distribution();
	}
	
	/**
	 * Factory method to build a Markov process from observed frequencies in a string.
	 * 
	 * Rather than solving the linear system for the steady state probabilities we just use the observed frequencies.
	 * (This allow us to avoid implementing a matrix solver)
	 * 
	 * @param source
	 *            String contain text (including line brake, etc)
	 * 
	 * @return  Markov process
	 */
	public static Markov fromString(String source) {
		
		Markov result = new Markov();
		
		// construct steady state distribution
		// TODO
	
		// first pass, over source string -> calculate frequencies
		// TODO
		
		// second pass, over frequencies -> convert frequencies to probabilities (row sum = 1)
		// TODO
		
		return result;
	}

	/**
	 * Calculate the entropy of a Markov process.
	 * 
	 * @return entropy of Markov process (in bits)
	 */
	public double entropy() {

		double sum = 0.0;

		// TODO

		return sum;
	}

}








