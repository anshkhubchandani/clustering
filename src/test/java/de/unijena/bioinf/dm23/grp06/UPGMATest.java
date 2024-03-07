package de.unijena.bioinf.dm23.grp06;

import de.unijena.bioinf.dm23.grp06.algo.DistanceMatrix;
import de.unijena.bioinf.dm23.grp06.algo.UPGMA;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import de.unijena.bioinf.dm23.grp06.model.TreeNode;
import static org.junit.jupiter.api.Assertions.*;

    public class UPGMATest {
        DistanceMatrix distanceMatrix = new DistanceMatrix();
        UPGMA upgma = new UPGMA(distanceMatrix);
        @Test
        public void testUPGMAWithToyExample() {
            // Erstellen einer Beispiel-Distanzmatrix
            distanceMatrix.addDistance("A", "B", 5.0);
            distanceMatrix.addDistance("A", "C", 4.0);
            distanceMatrix.addDistance("B", "C", 3.0);

            // Erwartete Cluster und Distanzen
            String expectedCluster = "(A,B,C)";
            double expectedDistanceAB = 2.5;
            double expectedDistanceAC = 3.5;
            double expectedDistanceBC = 3.5;

            // UPGMA ausführen
            upgma.run();

            // Überprüfen der Ergebnisse
            assertEquals(expectedCluster, upgma.nextClusterCandidate());
            assertEquals(expectedDistanceAB, distanceMatrix.getDistance("A", "B"));
            assertEquals(expectedDistanceAC, distanceMatrix.getDistance("A", "C"));
            assertEquals(expectedDistanceBC, distanceMatrix.getDistance("B", "C"));
        }

        @Test
        public void testUPGMAWithEmptyInput() {
            // Testen mit leerer Eingabematrix
            upgma.run();

            // Erwartung: Keine Änderungen sollten vorgenommen werden
            assertEquals(0, upgma.nextClusterCandidate().size());
        }

        @Test
        void testRandomDNASequences() {
            int numSequences = 10;
            int sequenceLength = 20;

            TreeNode tree = upgma.run();

            assertNotNull(tree);
        }

        // Hilfsmethode zur Generierung einer zufälligen Distanzmatrix für Testzwecke
        private DistanceMatrix generateRandomDistanceMatrix(int numSequences, int sequenceLength) {

            DistanceMatrix distMatrix = new DistanceMatrix();
            for (int i = 0; i < numSequences; i++) {
                String sequenceA = generateRandomSequence(sequenceLength);
                for (int j = i + 1; j < numSequences; j++) {
                    String sequenceB = generateRandomSequence(sequenceLength);
                    double distance = upgma.calculateNewDistances(sequenceA, sequenceB,"sequenceAB");
                    distMatrix.addDistance("Seq" + i, "Seq" + j, distance);
                }
            }
            return distMatrix;
        }

        // Hilfsmethode zur Generierung einer zufälligen DNA-Sequenz für Testzwecke
        private String generateRandomSequence(int length) {
            StringBuilder sb = new StringBuilder();
            String alphabet = "ACGT";
            for (int i = 0; i < length; i++) {
                int index = (int) (Math.random() * alphabet.length());
                sb.append(alphabet.charAt(index));
            }
            return sb.toString();
        }


    }
