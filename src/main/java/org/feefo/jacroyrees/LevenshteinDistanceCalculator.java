package org.feefo.jacroyrees;

public class LevenshteinDistanceCalculator implements StringSimilarityCalculator {

    /**
     * Calculates the normalised closeness score which is provided to us by the calculateDistance function.
     *
     * @param userInputJobTitle The first string to compare.
     * @param candidateJobTitle The second string to compare.
     * @return The normalised (0-1) Levenshtein distance between the two strings.
     */
    @Override
    public double calculateClosenessScore(String userInputJobTitle, String candidateJobTitle) {
        double levenshteinDistance = calculateDistance(userInputJobTitle, candidateJobTitle);
        return 1.0 - levenshteinDistance / longestStringLength(userInputJobTitle, candidateJobTitle);
    }


    /**
     * Calculates the Levenshtein distance between two strings.
     * The Levenshtein distance is a measure of how many single-character edits (insertions, deletions, or substitutions)
     * are required to change one string into the other.
     *
     * @param userInputJobTitle The first string to compare.
     * @param candidateJobTitle The second string to compare.
     * @return The Levenshtein distance between the two strings.
     */
    @Override
    public int calculateDistance(String userInputJobTitle, String candidateJobTitle) {


        if (userInputJobTitle.equals(candidateJobTitle)) {
            return 0;
        }

        int[][] matrix = new int[userInputJobTitle.length() + 1][candidateJobTitle.length() + 1];

        for (int i = 0; i < matrix.length; i++) {
            matrix[i][0] = i;
        }
        for (int j = 0; j < matrix[0].length; j++) {
            matrix[0][j] = j;
        }

        for (int row = 1; row < matrix.length; row++) {
            for (int col = 1; col < matrix[0].length; col++) {
                if (userInputJobTitle.charAt(row - 1) == candidateJobTitle.charAt(col - 1)) {
                    matrix[row][col] = matrix[row - 1][col - 1];
                } else {
                    int minimum = Math.min(matrix[row - 1][col - 1], Math.min(matrix[row - 1][col], matrix[row][col - 1]));
                    matrix[row][col] = minimum + 1;
                }
            }
        }

        return matrix[userInputJobTitle.length()][candidateJobTitle.length()];
    }

    /**
     * Helper function which returns the length of the longest string between the userInputJobTitle and candidateJobTitle.
     * This is used to normalise the Levenshtein distance score.
     *
     * @param userInputJobTitle The first string to compare.
     * @param candidateJobTitle The second string to compare.
     * @return The length of the longest string.
     */
    private int longestStringLength(String userInputJobTitle, String candidateJobTitle) {
        return Math.max(userInputJobTitle.length(), candidateJobTitle.length());
    }


}