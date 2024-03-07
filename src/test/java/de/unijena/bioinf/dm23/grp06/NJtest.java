package de.unijena.bioinf.dm23.grp06;

import de.unijena.bioinf.dm23.grp06.algo.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import javax.swing.*;
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

        NeighbourJoining upgma = new NeighbourJoining(dm);
        //upgma.calculateNewDistances("Mensch", "Maus", "Cluster1");

        System.out.println("\nNew Distance Matrix after merging 'Mensch' and 'Maus':");
        System.out.println(dm);

        // Calculate expected distances
        double expectedDistanceMR = 12;
        double expectedDistanceRT = 10;

        LabelPair newCluster = upgma.nextClusterCandidate();
        upgma.updateDistanceMatrix(newCluster, "Me-Ma");
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
}