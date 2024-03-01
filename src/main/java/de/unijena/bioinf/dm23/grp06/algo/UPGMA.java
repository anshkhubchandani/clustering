package de.unijena.bioinf.dm23.grp06.algo;

import de.unijena.bioinf.dm23.grp06.model.Node;

import java.util.ArrayList;

public class UPGMA extends PairGroupMethods {

    public UPGMA (DistanceMatrix distMatrix) {
        super(distMatrix);
    }

    @Override
    protected void calculateNewDistances(String firstNode, String secondNode, String newClusterNode) {

        double newDistance;

        for (String nodeInMatrix : (this.distanceMatrix.getNodesInMatrix())) {

            newDistance = ((firstNode.length() * this.distanceMatrix.getDistance(firstNode, nodeInMatrix)) +
                    (secondNode.length() * this.distanceMatrix.getDistance(secondNode, nodeInMatrix))) /
                    (firstNode.length() + secondNode.length());
            this.distanceMatrix.addDistance(nodeInMatrix, newClusterNode, newDistance);

        }

    }

    @Override
    public Node nextCluster() {

        return null;
    }

}
