package de.unijena.bioinf.dm23.grp06.algo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class ClusteringScorer extends de.unijena.bioinf.dm23.grp06.alignments.algo.Scorer {

    private final boolean defaultScores;
    private final int match = 0;
    private final int mismatch = 1;
    private final int gap = 1;
    private HashMap<String,Integer> customScores;
    private String path;

    /**
     * Creates a Scorer object with default scores.
     */
    public ClusteringScorer() {
        defaultScores = true;
    }

    /**
     * Creates a Scorer object with scores read from the scorer input file
     * @param path File path of the file, the scores should be read from
     */
    public ClusteringScorer(String path) {
        getScoresFromFile(path);
        this.path = path;
        defaultScores = false;
    }

    /**
     * Returns the score for a pair of characters according to the scoring scheme
     * @param firstChar The first character
     * @param secondChar The second character
     * @return Integer value of the given pair of characters
     */
    public int score(char firstChar, char secondChar) {
        if (defaultScores) {
            return defaultScorer(firstChar, secondChar);
        }
        else {
            return scorerByInputScheme(firstChar, secondChar);
        }
    }

    private int defaultScorer (char firstChar, char secondChar) {
        if (firstChar == secondChar) {
            return match;
        }
        else if (firstChar == '*' || secondChar == '*' || firstChar == '-' || secondChar == '-') {
            return gap;
        }
        else {
            return mismatch;
        }
    }

    private int scorerByInputScheme (char firstChar, char secondChar) {

        if (firstChar == '-') {
            firstChar = '*';
        }
        if (secondChar == '-') {
            secondChar = '*';
        }

        String charPair = Character.toString(firstChar).concat(Character.toString(secondChar));
        String charPairRev = new StringBuilder(charPair).reverse().toString();

        if (customScores.containsKey(charPair)) {
            return customScores.get(charPair);
        }
        else if (customScores.containsKey(charPairRev)) {
            return customScores.get(charPairRev);
        }
        else {
            return defaultScorer(firstChar, secondChar);
        }
    }

    private void getScoresFromFile (String fileNameOrPath) {

        customScores = new HashMap<>();

        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(fileNameOrPath));
            String nextLine = reader.readLine();

            while (nextLine.startsWith("#")) { // oder (nextLine = reader.readLine()) != null ?
                nextLine = reader.readLine();
            }

            String[] firstCharacters = nextLine.trim().split("\\s+");

            String secondCharacter;
            String[] characterPairScores;
            int k = 0;

            while ((nextLine = reader.readLine()) != null) {

                secondCharacter = nextLine.substring(0, 1);

                characterPairScores = nextLine.substring(2).trim().split(" +");

                for (int i = k; i < firstCharacters.length; i++) {
                    customScores.put(firstCharacters[i].concat(secondCharacter), Integer.parseInt(characterPairScores[i]));
                }

                k++;
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                reader.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Creates and returns a string of the scores used by this Scorer object.
     * @return String of default scores if default scores are used, filepath of scorer file if user input
     * scores are used.
     */
    public String getScoresAsString () {
        if (defaultScores) {
            StringBuilder sb = new StringBuilder();
            sb.append("Default scores: \nMatch: ").append(match).append("; Mismatch: ").append(mismatch).append("; Gap: ").append(gap);
            return sb.toString();
        }
        else {
            return "Scores:\nUser input scores from file " + path;
        }
    }
}
