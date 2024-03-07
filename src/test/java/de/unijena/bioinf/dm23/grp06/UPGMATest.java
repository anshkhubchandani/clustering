package de.unijena.bioinf.dm23.grp06;

import de.unijena.bioinf.dm23.grp06.algo.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

    public class UPGMATest {
        DistanceMatrix dm = new DistanceMatrix();
        UPGMA upgma = new UPGMA(dm);
        @Test
        public void testCalculateNewDistancesWithEdgeCases() {
            // Adding distances for edge cases
            dm.addDistance("A", "B", 0); // Distance between same nodes
            dm.addDistance("A", "C", Double.POSITIVE_INFINITY); // Distance is infinity
            dm.addDistance("B", "C", Double.POSITIVE_INFINITY); // Distance is NaN
            dm.addDistance("D", "E", -1); // Negative distance
            dm.addToNodeList("A");
            dm.addToNodeList("B");
            dm.addToNodeList("C");
            dm.addToNodeList("D");
            dm.addToNodeList("E");

            System.out.println("Initial Distance Matrix:");
            System.out.println(dm);

            // Calculate new distances and update matrix
            LabelPair newCluster = upgma.nextClusterCandidate();
            upgma.updateDistanceMatrix(newCluster, "A-B");

            // Calculate expected distances
            double DistanceCluster1C = dm.getDistance("A-B", "C");
            double DistanceCluster1D = dm.getDistance("A-B", "D");
            double DistanceCluster1E = dm.getDistance("A-B", "E");

            // In edge cases, distances may vary, so we won't set strict expected values
            System.out.println("\nExpected Distance between A-B and C: Infinity");
            System.out.println("Actual Distance between A-B and C: " + DistanceCluster1C);
            assert(Double.isInfinite(DistanceCluster1C)); // Asserting that distance is infinity

            System.out.println("Expected Distance between A-B and D: NaN");
            System.out.println("Actual Distance between A-B and D: " + DistanceCluster1D);
            assertTrue(Double.isNaN(DistanceCluster1D)); // Asserting that distance is NaN

            System.out.println("Expected Distance between A-B and E: Negative");
            System.out.println("Actual Distance between A-B and E: " + DistanceCluster1E);
            assertTrue(DistanceCluster1E < 0); // Asserting that distance is negative
        }

        @Test
        public void testCalculateRandomDistances() {
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
        @Test
        public void testRandomDNASequences() {
            int numSequences = (int) (Math.random() * 10) + 1; // Generate a random number of sequences between 1 and 10
            for (int i = 0; i < numSequences; i++) {
                String sequence = generateRandomDNASequence();
                assertNotNull(sequence);
                assertTrue(sequence.matches("[ACGT]+")); // Ensure the sequence contains only valid DNA characters
                System.out.println("Random DNA Sequence " + (i + 1) + ": " + sequence);
            }
        }

        // Method to generate a random DNA sequence of length between 5 and 20
        private String generateRandomDNASequence() {
            StringBuilder sequence = new StringBuilder();
            int length = (int) (Math.random() * 16) + 5; // Random length between 5 and 20
            for (int i = 0; i < length; i++) {
                char nucleotide = "ACGT".charAt((int) (Math.random() * 4)); // Randomly select A, C, G, or T
                sequence.append(nucleotide);
            }
            return sequence.toString();
        }

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
    }



