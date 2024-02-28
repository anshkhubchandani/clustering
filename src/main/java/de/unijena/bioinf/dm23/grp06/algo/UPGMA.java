package de.unijena.bioinf.dm23.grp06.algo;

import de.unijena.bioinf.dm23.grp06.model.Node;

import java.util.ArrayList;

public class UPGMA extends PairGroupMethods {

    public UPGMA (DistanceMatrix distMatrix) {
        super(distMatrix);
    }

    protected void calculateNewDistances() {

    }

    @Override
    public Node nextCluster() {
        Node nextCluster = calculateNextCluster();
        calculateNewDistances();
        return nextCluster;
    }

}
