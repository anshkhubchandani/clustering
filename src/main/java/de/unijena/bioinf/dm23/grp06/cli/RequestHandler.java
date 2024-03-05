package de.unijena.bioinf.dm23.grp06.cli;

import de.unijena.bioinf.dm23.grp06.algo.*;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.Sequence;
import de.unijena.bioinf.dm23.grp06.model.TreeType;

import java.util.ArrayList;
import java.util.HashMap;

public class RequestHandler {

    Clustering clusteringAlgorithm;
    TreeBuilder treeBuilder;

    public RequestHandler (String algorithm, ArrayList<String> inputPath) {

    }

    private void createAlignments () {
        // globales Alignment

    }

    private void setClusteringAlgorithm (String algorithm, DistanceMatrix dm) {

    }

    private void createTreeBuilder (HashMap<String, Sequence> leafLabels, TreeType type) {
        // TreeBuilder initialisieren
    }


    // TreeBuilder als Parameter für Clustering!

}
