package de.unijena.bioinf.dm23.grp06.algo;

import de.unijena.bioinf.dm23.grp06.model.TreeNode;

public abstract class PairGroupMethods implements Clustering {

    DistanceMatrix distanceMatrix;

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

    public void updateDistanceMatrix (LabelPair lastClustered, String newClusterNode) {

        calculateNewDistances(lastClustered.first, lastClustered.second, newClusterNode);
        this.distanceMatrix.deleteDistances(lastClustered.first, lastClustered.second);

    }

    protected abstract void calculateNewDistances(String firstNode, String secondNode, String newClusterNode);

}
