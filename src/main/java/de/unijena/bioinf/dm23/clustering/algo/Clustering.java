package de.unijena.bioinf.dm23.clustering.algo;

/**
 * The Clustering interface defines methods for performing clustering operations.
 */
public interface Clustering {

    /**
     * Retrieves the next cluster candidate.
     *
     * @return the next cluster candidate as a LabelPair object
     */
    LabelPair nextClusterCandidate();

    /**
     * Calculates the distance to the first cluster in the last clustered pair.
     *
     * @param lastClustered the last clustered pair
     * @return the distance to the first cluster
     */
    double distanceToFirst(LabelPair lastClustered);

    /**
     * Calculates the distance to the second cluster in the last clustered pair.
     *
     * @param lastClustered the last clustered pair
     * @return the distance to the second cluster
     */
    double distanceToSecond(LabelPair lastClustered);

    /**
     * Updates the distance matrix after clustering.
     *
     * @param oldNode       the old node that was clustered
     * @param newClusterNode the new cluster node created from the clustering
     */
    void updateDistanceMatrix(LabelPair oldNode, String newClusterNode);
    DistanceMatrix getDistanceMatrix();

}
