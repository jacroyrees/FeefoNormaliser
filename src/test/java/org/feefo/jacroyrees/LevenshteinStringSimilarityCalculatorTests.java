package org.feefo.jacroyrees;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LevenshteinStringSimilarityCalculatorTests {

    private static final LevenshteinDistanceCalculator calculator = new LevenshteinDistanceCalculator();


    @ParameterizedTest
    @CsvSource({
            "'Java Engineer', 'Software Engineer', 0.5882352941176471",
            "'C# Engineer', 'Software Engineer', 0.5294117647058824",
            "'Accountant', 'Accountant', 1.0",
            "'Chief Accountant', 'Accountant', 0.625",
            "'book', 'back', 0.5"
    })
    void shouldReturnCorrectClosenessScore(String input, String candidate, double expectedScore) {
        double actualScore = calculator.calculateClosenessScore(input, candidate);
        assertEquals(expectedScore, actualScore, 0.0001);
    }
    @ParameterizedTest
    @CsvSource({
            "'Java Engineer', 'Software Engineer', 7",
            "'C# Engineer', 'Software Engineer', 8",
            "'Accountant', 'Accountant', 0",
            "'Chief Accountant', 'Accountant', 6"
    })
    void shouldReturnCorrectLevenshteinDistanceScore(String input, String candidate, double expectedScore) {
        double actualScore = calculator.calculateDistance(input, candidate);
        assertEquals(expectedScore, actualScore);
    }

    @ParameterizedTest
    @CsvSource({
            "'Java Engineer', 'Software Engineer', 7",
            "'C# Engineer', 'Software Engineer', 8",
            "'Accountant', 'Accountant', 0",
            "'Chief Accountant', 'Accountant', 6"
    })
    void shouldReturnCorrectLevenshteinDistanceScoreWhenCaseDoesntMatch(String input, String candidate, double expectedScore) {
        double actualScore = calculator.calculateDistance(input, candidate);
        assertEquals(expectedScore, actualScore);
    }






}