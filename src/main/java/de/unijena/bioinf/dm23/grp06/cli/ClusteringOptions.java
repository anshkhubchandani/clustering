package de.unijena.bioinf.dm23.grp06.cli;

import picocli.CommandLine;

import java.util.ArrayList;

public class ClusteringOptions implements Runnable{

    @CommandLine.Option(names = {"-h", "--help"}, usageHelp = true, description = "Prints help message.")
    protected boolean helpRequested;

    @CommandLine.ArgGroup(heading = "User input")
    Input userInput;

    static class Input {

        @CommandLine.ArgGroup(heading = "Input sequence")
        InputSequence userInputSequence;

        static class InputSequence {

            @CommandLine.Option(names = {"-s", "--seq"}, description = "Input sequences to be used (2 or more sequences or file paths).")
            ArrayList<String> inputSeq;

            @CommandLine.Option(names = {"-t", "--type"}, description = "Sequence type of input sequences (DNA, RNA, amino acid AA, or ASCII).")
            String sequenceType;
        }

        @CommandLine.Option(names = {"-m", "--matrix"}, description = "File path of a distance matrix as an input to create a phylogenic tree.")
        String distanceMatrixPath;
    }

    @CommandLine.Option(names = {"-c", "--clustering"}, description = "Clustering method.\n" +
            "WPGMA: Weighted Pair Group Method with Arithmetic Mean\nUPGMA: Unweighted Pair Group Method with Arithmetic Mean\n" +
            "NJ: Neighbour joining")
    String method;

    @CommandLine.Option(names = {"-o", "--output"}, description = "Saves results in an output file.")
    String outputFilePath;

    // Additional options here

    @Override
    public void run() {

    }
}
