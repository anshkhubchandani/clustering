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

}
