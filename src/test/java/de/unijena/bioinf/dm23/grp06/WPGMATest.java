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
    }
