package org.example;

public interface Normaliser {

    /**
     * Normalises the given job title input.
     * @param input the job title to normalise
     * @return the normalised job title
     * @throws IllegalArgumentException if input is null or blank
     */
    String normalise(String input);

}
