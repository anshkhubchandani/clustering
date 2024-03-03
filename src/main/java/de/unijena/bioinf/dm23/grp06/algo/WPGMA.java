package de.unijena.bioinf.dm23.grp06.algo;

import de.unijena.bioinf.dm23.grp06.model.TreeNode;

public class WPGMA extends PairGroupMethods {

    public WPGMA (DistanceMatrix distMatrix) {
        super(distMatrix);
    }

    @Override
    protected void calculateNewDistances(String firstNode, String secondNode, String newClusterNode) {

        double newDistance;

        for (String nodeInMatrix : (this.distanceMatrix.getNodesInMatrix())) {

            newDistance = (this.distanceMatrix.getDistance(firstNode, nodeInMatrix) + this.distanceMatrix.getDistance(secondNode, nodeInMatrix)) / 2;
            this.distanceMatrix.addDistance(nodeInMatrix, newClusterNode, newDistance);

        }

    }

    @Override
    public TreeNode nextCluster() {



        return null;
    }

}
