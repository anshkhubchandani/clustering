package de.unijena.bioinf.dm23.clustering;

import de.unijena.bioinf.dm23.clustering.algo.DistanceMatrix;
import de.unijena.bioinf.dm23.clustering.algo.InvalidDistanceException;
import de.unijena.bioinf.dm23.clustering.algo.LabelPair;
import de.unijena.bioinf.dm23.clustering.algo.WPGMA;
import de.unijena.bioinf.dm23.grp06.algo.ClusteringScorer;
import de.unijena.bioinf.dm23.grp06.alignments.algo.NeedlemanWunsch;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.AminoacidSequence;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for the WPGMA algorithm and related functionality.
 */

public class WPGMATest {
    DistanceMatrix dm = new DistanceMatrix();
    WPGMA wpgma = new WPGMA(dm);

    /**
     * Test method for calculating new distances in the WPGMA algorithm.
     *
     * @throws InvalidDistanceException if there is an error in distance calculation.
     */
    @Test
    public void testCalculateNewDistances() throws InvalidDistanceException {

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

        LabelPair newCluster = wpgma.nextClusterCandidate();
        wpgma.updateDistanceMatrix(newCluster, "A-B");

        double DistanceCluster1C = dm.getDistance("A-B", "C");
        double DistanceCluster1D = dm.getDistance("A-B", "D");
        double DistanceCluster1E = dm.getDistance("A-B", "E");

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

    /**
     * Test method for edge cases where distances are infinity.
     */
    @Test
    public void testEdgeCases_infinity() {
        InvalidDistanceException e = Assertions.assertThrows(InvalidDistanceException.class, () -> {
            dm.addDistance("A", "C", Double.POSITIVE_INFINITY); // Distance is infinity
            dm.addDistance("B", "C", Double.NEGATIVE_INFINITY); // Distance is NaN

            dm.addToNodeList("A");
            dm.addToNodeList("B");
            dm.addToNodeList("C");

        });
    }

    /**
     * Test method for edge cases where distances are negative.
     */
    @Test
    public void testEdgeCases_negativ() {
        InvalidDistanceException e = Assertions.assertThrows(InvalidDistanceException.class, () -> {
            dm.addDistance("A", "C", -1); // Distance is infinity
            dm.addDistance("B", "C", -2); // Distance is NaN

            dm.addToNodeList("A");
            dm.addToNodeList("B");
            dm.addToNodeList("C");
        });
    }

    /**
     * Test method for edge cases where distances are NaN.
     */
    @Test
    public void testEdgeCases_NaN() {
        InvalidDistanceException e = Assertions.assertThrows(InvalidDistanceException.class, () -> {
            dm.addDistance("A", "C", Double.NaN); // Distance is NaN
            dm.addDistance("B", "C", -2); // Distance is NaN

            dm.addToNodeList("A");
            dm.addToNodeList("B");
            dm.addToNodeList("C");
        });
    }

    @Test
    public void testRandomDNASequences() throws InvalidDistanceException {
        int sequenceLength = 5; // Beispiel: Länge der zufällig generierten DNA-Sequenz
        String randomSequenceA = RandomDNASequencesGenerator.generateRandomDNASequence(sequenceLength);
        String randomSequenceB = RandomDNASequencesGenerator.generateRandomDNASequence(sequenceLength);

        NeedlemanWunsch nw = new NeedlemanWunsch(new AminoacidSequence(randomSequenceA, "randomA"), new AminoacidSequence(randomSequenceB, "randomB"), new ClusteringScorer());
        int score = nw.linearScore();
        System.out.println("NW: " + score);

        DistanceMatrix dm2 = new DistanceMatrix();
        dm2.addDistance("SequenceA", "SequenceB",score);
        dm2.addToNodeList("SequenceA");
        dm2.addToNodeList("SequenceB");

        WPGMA wpgmaNeu = new WPGMA(dm2);

        System.out.println("Initial Distance Matrix:");
        System.out.println(dm2);
        // wpgma.calculateNewDistances("SequenceA","SequenceB","ClusterAB");

        LabelPair newCluster = wpgmaNeu.nextClusterCandidate();

        double distanceToA = wpgmaNeu.distanceToFirst(newCluster);
        double distanceToB = wpgmaNeu.distanceToSecond(newCluster);

        double DistanceClusterAB = distanceToB;

        double expectedDistanceClusterAB = score;


        System.out.println("\nExpected Distance between A-B: " + expectedDistanceClusterAB);
        System.out.println("Actual Distance between A-B: " + DistanceClusterAB);
        assertEquals(expectedDistanceClusterAB,DistanceClusterAB,0.001);




    }

}
