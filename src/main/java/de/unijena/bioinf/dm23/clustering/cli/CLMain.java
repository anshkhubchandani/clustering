package de.unijena.bioinf.dm23.clustering.cli;

import picocli.CommandLine;

/**
 * The CLMain class is the entry point for the command-line interface (CLI) application.
 */
public class CLMain {
    /**
     * The main method of the CLI application.
     *
     * @param args the command-line arguments
     */


    public static void main (String[] args) {

        ClusteringOptions options = new ClusteringOptions();
        CommandLine clusteringCli = new CommandLine(options);
        CommandLine.ParseResult result = clusteringCli.parseArgs(args);

        if (args.length == 0 || result.isUsageHelpRequested()) {
            clusteringCli.usage(System.err);
        }
        else {
            clusteringCli.execute(args);
        }
    }

}