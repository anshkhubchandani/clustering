package de.unijena.bioinf.dm23.clustering.io;

import de.unijena.bioinf.dm23.clustering.algo.DistanceMatrix;
import de.unijena.bioinf.dm23.clustering.algo.InvalidDistanceException;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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
    public static DistanceMatrix readMyMatrix(Path filepath) throws IOException {
        DistanceMatrix matrix = new DistanceMatrix();

        try (BufferedReader file = Files.newBufferedReader(filepath)) {
            String line;
            while ((line = file.readLine()) != null) {
                String[] parts = line.split("\\s+");
                if (parts.length != 3) {
                    throw new IOException("Invalid matrix format");
                }

                String taxon1 = parts[0];
                String taxon2 = parts[1];
                double distance = Double.parseDouble(parts[2]);

                matrix.addDistance(taxon1, taxon2, distance);
                matrix.addToNodeList(taxon1);
                matrix.addToNodeList(taxon2);
            }
        } catch (InvalidDistanceException e) {
            throw new RuntimeException(e);
        }

        return matrix;
    }

}
