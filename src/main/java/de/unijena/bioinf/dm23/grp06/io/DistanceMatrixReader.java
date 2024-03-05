package de.unijena.bioinf.dm23.grp06.io;

import de.unijena.bioinf.dm23.grp06.algo.DistanceMatrix;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DistanceMatrixReader {

    /**read the file line by line.
     For each line, split it into three parts: the names of the two taxa and the distance between them.
     We add this information to the DistanceMatrix object using the addDistance method. **/

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
            }
        }

        return matrix;
    }

}
