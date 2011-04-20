/*
 * Created on Sep 20, 2004
 *
 * Administrator
 */
package aero.nettracer.fs.utilities.tracing;

import java.util.ArrayList;

/**
 * @author Administrator
 * 
 * create date - Sep 20, 2004
 */
public class StringCompare {

	/** @return lexical similarity value in the range [0,1] */
	public static double compareStrings(String str1, String str2) {
		if (str1 == null || str2 == null || str1.trim().length() == 0 || str2.trim().length() == 0) return 0;
		// so if there is a compare already, always return greater than 0
		double result = docompare(str1, str2);
		if (result <= 0) return 0;
		else return result;
	}

	public static double compareStrings(int str1, int str2) {
		if (str1 == 0 || str2 == 0) return 0;
		return docompare(Integer.toString(str1), Integer.toString(str2)) + 0;
	}

	private static double docompare(String str1, String str2) {
		ArrayList pairs1 = wordLetterPairs(str1.toUpperCase().trim());
		ArrayList pairs2 = wordLetterPairs(str2.toUpperCase().trim());
		int intersection = 0;
		int union = pairs1.size() + pairs2.size();
		for (int i = 0; i < pairs1.size(); i++) {
			Object pair1 = pairs1.get(i);
			for (int j = 0; j < pairs2.size(); j++) {
				Object pair2 = pairs2.get(j);
				if (pair1.equals(pair2)) {
					intersection++;
					pairs2.remove(j);
					break;
				}
			}
		}
		return ((2.0 * intersection) / union) * 100;
	}

	/** @return an ArrayList of 2-character Strings. */
	private static ArrayList wordLetterPairs(String str) {
		ArrayList allPairs = new ArrayList();
		if (str.equals("")) return allPairs;
		// Tokenize the string and put the tokens/words into an array
		String[] words = str.split("s");
		// For each word
		for (int w = 0; w < words.length; w++) {
			// Find the pairs of characters
			String[] pairsInWord = letterPairs(words[w]);
			for (int p = 0; p < pairsInWord.length; p++) {
				allPairs.add(pairsInWord[p]);
			}
		}
		return allPairs;
	}

	private static String[] letterPairs(String str) {
		int numPairs = str.length() - 1;
		String[] pairs = new String[numPairs];
		for (int i = 0; i < numPairs; i++) {
			pairs[i] = str.substring(i, i + 2);
		}
		return pairs;
	}

}