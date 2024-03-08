package de.unijena.bioinf.dm23.clustering;

import de.unijena.bioinf.dm23.clustering.algo.*;
import de.unijena.bioinf.dm23.grp06.alignments.algo.NeedlemanWunsch;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.AminoacidSequence;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NJtest {
    DistanceMatrix dm = new DistanceMatrix();
    /**
     * Tests the calculation of new distances after merging two nodes in the NJ algorithm.
     * @throws InvalidDistanceException if an invalid distance is encountered.
     */
    @Test
    public void testCalculateNewDistances() throws InvalidDistanceException {
        dm.addDistance("Mensch", "Maus", 3);
        dm.addDistance("Mensch", "Rose", 14);
        dm.addDistance("Mensch", "Tulpe", 12);
        dm.addDistance("Maus", "Rose", 13);
        dm.addDistance("Maus", "Tulpe", 11);
        dm.addDistance("Rose", "Tulpe", 4);
        dm.addToNodeList("Mensch");
        dm.addToNodeList("Maus");
        dm.addToNodeList("Rose");
        dm.addToNodeList("Tulpe");

        System.out.println("Initial Distance Matrix:");
        System.out.println(dm);

        NeighbourJoining nj = new NeighbourJoining(dm);

        System.out.println("\nNew Distance Matrix after merging 'Mensch' and 'Maus':");
        System.out.println(dm);

        // Calculate expected distances
        double expectedDistanceMR = 12;
        double expectedDistanceRT = 10;

        LabelPair newCluster = nj.nextClusterCandidate();
        nj.updateDistanceMatrix(newCluster, "Me-Ma");
        // Check the distances
        double distanceMeMaR = dm.getDistance("Me-Ma", "Rose"); //Distance btw Mensch and Rose
        double distanceMeMaT = dm.getDistance("Me-Ma", "Tulpe"); //Distance btw Rose and Tulpe

        System.out.println("\nExpected Distance between Cluster1 and Rose: " + expectedDistanceMR);
        System.out.println("Actual Distance between Cluster1 and Rose: " + distanceMeMaR);
        assertEquals(expectedDistanceMR, distanceMeMaR, 0.001);

        System.out.println("Expected Distance between Cluster1 and Tulpe: " + expectedDistanceRT);
        System.out.println("Actual Distance between Cluster1 and Tulpe: " + distanceMeMaT);
        assertEquals(expectedDistanceRT, distanceMeMaT, 0.001);
    }
    /**
     * Tests handling of edge cases where distances are infinity.
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
     * Tests handling of edge cases where distances are negative.
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
     * Tests handling of edge cases where distances are NaN.
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

        NeedlemanWunsch nw = new NeedlemanWunsch(new AminoacidSequence(randomSequenceA, "randomA"), new AminoacidSequence(randomSequenceB, "randomB"), new de.unijena.bioinf.dm23.grp06.algo.ClusteringScorer());
        int score = nw.linearScore();
        System.out.println("NW: " + score);

        DistanceMatrix dm2 = new DistanceMatrix();
        dm2.addDistance("SequenceA", "SequenceB",score);
        dm2.addToNodeList("SequenceA");
        dm2.addToNodeList("SequenceB");

        NeighbourJoining njNeu = new NeighbourJoining(dm2);

        System.out.println("Initial Distance Matrix:");
        System.out.println(dm2);

        LabelPair newCluster = njNeu.nextClusterCandidate();

        double distanceToA = njNeu.distanceToFirst(newCluster);
        double distanceToB = njNeu.distanceToSecond(newCluster);

        double DistanceClusterAB = distanceToA;

        double expectedDistanceClusterAB = score;


        System.out.println("\nExpected Distance between A-B: " + expectedDistanceClusterAB);
        System.out.println("Actual Distance between A-B: " + DistanceClusterAB);
        assertEquals(expectedDistanceClusterAB,DistanceClusterAB,0.001);

    }
}