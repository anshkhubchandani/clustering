package de.unijena.bioinf.dm23.grp06;

import de.unijena.bioinf.dm23.grp06.algo.DistanceMatrix;
import de.unijena.bioinf.dm23.grp06.algo.LabelPair;
import de.unijena.bioinf.dm23.grp06.algo.UPGMA;
import de.unijena.bioinf.dm23.grp06.algo.WPGMA;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WPGMATest {
    DistanceMatrix dm = new DistanceMatrix();
    WPGMA wpgma = new WPGMA(dm);
    @Test
    public void testCalculateNewDistances() {
        DistanceMatrix distanceMatrix = new DistanceMatrix();
        distanceMatrix.addDistance("A","B", 3);
        distanceMatrix.addDistance("A","C", 4);
        distanceMatrix.addDistance("B","C", 5);

        WPGMA wpgma = new WPGMA(distanceMatrix);
        wpgma.calculateNewDistances("A","B","AB");
        assertEquals(3.0,distanceMatrix.getDistance("AB","C"));
        assertEquals(3.5,distanceMatrix.getDistance("A","AB"));
        assertEquals(3.5,distanceMatrix.getDistance("B","AB"));



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
    }
