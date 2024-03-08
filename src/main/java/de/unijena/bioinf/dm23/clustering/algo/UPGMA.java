package de.unijena.bioinf.dm23.clustering.algo;
import java.util.ArrayList;

/**
 * The UPGMA class implements the UPGMA (Unweighted Pair Group Method with Arithmetic Mean) algorithm for clustering.
 * It extends the PairGroupMethods abstract class.
 */
public class UPGMA extends PairGroupMethods {

    /**
     * Constructs a new UPGMA object with the specified distance matrix.
     *
     * @param distMatrix the distance matrix
     */
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
            try {
            newDistance = ((firstNodeCluster * this.distanceMatrix.getDistance(firstNode, nodeInMatrix)) +
                    (secondNodeCluster * this.distanceMatrix.getDistance(secondNode, nodeInMatrix))) /
                    (firstNodeCluster + secondNodeCluster);

                this.distanceMatrix.addDistance(nodeInMatrix, newClusterNode, newDistance);
            } catch (InvalidDistanceException e) {
                System.err.println("Error calculating new distances: " + e.getMessage());
            }
        }
        this.distanceMatrix.addToNodeList(newClusterNode);
    }

    /**
     * Calculates the number of clusters a node belongs to.
     *
     * @param node the node label
     * @return the number of clusters the node belongs to
     */
    private int numberOfCluster (String node) {
       return node.split("-").length;

        }
}
