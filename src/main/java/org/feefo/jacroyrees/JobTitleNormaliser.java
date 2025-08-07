package org.feefo.jacroyrees;

import java.util.ArrayList;
import java.util.List;

/**
 * JobTitleNormaliser normalises job titles by comparing them against a predefined list of candidate titles.
 * It uses a StringSimilarityCalculator to compute the closeness score between the userInputString and candidate titles.
 * If a perfect match is found, it returns that candidate. If no suitable match is found, it returns a default message.
 */
public class JobTitleNormaliser implements Normaliser {

    private static final double MINIMUM_MATCHING_SCORE = 0.3;
    private static final double MAXIMUM_MATCHING_SCORE = 1.0;
    /**
     * A list of candidate job titles against which the userInputString will be compared.
     * This list can be extended with more job titles as needed - in larger applications would be loaded from a database or configuration file.
     */
    private static final String[] CANDIDATE_JOB_TITLES = new String[]{
            "Architect",
            "Software Engineer",
            "Quantity Surveyor",
            "Accountant"

    };
    private final StringSimilarityCalculator stringSimilarityCalculator;

    /**
     * Constructor for JobTitleNormaliser.
     *
     * @param stringSimilarityCalculator the calculator used to compute closeness scores between strings
     */
    public JobTitleNormaliser(StringSimilarityCalculator stringSimilarityCalculator) {
        this.stringSimilarityCalculator = stringSimilarityCalculator;
    }


    /**
     * Normalises the userInputString job title by comparing it against a list of candidate titles.
     * It returns the best matching candidate or a default message if no suitable match is found.
     *
     * @param userInputString the job title to normalise
     * @return the normalised job title or a default message if no match is found
     */
    @Override
    public String normalise(String userInputString) {

        if (userInputString == null || userInputString.isEmpty()) {
            throw new IllegalArgumentException("userInputString cannot be null or blank");
        }

        double highestScore = Double.MIN_VALUE;
        String formatteduserInputString = formatString(userInputString);
        List<String> bestCandidates = new ArrayList<>();

        for (String candidateJobTitle : CANDIDATE_JOB_TITLES) {
            String formattedCandidateJobTitle = formatString(candidateJobTitle);
            double candidateScore = stringSimilarityCalculator.calculateClosenessScore(formatteduserInputString, formattedCandidateJobTitle);

            if (candidateScore == MAXIMUM_MATCHING_SCORE) {
                return candidateJobTitle;
            }

            if (candidateScore > highestScore) {
                highestScore = candidateScore;
                bestCandidates.clear();
                bestCandidates.add(candidateJobTitle);
            } else if (candidateScore == highestScore) {
                bestCandidates.add(candidateJobTitle);
            }
        }

        if (highestScore < MINIMUM_MATCHING_SCORE || bestCandidates.isEmpty()) {
            return "No matching job title has been found";
        }

        return bestCandidate(bestCandidates, formatteduserInputString);
    }

    /**
     * Formats the userInputString string by converting it to lowercase and removing whitespace.
     *
     * @param userInputString the string to format
     * @return the formatted string
     */
    private String formatString(String userInputString) {
        return userInputString.toLowerCase().replaceAll("\\s", "");
    }


    /**
     * Finds the best candidate from a list of candidates based on the length difference
     * between the formatted userInputString and each candidate.
     *
     * @param bestCandidateJobTitles the list of candidate job titles
     * @param userInputString        the original userInputString job title
     * @return the best matching candidate job title
     */
    private String bestCandidate(List<String> bestCandidateJobTitles, String userInputString) {
        int userInputStringLength = formatString(userInputString).length();

        if (bestCandidateJobTitles.size() == 1) {
            return bestCandidateJobTitles.getFirst();
        }

        String bestMatch = bestCandidateJobTitles.getFirst();
        int bestDiff = Math.abs(formatString(bestMatch).length() - userInputStringLength);

        for (int i = 1; i < bestCandidateJobTitles.size(); i++) {
            String candidate = bestCandidateJobTitles.get(i);
            int candidateLength = formatString(candidate).length();
            int diff = Math.abs(candidateLength - userInputStringLength);

            if (diff < bestDiff) {
                bestMatch = candidate;
                bestDiff = diff;
            }
        }

        return bestMatch;
    }

}



