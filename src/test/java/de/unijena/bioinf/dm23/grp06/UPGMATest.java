package de.unijena.bioinf.dm23.grp06;

import de.unijena.bioinf.dm23.grp06.algo.*;
import de.unijena.bioinf.dm23.grp06.model.TreeType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import de.unijena.bioinf.dm23.grp06.model.TreeNode;

import javax.lang.model.util.SimpleElementVisitor14;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.Sequence;
import java.awt.*;
import java.rmi.UnexpectedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

    public class UPGMATest {
        DistanceMatrix dm = new DistanceMatrix();
        UPGMA upgma = new UPGMA(dm);
        @Test
        public void testCalculateNewDistances() {
                dm.addDistance("A", "B", 17);
                dm.addDistance("A", "C", 21);
                dm.addDistance("A", "D", 31);
                dm.addDistance("A", "E", 23);
                dm.addDistance("B", "C", 30);
                dm.addDistance("B", "D", 34);
                dm.addDistance("B", "E", 21);
                dm.addDistance("C", "D", 28);
                dm.addDistance("C", "E", 39);
                dm.addDistance("D", "E", 43);
                dm.addToNodeList("A");
                dm.addToNodeList("B");
                dm.addToNodeList("C");
                dm.addToNodeList("D");
                dm.addToNodeList("E");

                System.out.println("Initial Distance Matrix:");
                System.out.println(dm);
                //upgma.calculateNewDistances("A","B","Cluster1");

                LabelPair newCluster = upgma.nextClusterCandidate();
                upgma.updateDistanceMatrix(newCluster, "A-B");

                // Calculate expected distances
                double DistanceCluster1C = dm.getDistance("A-B" , "C");
                double DistanceCluster1D = dm.getDistance("A-B" , "D");
                double DistanceCluster1E = dm.getDistance("A-B" , "E");

                double expectedDistanceC = 25.5;
                double expectedDistanceD = 32.5;
                double expectedDistanceE = 22.0;


                System.out.println("\nExpected Distance between A-B and C: " + expectedDistanceC);
                System.out.println("Actual Distance between A-B and C: " + DistanceCluster1C);
                assertEquals(expectedDistanceC, DistanceCluster1C, 0.001);

                System.out.println("Expected Distance between A-B and D: " + expectedDistanceD);
                System.out.println("Actual Distance between A-B and D: " + DistanceCluster1D);
                assertEquals(expectedDistanceD, DistanceCluster1D, 0.001);

                System.out.println("Expected Distance between A-B and E: " + expectedDistanceE);
                System.out.println("Actual Distance between A-B and E: " + DistanceCluster1E);
                assertEquals(expectedDistanceE, DistanceCluster1E, 0.001);

            }
            /*
        @Test
        void testMetricProperties () {
            DistanceMatrix validDM = createValidDistanceMatrix();
            assertTrue(validDM.checkMetric(), "Valid matrix");

            DistanceMatrix invalidDM = createInvalidDistanceMatrix();
                assertFalse(invalidDM.checkMetric(), "Invalid Matrix");
            }


            private DistanceMatrix createValidDistanceMatrix() {
                DistanceMatrix validDM = new DistanceMatrix();
                validDM.addDistance("A", "B", 1.0);
                validDM.addDistance("A", "C", 2.0);
                validDM.addDistance("B", "C", 3.0);
                return validDM;

            }
        private DistanceMatrix createInvalidDistanceMatrix() {
            DistanceMatrix invalidDM = new DistanceMatrix();
            invalidDM.addDistance("A", "B", 1.0);
            invalidDM.addDistance("B", "C", 3.0); // Violates triangle inequality???
            //invalidDM.addDistance("A", "C", 5.0);
            return invalidDM;

        }
        */

/*
         @Test
        public void testUPGMAWithEmptyInput() {
            // Testen mit leerer Eingabematrix
            //upgma.run();

            UPGMA upgma = new UPGMA(new DistanceMatrix());
            upgma.nextClusterCandidate();

            // Erwartung: Keine Änderungen sollten werden
            // assertEquals(0, upgma.nextClusterCandidate().size());
        }*/

        @Test
        void testRandomDNASequences() {
            int numSequences = 2;
            int sequenceLength = 4;

            DistanceMatrix randomMatrix = generateRandomDistanceMatrix(numSequences, sequenceLength);
            System.out.println("Random Distance Matrix:");
            System.out.println(randomMatrix);

            UPGMA upgma = new UPGMA(randomMatrix);
            // HashMap<String, Sequence> randomHashMap = new HashMap<>();

            LabelPair cluster;
            String newLabel;


            cluster = upgma.nextClusterCandidate();
            // A und B -> cluster.taxon1(): A, cluster.taxon2(): B
            // Hier kann man mit cluster.taxon1(), cluster.taxon2(), upgma.getDistanceToFirst, upgma.getDistanceToSecond abrufen
            // Hier testen!
            newLabel = cluster.getTaxon1().concat("-").concat(cluster.getTaxon2());
            upgma.updateDistanceMatrix(cluster, newLabel);


        }

        // Hilfsmethode zur Generierung einer zufälligen Distanzmatrix
        private DistanceMatrix generateRandomDistanceMatrix(int numSequences, int sequenceLength) {
            DistanceMatrix distMatrix = new DistanceMatrix();
            ArrayList<String> sequences = new ArrayList<>();

            // Generiere und speichere die zufälligen Sequenzen
            for (int i = 0; i < numSequences; i++) {
                String sequence = generateRandomSequence(sequenceLength);
                sequences.add(sequence);
                distMatrix.addToNodeList("Seq" + i);
            }

            // Berechne die Distanzen zwischen den Sequenzen und fülle die Distanzmatrix
            for (int i = 0; i < numSequences; i++) {
                String sequenceA = sequences.get(i);
                for (int j = i + 1; j < numSequences; j++) {
                    String sequenceB = sequences.get(j);
                    double distance = calculateDistance(sequenceA, sequenceB);
                    distMatrix.addDistance("Seq" + i, "Seq" + j, distance);
                }
            }
            return distMatrix;
        }

        // Hilfsmethode zur Berechnung der Distanz zwischen zwei Sequenzen
        private double calculateDistance(String sequenceA, String sequenceB) {
            // Hier kannst du deine Distanzberechnung implementieren, z.B. mit Alignment-Algorithmen
            // Für diese Testzwecke kannst du einfach die Hamming-Distanz verwenden
            int distance = 0;
            for (int i = 0; i < sequenceA.length(); i++) {
                if (sequenceA.charAt(i) != sequenceB.charAt(i)) {
                    distance++;
                }
            }
            return distance;
        }

        // Hilfsmethode zur Generierung einer zufälligen DNA-Sequenz
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



