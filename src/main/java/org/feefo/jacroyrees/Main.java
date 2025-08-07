package org.feefo.jacroyrees;

public class Main {
    public static void main(String[] args) {

        StringSimilarityCalculator stringSimilarityCalculator = new LevenshteinDistanceCalculator();
        Normaliser n = new JobTitleNormaliser(stringSimilarityCalculator);

        String jt = "Java engineer";
        String normalisedTitle = n.normalise(jt);
        System.out.println(normalisedTitle);

        jt = "C# engineer";
        normalisedTitle = n.normalise(jt);
        System.out.println(normalisedTitle);

        jt = "Accountant";
        normalisedTitle = n.normalise(jt);
        System.out.println(normalisedTitle);

        jt = "Chief Accountant";
        normalisedTitle = n.normalise(jt);
        System.out.println(normalisedTitle);


    }
    }
