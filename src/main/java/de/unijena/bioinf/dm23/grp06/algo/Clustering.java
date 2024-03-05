package de.unijena.bioinf.dm23.grp06.algo;

public interface Clustering {

    LabelPair nextClusterCandidate();
    double distanceToFirst(LabelPair lastClustered);
    double distanceToSecond(LabelPair lastClustered);
    void updateDistanceMatrix(LabelPair oldNode, String newClusterNode);

}
