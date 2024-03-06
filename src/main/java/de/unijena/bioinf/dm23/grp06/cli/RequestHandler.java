package de.unijena.bioinf.dm23.grp06.cli;

import de.unijena.bioinf.dm23.grp06.algo.*;
import de.unijena.bioinf.dm23.grp06.alignments.algo.NeedlemanWunsch;
import de.unijena.bioinf.dm23.grp06.alignments.algo.Scorer;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.Sequence;
import de.unijena.bioinf.dm23.grp06.io.DistanceMatrixReader;
import de.unijena.bioinf.dm23.grp06.model.TreeType;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class RequestHandler {
    String algo;
    Clustering clusteringAlgorithm;
    TreeBuilder treeBuilder;
    DistanceMatrix distanceMatrix;
    ArrayList<Sequence> inputSequences;
    HashMap<String, Sequence> sequenceLabels;

    public RequestHandler (String algorithm, ArrayList<String> inputPath, TreeType type) {
        this.algo = algorithm;

        // setClusteringAlgorithm(algorithm, distanceMatrix);
    }

    public RequestHandler (String algorithm, String distanceMatrixPath, TreeType type) {
        // this.algo = algorithm;
        setClusteringAlgorithm(algorithm, readMatrix(distanceMatrixPath));
    }

    // TODO readFastaFiles

    private DistanceMatrix readMatrix (String distanceMatrixPath) {
        DistanceMatrix dm = new DistanceMatrix();
        try {
            dm = DistanceMatrixReader.readMyMatrix(Paths.get(distanceMatrixPath));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return dm;
    }

    private void connectSequencesToLabels (ArrayList<Sequence> sequences) {
        String label;
        sequenceLabels = new HashMap<>();
        for (int i = 1; i <= sequences.size(); i++) {
            label = "Seq" + Integer.toString(i);
            sequenceLabels.put(label, sequences.get(i-1));
        }
    }

    private void createAlignments (ArrayList<Sequence> inputSequences) {
        // globales Alignment

        ArrayList<NeedlemanWunsch<Sequence>> alignmentObjects = new ArrayList<>();
        Scorer scorer = new Scorer();
        int k = 0;

        for (int i = 0; i < inputSequences.size(); i++) {
            for (int j = k; j < inputSequences.size(); j++) {
                if (!(i == j)) {
                    alignmentObjects.add(new NeedlemanWunsch<>(inputSequences.get(i), inputSequences.get(k), scorer));
                }
            }
            k++;
        }
    }

    private void createDistancematrix (ArrayList<NeedlemanWunsch<Sequence>> alignerObjects) {
        DistanceMatrix dm = new DistanceMatrix();
        for (NeedlemanWunsch<Sequence> a : alignerObjects) {
            // TODO !!!

        }
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
}
