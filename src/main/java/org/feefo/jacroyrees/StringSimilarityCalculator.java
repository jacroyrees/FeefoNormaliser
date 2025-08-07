package org.feefo.jacroyrees;

public interface StringSimilarityCalculator {
    /**
     * Calculates the normalised closeness score between two strings.
     * The score is a value between 0 and 1, where 1 means the strings are identical.
     *
     * @param input    The first string to compare.
     * @param candidate The second string to compare.
     * @return The normalised closeness score (0-1) between the two strings.
     */
    double calculateClosenessScore(String input, String candidate);

    /**
     * Calculates the distance between two strings.
     * The actual implementation of this method should compute the Levenshtein distance or any other distance metric
     * which measures how many edits are required in order to  change one string into the other.
     *
     * @param input    The first string to compare.
     * @param candidate The second string to compare.
     * @return The Levenshtein distance between the two strings.
     */
    int calculateDistance(String input, String candidate);
}
