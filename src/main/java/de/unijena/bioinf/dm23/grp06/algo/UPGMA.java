package de.unijena.bioinf.dm23.grp06.algo;

import de.unijena.bioinf.dm23.grp06.model.TreeNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UPGMA extends PairGroupMethods {

    public UPGMA (DistanceMatrix distMatrix) {
        super(distMatrix);
    }

    @Override
    protected void calculateNewDistances(String firstNode, String secondNode, String newClusterNode) {

        double newDistance;
        ArrayList<String> nodesInDistanceMatrix = this.distanceMatrix.getNodesInMatrix();
        int firstNodeCluster = numberOfCluster(firstNode);
        int secondNodeCluster = numberOfCluster(secondNode);

        for (String nodeInMatrix : nodesInDistanceMatrix) {

            newDistance = ((firstNodeCluster * this.distanceMatrix.getDistance(firstNode, nodeInMatrix)) +
                    (secondNodeCluster * this.distanceMatrix.getDistance(secondNode, nodeInMatrix))) /
                    (firstNodeCluster + secondNodeCluster);
            this.distanceMatrix.addDistance(nodeInMatrix, newClusterNode, newDistance);

        }
        this.distanceMatrix.addToNodeList(newClusterNode);
    }


    private int numberOfCluster (String node) {
       /* return node.split("-").length;*/
        //Ansh probiert was desperate aus

            // Split the node name by "-" character
            String[] parts = node.split("-");
            // If the node name contains "-", return the length of the resulting array
            // Otherwise, return 1
            return parts.length > 1 ? parts.length : 1;
        }
}
