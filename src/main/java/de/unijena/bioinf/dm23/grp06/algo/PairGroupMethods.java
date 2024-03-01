package de.unijena.bioinf.dm23.grp06.algo;

import de.unijena.bioinf.dm23.grp06.model.Node;
import de.unijena.bioinf.dm23.grp06.model.TreeNode;

public abstract class PairGroupMethods implements Clustering {

    DistanceMatrix distanceMatrix;

    public PairGroupMethods (DistanceMatrix distMatrix) {
        this.distanceMatrix = distMatrix;
    }

    protected Node calculateNextCluster () {



        return null;
    }

    private void updateDistanceMatrix (String firstNode, String secondNode, String newClusterNode) {

        calculateNewDistances(firstNode, secondNode, newClusterNode);
        this.distanceMatrix.deleteDistances(firstNode, secondNode);
        this.distanceMatrix.findNewMinimum();

    }

    protected abstract void calculateNewDistances(String firstNode, String secondNode, String newClusterNode);

}
