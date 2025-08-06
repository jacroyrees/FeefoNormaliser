package org.example;

public class LevenshteinDistanceCalculator implements StringSimilarityCalculator {

    /**
     * Calculates the normalised closeness score which is provided to us by the calculateDistance function.
     * @param input    The first string to compare.
     * @param candidate The second string to compare.
     * @return The normalised (0-1) Levenshtein distance between the two strings.
     */
    @Override
    public double calculateClosenessScore(String input, String candidate) {
        double levenshteinDistance = calculateDistance(input, candidate);
        return 1.0 -  levenshteinDistance / longestStringLength(input, candidate);
    }


    /**
     * Calculates the Levenshtein distance between two strings.
     * The Levenshtein distance is a measure of how many single-character edits (insertions, deletions, or substitutions)
     * are required to change one string into the other.
     *
     * @param input    The first string to compare.
     * @param candidate The second string to compare.
     * @return The Levenshtein distance between the two strings.
     */
    @Override
    public int calculateDistance(String input, String candidate) {



        if (input.equals(candidate)) {
            return 0;
        }

        int[][] matrix = new int[input.length() + 1][candidate.length() + 1];

        for (int i = 0; i < matrix.length; i++) {
            matrix[i][0] = i;
        }
        for (int j = 0; j < matrix[0].length; j++) {
            matrix[0][j] = j;
        }

        for (int row = 1; row < matrix.length; row++) {
            for (int col = 1; col < matrix[0].length; col++) {
                if (input.charAt(row - 1) == candidate.charAt(col - 1)) {
                    matrix[row][col] = matrix[row - 1][col - 1];
                } else {
                    int minimum = Math.min(matrix[row - 1][col - 1], Math.min(matrix[row - 1][col], matrix[row][col - 1]));
                    matrix[row][col] = minimum + 1;
                }
            }
        }

        return matrix[input.length()][candidate.length()];
    }

    /**
     * Helper function which returns the length of the longest string between the input and candidate.
     * This is used to normalise the Levenshtein distance score.
     *
     * @param input    The first string to compare.
     * @param candidate The second string to compare.
     * @return The length of the longest string.
     */
    private int longestStringLength(String input, String candidate) {
        return Math.max(input.length(), candidate.length());
    }



}