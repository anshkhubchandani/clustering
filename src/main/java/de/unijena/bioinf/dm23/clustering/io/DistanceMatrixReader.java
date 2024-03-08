package de.unijena.bioinf.dm23.clustering.io;

import de.unijena.bioinf.dm23.clustering.algo.DistanceMatrix;
import de.unijena.bioinf.dm23.clustering.algo.InvalidDistanceException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The DistanceMatrixReader class provides methods for reading distance matrices from files.
 */
public class DistanceMatrixReader {

    /**
     * Reads a distance matrix from the specified file path.
     *
     * @param filepath the path to the file containing the distance matrix
     * @return a DistanceMatrix object containing the read matrix
     * @throws IOException if an I/O error occurs while reading the file
     */
    public static DistanceMatrix readMyMatrix (String filepath) throws IOException {
        DistanceMatrix matrix = new DistanceMatrix();
        ArrayList<String> linesOfFile = new ArrayList<>();

        try (BufferedReader fileReader = new BufferedReader(new FileReader(filepath))) {

            String line;

            while ((line = fileReader.readLine()) != null) {

                linesOfFile.add(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        boolean matrixReached = false;
        ArrayList<String> firstLabels = new ArrayList<>();
        String[] nextMatrixLine;
        String secondLabel;
        int k = 2;

        for (String line : linesOfFile) {

            if ((!line.isBlank()) && line.startsWith("   ")) {
                matrixReached = true;
                firstLabels = new ArrayList<>(Arrays.asList(line.trim().split("\\s+")));
                matrix.addAllLabelsToNodeList(firstLabels);
                continue;
            }

            if (matrixReached) {
                nextMatrixLine = line.trim().split("\\s+");
                secondLabel = nextMatrixLine[0];
                for (int i = k; i < nextMatrixLine.length; i++) {

                    try {
                        if (!firstLabels.get(i-1).equals(nextMatrixLine[i])) {

                            matrix.addDistance(firstLabels.get(i-1), secondLabel, Double.parseDouble(nextMatrixLine[i]));
                        }
                    }
                    catch (InvalidDistanceException e) {
                        e.printStackTrace();
                    }
                }
                k++;
            }

        }

        return matrix;
    }

}
