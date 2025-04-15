/**
 * @file 		TestDriver.java
 * @author 
 * @brief		Sample usage of Huffman encoding for distributions and markov processes.
 * 
 * Note:
 *  * 	The source alphabet is the 7-bit ASCII characters (i.e. ASCII characters with ord values in range 0-127). 
 *  	The ord value of the character is used as the index in arrays.
 *  *	So ALL arrays have length <code>Distribution.RANGE</code>
 */

import java.nio.file.Paths;
import java.nio.file.Files;


public class TestDriver {

	public static void main(String[] args) {

		TestDriver app = new TestDriver();
		app.run();
	}
	
	
	public void run () {
		
		// Read in data file into a string
		String source = "";
		try {
			source = new String(Files.readAllBytes(Paths.get("data/sample_1.txt")));
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		System.out.println("Length of text message (in 7-bit char) = " + source.length());
		System.out.println("Length of text message (in bits) = " + 7 * source.length());

		// test Huffman encoding/decoding while treating source as a random distribution.
		testDistribution(source);
		
		// test Huffman encoding/decoding while treating source as a Markov process.
		testMarkov(source);	
	}
	
	public void testDistribution(String source) {
		
		// Construct probabilities using observed frequencies in given string of text
		Distribution rv = Distribution.fromString(source);
		
		// Construct Huffman tree/codewords from RV
		Huffman huffman = Huffman.generate(rv);

		System.out.println("\nRV representation ...\n");

		// Calculate entropy of RV
		System.out.println("Entropy of RV (in bits) = " + rv.entropy());

		// Construct length of codewords
		System.out.println("Huffman L(C) (in bits) = " + huffman.codewordLength());

		// encode - decode a sample (or all) of the input
		String sample = source.substring(0, 100);

		System.out.println("\nLength of input sample (in 7-bit char) = " + sample.length());
		System.out.println("Length of input sample (in bits) = " + 7 * sample.length());

		// Encode message using Huffman codewords
		String encoded = huffman.encode(sample);
		System.out.println("Length of encoded message (in bits) = " + encoded.length());

		// decode message
		String decoded = huffman.decode(encoded);

		if (sample.length() < 30) {
			System.out.println(sample);
			System.out.println(encoded);
			System.out.println(decoded);
		}

		// Verify that decoded(encoded(source)) = source
		if (sample.equals(decoded)) {
			System.out.println("YES !! decoded(encoded(message)) == message");
		} else {
			System.out.println("ERROR !! decoded(encoded(message)) != message");
		}

	}
	
	
	public void testMarkov(String source) {
		System.out.println("\nMarkov chain representation ...\n");

		// Construct Markov chain representation using observed frequencies in given string of text
		// rather than solving the linear system for the steady state we just use the observed frequencies

		Markov markov = Markov.fromString(source);	// transitional probabilities

		// Calculate entropy of Markov chain
		System.out.println("Entropy of Markov chain (in bits) = " + markov.entropy());

		// Construct Huffman tree/codewords from Markov process
		HuffmanMarkov huffmanMarkov = HuffmanMarkov.generate(markov);

		// Construct length of codewords
		System.out.println("Huffman L(C) for Markov chain (in bits) = "+ huffmanMarkov.codewordLength());

		System.out
				.println("\nTest 1 - Small sample (with no unique sequence pair,  eg 'u' is only char following 'q') ...");
		String sample = source.substring(0, 30);
		System.out.println("\tLength of source message (in bits) = " + sample.length() * 7);

		// Encode message using HuffmanMarkov codewords
		String encodedMarkov = huffmanMarkov.encode(sample);
		System.out.println("\tLength of encoded message (in bits) = " + encodedMarkov.length());

		// decode message
		String decodedMarkov = huffmanMarkov.decode(encodedMarkov);

		if (sample.length() < 50) {
			System.out.println(sample);
			System.out.println(encodedMarkov);
			System.out.println(decodedMarkov);
		}

		// Verify that decoded(encoded(source)) = source
		if (sample.equals(decodedMarkov)) {
			System.out.println("\tYES !! decoded(encoded(message)) == message");
		} else {
			System.out.println("\tERROR !! decoded(encoded(message)) != message");
			for (int k = 0; k < decodedMarkov.length(); k++)
				System.out.printf("%3d %s %3d %c %3d\n", k, sample.charAt(k), (int) sample.charAt(k),
						decodedMarkov.charAt(k), (int) decodedMarkov.charAt(k));
		}

		System.out
				.println("\nTest 2 - Large sample (with unique sequence pairs, eg 'u' is only char following 'q') ...");
		sample = source;
		System.out.println("\tLength of source message (in bits) = " + sample.length() * 7);

		// Encode message using HuffmanMarkov codewords
		encodedMarkov = huffmanMarkov.encode(sample);
		System.out.println("\tLength of encoded message (in bits) = " + encodedMarkov.length());

		// decode message
		decodedMarkov = huffmanMarkov.decode(encodedMarkov);

		// Verify that decoded(encoded(source)) = source
		if (sample.equals(decodedMarkov)) {
			System.out.println("\tYES !! decoded(encoded(message)) == message");
		} else {
			System.out.println("\tERROR !! decoded(encoded(message)) != message");
			for (int k = 0; k < decodedMarkov.length(); k++)
				System.out.printf("%3d %s %3d %c %3d\n", k, sample.charAt(k), (int) sample.charAt(k),
						decodedMarkov.charAt(k), (int) decodedMarkov.charAt(k));
		}

	}

}
