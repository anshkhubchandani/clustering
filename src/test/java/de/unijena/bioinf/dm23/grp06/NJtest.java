/*package de.unijena.bioinf.dm23.grp06;

import de.unijena.bioinf.dm23.grp06.algo.DistanceMatrix;
import de.unijena.bioinf.dm23.grp06.algo.LabelPair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import de.unijena.bioinf.dm23.grp06.algo.NeighbourJoining;

public class NJtest {
        private DistanceMatrix distanceMatrix;
        private NeighbourJoining neighbourJoining;

        @BeforeEach
        public void setUp() {
            // Create a new distance matrix and NeighbourJoining instance before each test
            distanceMatrix = new DistanceMatrix();
            distanceMatrix.addDistance("A", "B", 3);
            distanceMatrix.addDistance("A", "C", 4);
            distanceMatrix.addDistance("B", "C", 5);
            neighbourJoining = new NeighbourJoining(distanceMatrix);
        }

        @Test
        public void testCalculateRValues() {
            // Call the calculateRValues method
            neighbourJoining.calculateRValues();

            // Check if rValues are calculated correctly
            assertEquals(3.5, neighbourJoining.getValues().get("A"));
            assertEquals(3.5, neighbourJoining.getRValues().get("B"));
            assertEquals(3.5, neighbourJoining.getRValues().get("C"));
        }

        @Test
        public void testCalculateMinM() {
            neighbourJoining.calculateRValues();

            LabelPair minMPair = neighbourJoining.calculateMinM();

            // Check if the minimum M value and corresponding pair are correct
            assertEquals(0.5, neighbourJoining.distanceToFirst(minMPair));
            assertEquals(1.5, neighbourJoining.distanceToSecond(minMPair));
        }

        @Test
        public void testUpdateDistanceMatrix() {
            // Calculate rValues first
            neighbourJoining.calculateRValues();

            // Call the calculateMinM method to get the next cluster candidate
            LabelPair minMPair = neighbourJoining.calculateMinM();

            // Call the updateDistanceMatrix method with the new cluster node
            neighbourJoining.updateDistanceMatrix(minMPair, "AB");

            // Check if distances are updated correctly in the distance matrix
            assertEquals(3.0, distanceMatrix.getDistance("AB", "C"));
            assertEquals(3.5, distanceMatrix.getDistance("A", "AB"));
            assertEquals(3.5, distanceMatrix.getDistance("B", "AB"));
            assertNull(distanceMatrix.getDistance("A", "B")); // Check if old distances are removed
        }

        // Add more test cases to cover edge cases, such as empty distance matrix or special input values
    }

*/