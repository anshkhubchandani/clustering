package de.unijena.bioinf.dm23.grp06.algo;

import de.unijena.bioinf.dm23.grp06.model.TreeNode;

import java.util.ArrayList;
import java.util.HashMap;

public class NeighbourJoining implements Clustering {

    private DistanceMatrix distanceMatrix;
    private HashMap<String, Double> rValues;
    private double minM;
    private NodePair minMNodePair;

    public NeighbourJoining (DistanceMatrix distMatrix) {
        this.distanceMatrix = distMatrix;
    }

    @Override
    public TreeNode nextCluster() { // oder TaxonPair als return-Wert?

        double distanceToFirst = (this.distanceMatrix.getDistance(minMNodePair.first, minMNodePair.second) + rValues.get(minMNodePair.first) - rValues.get(minMNodePair.second));
        double distanceToSecond = this.distanceMatrix.getDistance(minMNodePair.first, minMNodePair.second) - distanceToFirst;

        // TODO !!!

        return null;
    }

    private void calculateRValues () {

        double sumOfDistances;
        ArrayList<String> nodesInDistanceMatrix = this.distanceMatrix.getNodesInMatrix();

        for (String fromNode : nodesInDistanceMatrix) {
            sumOfDistances = 0;
            for (String toNode : nodesInDistanceMatrix) {

                if (!fromNode.equals(toNode)) {
                    sumOfDistances += this.distanceMatrix.getDistance(fromNode, toNode);
                }

            }
            this.rValues.put(fromNode, ( (1 / nodesInDistanceMatrix.size()-2) * sumOfDistances ));
        }

    }

    private void calculateMinM () {

        ArrayList<String> nodesInDistanceMatrix = this.distanceMatrix.getNodesInMatrix();

        for (String fromNode : nodesInDistanceMatrix) {

            for (String toNode : nodesInDistanceMatrix) {

                if (!fromNode.equals(toNode) &&
                        ((this.distanceMatrix.getDistance(fromNode, toNode) - (rValues.get(fromNode) + rValues.get(toNode))) < minM)) {

                    minM = this.distanceMatrix.getDistance(fromNode, toNode) - (rValues.get(fromNode) + rValues.get(toNode));
                    minMNodePair = new NodePair(fromNode, toNode);
                }
            }
        }
    }

    private void updateDistanceMatrix (String newClusterNode) {
        double nodeDistance;

        for (String nodeInMatrix : this.distanceMatrix.getNodesInMatrix()) {

            nodeDistance = (this.distanceMatrix.getDistance(minMNodePair.first, nodeInMatrix) +
                    this.distanceMatrix.getDistance(minMNodePair.second, nodeInMatrix) -
                    this.distanceMatrix.getDistance(minMNodePair.first, minMNodePair.second)) / 2;
            this.distanceMatrix.addDistance(nodeInMatrix, newClusterNode, nodeDistance);
        }

        this.distanceMatrix.deleteDistances(minMNodePair.first, minMNodePair.second);
    }

}
