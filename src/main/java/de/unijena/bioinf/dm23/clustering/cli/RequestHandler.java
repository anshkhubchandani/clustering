package de.unijena.bioinf.dm23.clustering.cli;

import de.unijena.bioinf.dm23.clustering.algo.*;
import de.unijena.bioinf.dm23.clustering.model.TreeType;
import de.unijena.bioinf.dm23.grp06.alignments.algo.NeedlemanWunsch;
import de.unijena.bioinf.dm23.grp06.alignments.algo.Scorer;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.AsciiSequence;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.Sequence;
import de.unijena.bioinf.dm23.clustering.io.DistanceMatrixReader;
import de.unijena.bioinf.dm23.clustering.io.FastaReader;
import de.unijena.bioinf.dm23.clustering.io.NewickWriter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
/**
 * The RequestHandler class manages the processing of clustering requests from the command line.
 */
public class RequestHandler {
    Clustering clusteringAlgorithm;
    TreeBuilder treeBuilder;
    ArrayList<Sequence> inputSequences;
    HashMap<String, Sequence> sequenceLabels;
    TreeType treeType;
    String inputType;
    String algorithm;

    /**
     * Constructs a RequestHandler object with the given algorithm and input sequences.
     *
     * @param algorithm   the clustering algorithm to use
     * @param inputPaths  the paths to input sequences
     */
    public RequestHandler (String algorithm, ArrayList<String> inputPath) {
        this.algorithm = algorithm;
        this.inputType = "sequences";

        readAllInputSequences(inputPath);
        assignLabelsToSequences();
        DistanceMatrix dm = createDistancematrix(createAlignments());

        if (dm.checkMetric()) {
            createClusteringAndTreeBuilder(algorithm, dm);
            treeBuilder.buildTree();
        }
        else {
            throw new IllegalArgumentException("The distance matrix is not a metric.");
        }

    }
    /**
     * Constructs a RequestHandler object with the given algorithm and distance matrix path.
     *
     * @param algorithm           the clustering algorithm to use
     * @param distanceMatrixPath  the path to the distance matrix file
     */
    public RequestHandler (String algorithm, String distanceMatrixPath) {
        this.algorithm = algorithm;
        this.inputType = "distance matrix";

        DistanceMatrix dm = readMatrix(distanceMatrixPath);

        if (dm.checkMetric()) {
            createLabelHashMapFromMatrix(dm);
            createClusteringAndTreeBuilder(algorithm, dm);
            treeBuilder.buildTree();
        }
        else {
            throw new IllegalArgumentException("The input distance matrix is not a metric.");
        }
    }

    private void readAllInputSequences (ArrayList<String> inputPaths) {
        File filepath;
        try {
            for (String path : inputPaths) {
                filepath = new File(path);
                if (filepath.isFile()) {
                    this.inputSequences.addAll(readFastaFromFile(Paths.get(path)));
                }
                else {
                    this.inputSequences.add(new AsciiSequence(path, ("User input sequence: " + path)));
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Sequence> readFastaFromFile(Path filepath) throws IOException {
        try {
            return FastaReader.readFasta(filepath);
        } catch (IOException e) {
            throw new IOException("ERROR: Fasta file can not be read.", e);
        }
    }


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

    private void assignLabelsToSequences () {
        String label;
        sequenceLabels = new HashMap<>();
        for (int i = 1; i <= this.inputSequences.size(); i++) {
            label = "Seq" + i;
            sequenceLabels.put(label, this.inputSequences.get(i-1));
        }
    }

    private void createLabelHashMapFromMatrix (DistanceMatrix dm) {
        ArrayList<String> nodesInMatrix = dm.getNodesInMatrix();
        int i = 1;
        for (String s : nodesInMatrix) {
            this.sequenceLabels.put(s, new AsciiSequence("Unknown Sequence", ("Inputnode" + i)));
            i++;
        }
    }

    private ArrayList<NeedlemanWunsch<Sequence>> createAlignments () {
        // globales Alignment

        ArrayList<NeedlemanWunsch<Sequence>> alignmentObjects = new ArrayList<>();
        Scorer scorer = new Scorer();
        int k = 0;

        for (int i = 0; i < this.inputSequences.size(); i++) {
            for (int j = k; j < this.inputSequences.size(); j++) {
                if (!(i == j)) {
                    alignmentObjects.add(new NeedlemanWunsch<>(inputSequences.get(i), inputSequences.get(k), scorer));
                }
            }
            k++;
        }
        return alignmentObjects;
    }

    private DistanceMatrix createDistancematrix (ArrayList<NeedlemanWunsch<Sequence>> alignerObjects) {
        DistanceMatrix dm = new DistanceMatrix();
        HashMap<String, Sequence> leafLabels;
        String firstLabel;
        String secondLabel;

        for (NeedlemanWunsch<Sequence> a : alignerObjects) {

            firstLabel = lookForLabel(a.getSequence1().getName());
            secondLabel = lookForLabel(a.getSequence2().getName());
            try {
                dm.addDistance(firstLabel, secondLabel, a.linearScore());
            } catch (InvalidDistanceException e) {
                System.err.println("Error adding distance to distance matrix: " + e.getMessage());
            }
        }
        return dm;
    }

    private String lookForLabel (String sequenceHeader) {
        String[] keys = this.sequenceLabels.keySet().toArray(new String[0]);

        for (String key : keys) {
            if (this.sequenceLabels.get(key).getName().equals(sequenceHeader)) {
                return key;
            }
        }
        return null;
    }

    private void setClusteringAlgorithm (String algorithm, DistanceMatrix distanceMatrix) {
        if (algorithm.equals("UPGMA")) {
            clusteringAlgorithm = new UPGMA(distanceMatrix);
        } else if (algorithm.equals("WPGMA")) {
            clusteringAlgorithm = new WPGMA(distanceMatrix);
        } else if (algorithm.equals("NJ")){
            clusteringAlgorithm = new NeighbourJoining(distanceMatrix);
        }
    }

    private void createTreeBuilder () {
        this.treeBuilder = new TreeBuilder(this.treeType, clusteringAlgorithm, this.sequenceLabels);
    }

    protected void writeTreeToNewick (String outputFilePath) {
        if (this.treeBuilder.isFinishedBuilding()) {
            NewickWriter.writeTreeToFile(this.treeBuilder.getFinishedTree(), this.sequenceLabels, outputFilePath);
        }
    }

    private void setTreeType (String algo) {
        if (algo.equals("UPGMA") || algo.equals("WPGMA")) {
            this.treeType = TreeType.ROOTED;
        }
        else if (algo.equals("NJ")) {
            this.treeType = TreeType.UNROOTED;
        }
    }

    private void createClusteringAndTreeBuilder (String algorithm, DistanceMatrix dm) {
        setTreeType(algorithm);
        setClusteringAlgorithm(algorithm, dm);
        createTreeBuilder();
    }

    protected void printInfo () {
        StringBuilder sb = new StringBuilder();
        sb.append("Algorithm: ").append(this.algorithm).append("\n");
        sb.append("Input type: ").append(this.inputType).append("\n");
        sb.append(this.sequenceLabels.size()).append(" sequences in total.\n");
    }
}
