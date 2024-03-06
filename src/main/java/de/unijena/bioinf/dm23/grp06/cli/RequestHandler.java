package de.unijena.bioinf.dm23.grp06.cli;

import de.unijena.bioinf.dm23.grp06.algo.*;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.Sequence;
import de.unijena.bioinf.dm23.grp06.model.TreeType;

import java.util.ArrayList;
import java.util.HashMap;

public class RequestHandler {
    String algo;
    Clustering clusteringAlgorithm;
    TreeBuilder treeBuilder;
    DistanceMatrix distanceMatrix;

    public RequestHandler (String algorithm, ArrayList<String> inputPath,HashMap<String, Sequence> leafLabels, TreeType type) {
        this.algo = algorithm;
        createAlignments();
        setClusteringAlgorithm(algorithm, distanceMatrix);
        createTreeBuilder(leafLabels, type);
    }

    private void createAlignments () {
        //globales Alignment
        //Distanzen berechnen?

    }

    private void setClusteringAlgorithm (String algorithm, DistanceMatrix distanceMatrix) {
        if (algorithm.equals("UPGMA")) {
            clusteringAlgorithm = new UPGMA(distanceMatrix);
        } else if (algorithm.equals("WPGMA")) {
            clusteringAlgorithm = new WPGMA(distanceMatrix);
        } else if (algorithm.equals("NeighbourJoining")){
            clusteringAlgorithm = new NeighbourJoining(distanceMatrix);
        }
    }

    private void createTreeBuilder (HashMap<String, Sequence> leafLabels, TreeType type) {
        // TreeBuilder initialisieren
        TreeBuilder treeBuilder = new TreeBuilder(type, clusteringAlgorithm, leafLabels);
    }


    // TreeBuilder als Parameter für Clustering!

}
