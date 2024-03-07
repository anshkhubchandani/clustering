package de.unijena.bioinf.dm23.grp06.cli;

import de.unijena.bioinf.dm23.grp06.exactsearch.model.MissingInputsException;
import picocli.CommandLine;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ClusteringOptions implements Runnable{

    @CommandLine.Option(names = {"-h", "--help"}, usageHelp = true, description = "Prints help message.")
    protected boolean helpRequested;

    @CommandLine.ArgGroup(heading = "User input", exclusive = true)
    Input userInput;

    static class Input {

        @CommandLine.Option(names = {"-s", "--seq"}, description = "Input sequences to be used.", split = " ", arity = "1...*")
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

    private void writeInOutput (RequestHandler userRequest) {
        if (this.outputFilePath != null) {
            userRequest.writeTreeToNewick(this.outputFilePath);
        }
    }

    private boolean checkIfCorrectInput () {
        if (this.userInput.inputSeq == null && this.userInput.distanceMatrixPath == null) {
            throw new MissingInputsException("Input sequence or distance matrix required.");
        }
        else if (this.method == null) {
            throw new MissingInputsException("Clustering algorithm required.");
        }
        else if (this.outputFilePath == null) {
            throw new MissingInputsException("File name or file path of an output file required.");
        }
        else if (checkIfFileAlreadyExists()) {
            throw new MissingInputsException("An output file with this name already exists.");
        }
        return true;
    }

    private boolean checkIfFileAlreadyExists () {
        File file = new File(this.outputFilePath);
        return file.isFile();
    }
}
