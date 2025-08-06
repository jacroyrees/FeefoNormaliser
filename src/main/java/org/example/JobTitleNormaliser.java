package org.example;

import java.util.ArrayList;
import java.util.List;

/**
 * JobTitleNormaliser normalises job titles by comparing them against a predefined list of candidate titles.
 * It uses a StringSimilarityCalculator to compute the closeness score between the input and candidate titles.
 * If a perfect match is found, it returns that candidate. If no suitable match is found, it returns a default message.
 */
public class JobTitleNormaliser implements Normaliser {

    private final StringSimilarityCalculator stringSimilarityCalculator;
    private static final double MINIMUM_MATCHING_SCORE = 0.3;
    private static final double MAXIMUM_MATCHING_SCORE = 1.0;
    private static final String[] CANDIDATES = new String[]{
            "Architect",
            "Software Engineer",
            "Quantity Surveyor",
            "Accountant"

    };
    /**
     * Constructor for JobTitleNormaliser.
     *
     * @param stringSimilarityCalculator the calculator used to compute closeness scores between strings
     */
    public JobTitleNormaliser(StringSimilarityCalculator stringSimilarityCalculator) {
        this.stringSimilarityCalculator = stringSimilarityCalculator;
    }


    /**
     * Normalises the input job title by comparing it against a list of candidate titles.
     * It returns the best matching candidate or a default message if no suitable match is found.
     *
     * @param input the job title to normalise
     * @return the normalised job title or a default message if no match is found
     */
    @Override
    public String normalise(String input) {

        if(input == null || input.isEmpty()) {
            throw new IllegalArgumentException("Input cannot be null or blank");
        }

        double highestScore = Double.MIN_VALUE;
        String formattedInput = formatString(input);
        List<String> bestCandidates = new ArrayList<>();

        for (String candidate : CANDIDATES) {
            String formattedCandidate = formatString(candidate);
            double candidateScore = stringSimilarityCalculator.calculateClosenessScore(formattedInput, formattedCandidate);

            if (candidateScore == MAXIMUM_MATCHING_SCORE) {
                return candidate;
            }

            if (candidateScore > highestScore) {
                highestScore = candidateScore;
                bestCandidates.clear();
                bestCandidates.add(candidate);
            } else if (candidateScore == highestScore) {
                bestCandidates.add(candidate);
            }
        }

        if (highestScore < MINIMUM_MATCHING_SCORE || bestCandidates.isEmpty()) {
            return "No matching job title has been found";
        }

        return bestCandidate(bestCandidates, formattedInput);
    }

    /**
     * Formats the input string by converting it to lowercase and removing whitespace.
     *
     * @param input the string to format
     * @return the formatted string
     */
    private String formatString(String input) {
        return input.toLowerCase().replaceAll("\\s","");
    }



    /**
     * Finds the best candidate from a list of candidates based on the length difference
     * between the formatted input and each candidate.
     *
     * @param candidates the list of candidate job titles
     * @param input the original input job title
     * @return the best matching candidate job title
     */
    private String bestCandidate(List<String> candidates, String input) {
        int inputLength = formatString(input).length();

        if (candidates.size() == 1) {
            return candidates.get(0);
        }

        String bestMatch = candidates.get(0);
        int bestDiff = Math.abs(formatString(bestMatch).length() - inputLength);

        for (int i = 1; i < candidates.size(); i++) {
            String candidate = candidates.get(i);
            int candidateLength = formatString(candidate).length();
            int diff = Math.abs(candidateLength - inputLength);

            if (diff < bestDiff) {
                bestMatch = candidate;
                bestDiff = diff;
            }
        }

        return bestMatch;
    }

    }



