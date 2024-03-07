package de.unijena.bioinf.dm23.grp06.algo;

import de.unijena.bioinf.dm23.grp06.model.TreeNode;

import java.util.ArrayList;

public class WPGMA extends PairGroupMethods {

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
