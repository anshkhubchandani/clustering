package de.unijena.bioinf.dm23.clustering.algo;
/**
 * The PairGroupMethods class provides abstract methods and implements common functionality for clustering methods that operate on pairs of groups.
 */
public abstract class PairGroupMethods implements Clustering {

    DistanceMatrix distanceMatrix;
    /**
     * Constructs a new PairGroupMethods object with the specified distance matrix.
     *
     * @param distMatrix the distance matrix
     */
    public PairGroupMethods (DistanceMatrix distMatrix) {
        this.distanceMatrix = distMatrix;
    }

    @Override
    public LabelPair nextClusterCandidate() {

        LabelPair nextToCluster = this.distanceMatrix.getMinimumDistancePair();
        return nextToCluster;
    }

    @Override
    public double distanceToFirst(LabelPair lastClustered) {
        return this.distanceMatrix.getDistance(lastClustered.first, lastClustered.second);
    }

    @Override
    public double distanceToSecond(LabelPair lastClustered) {
        return distanceToFirst(lastClustered);
    }

    /**
     * Updates the distance matrix after clustering.
     *
     * @param lastClustered  the label pair that was last clustered
     * @param newClusterNode the label of the new cluster node
     */
    public void updateDistanceMatrix (LabelPair lastClustered, String newClusterNode){

         calculateNewDistances(lastClustered.first, lastClustered.second, newClusterNode);
        this.distanceMatrix.deleteDistances(lastClustered.first, lastClustered.second);

    }

    /**
     * Calculates new distances and updates the distance matrix.
     *
     * @param firstNode      the label of the first node
     * @param secondNode     the label of the second node
     * @param newClusterNode the label of the new cluster node
     */
    protected abstract void calculateNewDistances(String firstNode, String secondNode, String newClusterNode);

}
