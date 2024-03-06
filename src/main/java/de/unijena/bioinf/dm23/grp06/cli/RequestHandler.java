package de.unijena.bioinf.dm23.grp06.cli;

import de.unijena.bioinf.dm23.grp06.algo.*;
import de.unijena.bioinf.dm23.grp06.alignments.algo.Aligner;
import de.unijena.bioinf.dm23.grp06.alignments.algo.NeedlemanWunsch;
import de.unijena.bioinf.dm23.grp06.alignments.algo.Scorer;
import de.unijena.bioinf.dm23.grp06.alignments.model.Alignment;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.Sequence;
import de.unijena.bioinf.dm23.grp06.io.FastaReader;
import de.unijena.bioinf.dm23.grp06.model.TreeType;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

public class RequestHandler {

    Clustering clusteringAlgorithm;
    TreeBuilder treeBuilder;
    ArrayList<Sequence> inputSequences;
    HashMap<String, Sequence> sequenceLabels;

    public RequestHandler (String algorithm, String inputType, ArrayList<String> inputPath) {

    }

    private void connectSequencesToLabels (ArrayList<Sequence> sequences) {
        String label; // TODO !!!
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

    private void setClusteringAlgorithm (String algorithm, DistanceMatrix dm) {
        if (algorithm.equals("UPGMA")) {
            clusteringAlgorithm = new UPGMA(dm);
        }
        else if (algorithm.equals("WPGMA")) {
            clusteringAlgorithm = new WPGMA(dm);
        }
        else if (algorithm.equals("NJ")) {
            clusteringAlgorithm = new NeighbourJoining(dm);
        }
    }

    private void createTreeBuilder (HashMap<String, Sequence> leafLabels, TreeType type) {
        // TreeBuilder initialisieren
    }

    private String getStringForLabeling () {
        return "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    }
}