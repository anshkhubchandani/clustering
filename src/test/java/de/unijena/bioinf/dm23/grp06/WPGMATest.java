package de.unijena.bioinf.dm23.grp06;

import de.unijena.bioinf.dm23.grp06.algo.DistanceMatrix;
import de.unijena.bioinf.dm23.grp06.algo.WPGMA;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WPGMATest {
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


    }
}
