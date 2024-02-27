package de.unijena.bioinf.dm23.grp06.algo;

import de.unijena.bioinf.dm23.grp06.model.Node;

public abstract class PairGroupMethods implements Clustering {

    DistanceMatrix distanceMatrix;

    public PairGroupMethods (DistanceMatrix distMatrix) {
        this.distanceMatrix = distMatrix;
    }

    @Override
    public Node nextCluster() {
        return null;
    }
}
