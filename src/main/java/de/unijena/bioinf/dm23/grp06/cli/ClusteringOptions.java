package de.unijena.bioinf.dm23.grp06.cli;

import de.unijena.bioinf.dm23.grp06.exactsearch.model.MissingInputsException;
import picocli.CommandLine;

import java.io.IOException;
import java.util.ArrayList;

/**
 * The ClusteringOptions class represents the command-line options for the clustering application.
 */
public class ClusteringOptions implements Runnable{

    @CommandLine.Option(names = {"-h", "--help"}, usageHelp = true, description = "Prints help message.")
    protected boolean helpRequested;

    @CommandLine.ArgGroup(heading = "User input", exclusive = true)
    Input userInput;

    static class Input {

        @CommandLine.Option(names = {"-s", "--seq"}, description = "Input sequences to be used.")
        ArrayList<String> inputSeq;

        @CommandLine.Option(names = {"-m", "--matrix"}, description = "File path of a distance matrix as an input to create a phylogenic tree.")
        String distanceMatrixPath;
    }

    @CommandLine.Option(names = {"-c", "--clustering"}, description = "Clustering method.\n" +
            "WPGMA: Weighted Pair Group Method with Arithmetic Mean\nUPGMA: Unweighted Pair Group Method with Arithmetic Mean\n" +
            "NJ: Neighbour joining")
    String method;

    @CommandLine.Option(names = {"-o", "--output"}, description = "Saves resulting tree in Newick format in an output file.")
    String outputFilePath;

    // Additional options here

    /**
     * Runs the clustering application.
     */
    @Override
    public void run() {
        RequestHandler userRequestHandler;

        if (checkIfCorrectInput()) {
            if (userInput.inputSeq != null) {
                userRequestHandler = new RequestHandler(this.method, this.userInput.inputSeq);
                userRequestHandler.printInfo();
                writeInOutput(userRequestHandler);
            }
            else if (userInput.distanceMatrixPath != null) {
                userRequestHandler = new RequestHandler(this.method, this.userInput.distanceMatrixPath);
                userRequestHandler.printInfo();
                writeInOutput(userRequestHandler);
            }
        }

    }
    /**
     * Writes the result of clustering to an output file if specified.
     *
     * @param userRequest the user request handler
     */
    private void writeInOutput (RequestHandler userRequest) {
        if (this.outputFilePath != null) {
            userRequest.writeTreeToNewick(this.outputFilePath);
        }
    }
    /**
     * Writes the result of clustering to an output file if specified.
     *
     * @param userRequest the user request handler
     */
    private boolean checkIfCorrectInput () {
        if (this.userInput.inputSeq == null && this.userInput.distanceMatrixPath == null) {
            throw new MissingInputsException("Input sequence or distance matrix required.");
        }
        else if (this.method == null) {
            throw new MissingInputsException("Clustering algorithm required.");
        }
        else if (this.outputFilePath == null) {
            // TODO checken, ob File schon existiert wenn ja -> Fehler
            throw new MissingInputsException("File name or path of output file required.");
        }
        return true;
    }
}
