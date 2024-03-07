package de.unijena.bioinf.dm23.clustering.algo;

import java.util.ArrayList;
import java.util.HashMap;
/**
 * The NeighbourJoining class implements the Clustering interface and provides methods for performing clustering using the Neighbor Joining algorithm.
 */
public class NeighbourJoining implements Clustering {

    private DistanceMatrix distanceMatrix;
    private HashMap<String, Double> rValues;
    /**
     * Constructs a new NeighbourJoining object with the specified distance matrix.
     *
     * @param distMatrix the distance matrix
     */
    public NeighbourJoining (DistanceMatrix distMatrix) {
        this.distanceMatrix = distMatrix;
        rValues = new HashMap<>();
        calculateRValues();
    }

    @Override
    public LabelPair nextClusterCandidate() { // oder TaxonPair als return-Wert?

        return calculateMinM();
    }

    @Override
    public double distanceToFirst(LabelPair lastClustered) {
        return (this.distanceMatrix.getDistance(lastClustered.first, lastClustered.second) +
                rValues.get(lastClustered.first) - rValues.get(lastClustered.second))/2;
    }

    @Override
    public double distanceToSecond(LabelPair lastClustered) {
        double distanceToFirst = distanceToFirst(lastClustered);
        return this.distanceMatrix.getDistance(lastClustered.first, lastClustered.second)
                - distanceToFirst;
    }
    /**
     * Calculates the r-values for each node in the distance matrix.
     */
    public void calculateRValues () {

        double sumOfDistances;
        ArrayList<String> nodesInDistanceMatrix = this.distanceMatrix.getNodesInMatrix();

        for (String fromNode : nodesInDistanceMatrix) {
            sumOfDistances = 0;
            for (String toNode : nodesInDistanceMatrix) {

                if (!fromNode.equals(toNode)) {
                    sumOfDistances += this.distanceMatrix.getDistance(fromNode, toNode);
                }

            }
            double temp1 = nodesInDistanceMatrix.size() - 2;
            double fraction = 1 / temp1;
            this.rValues.put(fromNode, (fraction * sumOfDistances));
        }

    }

    /**
     * Calculates the minimum M value for clustering.
     *
     * @return the label pair with the minimum M value
     */
    public LabelPair calculateMinM () {

        ArrayList<String> nodesInDistanceMatrix = this.distanceMatrix.getNodesInMatrix();
        LabelPair minMPair = new LabelPair(nodesInDistanceMatrix.get(0), nodesInDistanceMatrix.get(0));
        double minM = Double.MAX_VALUE;

        for (String fromNode : nodesInDistanceMatrix) {

            for (String toNode : nodesInDistanceMatrix) {

                if (!fromNode.equals(toNode) &&
                        ((this.distanceMatrix.getDistance(fromNode, toNode) - (rValues.get(fromNode) + rValues.get(toNode))) < minM)) {

                    minM = this.distanceMatrix.getDistance(fromNode, toNode) - (rValues.get(fromNode) + rValues.get(toNode));
                    minMPair = new LabelPair(fromNode, toNode);
                }
            }
        }
        return minMPair;
    }

    @Override
    public void updateDistanceMatrix (LabelPair lastClustered, String newClusterNode) {
        double nodeDistance;
        ArrayList<String> nodesInDistanceMatrix = this.distanceMatrix.getNodesInMatrix();

        for (String nodeInMatrix : nodesInDistanceMatrix) {
    try{
            nodeDistance = (this.distanceMatrix.getDistance(lastClustered.first, nodeInMatrix) +
                    this.distanceMatrix.getDistance(lastClustered.second, nodeInMatrix) -
                    this.distanceMatrix.getDistance(lastClustered.first, lastClustered.second)) / 2;
            this.distanceMatrix.addDistance(nodeInMatrix, newClusterNode, nodeDistance);
        }catch (InvalidDistanceException e) {
        System.err.println("Error updating distance matrix" + e.getMessage());
    }
    }

        this.distanceMatrix.deleteDistances(lastClustered.first, lastClustered.second);
        this.distanceMatrix.addToNodeList(newClusterNode);
        calculateRValues();
    }

}
