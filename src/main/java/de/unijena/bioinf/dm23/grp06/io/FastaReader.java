package de.unijena.bioinf.dm23.grp06.io;

import de.unijena.bioinf.dm23.grp06.algo.DistanceMatrix;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.AminoacidSequence;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.InvalidCharactersException;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.NucleotideSequence;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.Sequence;
import de.unijena.bioinf.dm23.grp06.model.Tree;
import de.unijena.bioinf.dm23.grp06.model.TreeNode;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

//Reader und Writer fuer FASTA Dateien

public class FastaReader {

    public static ArrayList<Sequence> readFasta(Path filepath) {
        ArrayList<Sequence> sequences = new ArrayList<>();
        ArrayList<TreeNode> node = new ArrayList<>();
        String newLine = "";
        String sequenceName;

        try(BufferedReader file = Files.newBufferedReader(filepath)) {
            while(!newLine.startsWith(">")) {
                newLine = file.readLine();
                if(newLine == null) {
                    throw new FastaFileException();
                }
            }
            while(newLine != null) {
                sequenceName = newLine.substring(1);
                node.add(new TreeNode(sequenceName));
                StringBuilder sequenceBuilder = new StringBuilder();
                while (true) {
                    newLine = file.readLine();
                    if(newLine == null || newLine.startsWith(">")) {
                        break;
                    }
                    if(newLine.endsWith(System.lineSeparator())) {
                        newLine = newLine.substring(0, newLine.length()-System.lineSeparator().length());
                    }
                    sequenceBuilder.append(newLine);
                }
                String sequence = sequenceBuilder.toString();
                try {
                    if (isNucleotideSequence(sequence)) {
                        sequences.add(new NucleotideSequence(sequence, sequenceName, "DNA")); // Assuming DNA by default
                    } else if (isAminoacidSequence(sequence)) {
                        sequences.add(new AminoacidSequence(sequence, sequenceName));
                    }
                    else if (isAsciiSequence(sequence)) {
                        sequences.add(new AminoacidSequence(sequence, sequenceName));
                    }else {
                        throw new InvalidCharactersException("ERROR: The given sequence contains invalid characters.");
                    }
                } catch (InvalidCharactersException e) {
                    e.printStackTrace();
                }

            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return sequences;
    }


    private static boolean isNucleotideSequence(String sequence) {
        return sequence.matches("[ACGT]*");
    }

    private static boolean isAminoacidSequence(String sequence) {
        return sequence.matches("[ARNDCEQGHILKMFPSTWYVXBZJ*]*");
    }

    private static boolean isAsciiSequence (String sequence) {
        return sequence.matches("[!\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~]*");
    }

    /*private static boolean isAsciiSequence(String sequence) {
        return sequence.matches("[\\x00-\\x7F]*");
    }*/


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
