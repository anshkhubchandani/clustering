package de.unijena.bioinf.dm23.clustering;

import java.util.Random;

public class RandomDNASequencesGenerator {

    // Methode zur Generierung einer zufälligen DNA-Sequenz mit einer bestimmten Länge
    public static String generateRandomDNASequence(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int randomNumber = random.nextInt(4); // Zufällige Auswahl von A, C, G oder T
            switch (randomNumber) {
                case 0:
                    sb.append("A");
                    break;
                case 1:
                    sb.append("C");
                    break;
                case 2:
                    sb.append("G");
                    break;
                case 3:
                    sb.append("T");
                    break;
            }
        }
        return sb.toString();
    }
}
