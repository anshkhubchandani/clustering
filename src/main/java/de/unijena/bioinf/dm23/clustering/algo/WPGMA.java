package de.unijena.bioinf.dm23.clustering.algo;

import java.util.ArrayList;

/**
 * The WPGMA class implements the WPGMA (Weighted Pair Group Method with Arithmetic Mean) algorithm for clustering.
 * It extends the PairGroupMethods abstract class.
 */
public class WPGMA extends PairGroupMethods {

    /**
     * Constructs a new WPGMA object with the specified distance matrix.
     *
     * @param distMatrix the distance matrix
     */
    public WPGMA (DistanceMatrix distMatrix) {
        super(distMatrix);
    }

    @Override
    protected void calculateNewDistances(String firstNode, String secondNode, String newClusterNode) {

        double newDistance;
        ArrayList<String> nodesInDistanceMatrix = this.distanceMatrix.getNodesInMatrix();

        for (String nodeInMatrix : nodesInDistanceMatrix) {

            try {
                newDistance = (this.distanceMatrix.getDistance(firstNode, nodeInMatrix) + this.distanceMatrix.getDistance(secondNode, nodeInMatrix)) / 2;

                this.distanceMatrix.addDistance(nodeInMatrix, newClusterNode, newDistance);
            } catch (InvalidDistanceException e) {
               System.err.println("Error calculating new distances" + e.getMessage());
            }
        }
        this.distanceMatrix.addToNodeList(newClusterNode);
    }
}
