package org.feefo.jacroyrees;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JobTitleNormaliserTests {

    private final Normaliser normaliser = new JobTitleNormaliser(new LevenshteinDistanceCalculator());

    @ParameterizedTest
    @CsvSource({
            "'Java Engineer', 'Software Engineer'",
            "'C# Engineer', 'Software Engineer'",
            "'Accountant', 'Accountant'",
            "'Chief Accountant', 'Accountant'"
    })
    void shouldReturnCorrectJobTitle(String input, String expectedJobTitle) {
        String actualJobTitle = normaliser.normalise(input);
        assertEquals(expectedJobTitle, actualJobTitle);
    }

    @ParameterizedTest
    @CsvSource({
            "'King of Spain', 'No matching job title has been found'"
    })
    void shouldReturnNoMatchFoundForLowScoringInput(String input, String expectedJobTitle) {
        String actualJobTitle = normaliser.normalise(input);
        assertEquals(expectedJobTitle, actualJobTitle);
    }

    @ParameterizedTest
    @CsvSource({
            "'Accountant', 'Accountant'",
            "'java EngineEr', 'Software Engineer'",
            "'CHIEF ARCHITECT', 'Architect'"
    })
    void shouldReturnCorrectJobTitle_whenInputHasMixedCase(String input, String expectedJobTitle) {
        String result = normaliser.normalise(input);
        assertEquals(expectedJobTitle, result);
    }

    @ParameterizedTest
    @CsvSource({
            "'     Accountant     ', 'Accountant'",
            "'Java Engineer', 'Software Engineer'",
            "'CHIEF ARCHITECT', 'Architect'"
    })
    void shouldReturnCorrectJobTitle_whenInputHasExtraWhiteSpace(String input, String expectedJobTitle) {
        String result = normaliser.normalise(input);
        assertEquals(expectedJobTitle, result);

    }

    @Test
    void shouldThrowExceptionWhenInputIsBlank() {
        String input = "";
        assertThrows(IllegalArgumentException.class, () -> normaliser.normalise(input));
    }

    @Test
    void shouldThrowExceptionWhenInputIsNull() {

        assertThrows(IllegalArgumentException.class, () -> normaliser.normalise(null));
    }

    @Test
    void shouldReturnCorrectCandidateWhenMultipleCandidatesHaveSameScore() {
        StringSimilarityCalculator levenshteinDistanceCalculator = mock(LevenshteinDistanceCalculator.class);
        when(levenshteinDistanceCalculator.calculateClosenessScore(anyString(), anyString())).thenReturn(0.8);
        Normaliser normaliser = new JobTitleNormaliser(levenshteinDistanceCalculator);
        String result = normaliser.normalise("Historian");
        assertEquals("Architect", result);
    }

}
