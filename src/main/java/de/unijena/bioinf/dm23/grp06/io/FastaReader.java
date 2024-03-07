package de.unijena.bioinf.dm23.grp06.io;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * The FastaReader class provides methods for reading sequences from FASTA files.
 */
public class FastaReader {

    /**
     * Reads sequences from the specified FASTA file.
     *
     * @param filepath the path to the FASTA file
     * @return an ArrayList of Sequence objects representing the sequences in the FASTA file
     * @throws IOException if an I/O error occurs while reading the file
     * @throws FastaFileException if the FASTA file format is invalid
     */

    public static ArrayList<Sequence> readFasta(Path filepath) throws IOException {
        ArrayList<Sequence> sequences = new ArrayList<>();
        //ArrayList<TreeNode> node = new ArrayList<>();
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
                //node.add(new TreeNode(sequenceName));
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
                        sequences.add(new AsciiSequence(sequence, sequenceName));
                    }else {
                        throw new InvalidCharactersException("ERROR: The given sequence contains invalid characters.");
                    }
                } catch (InvalidCharactersException e) {
                    e.printStackTrace();
                }

            }
        } catch (IOException e) {
            throw new IOException("ERROR: An I/O error occurred while reading the FASTA file.", e);
        }
        return sequences;
    }

    /**
     * Checks if the given sequence contains only nucleotide characters (A, C, G, T).
     *
     * @param sequence the sequence to check
     * @return true if the sequence contains only nucleotide characters, false otherwise
     */
    private static boolean isNucleotideSequence(String sequence) {
        return sequence.matches("[ACGT]*");
    }

    /**
     * Checks if the given sequence contains only amino acid characters.
     *
     * @param sequence the sequence to check
     * @return true if the sequence contains only amino acid characters, false otherwise
     */
    private static boolean isAminoacidSequence(String sequence) {
        return sequence.matches("[ARNDCEQGHILKMFPSTWYVXBZJ*]*");
    }

    /**
     * Checks if the given sequence contains only ASCII characters.
     *
     * @param sequence the sequence to check
     * @return true if the sequence contains only ASCII characters, false otherwise
     */
    private static boolean isAsciiSequence(String sequence) {
        return sequence.matches("[\\x00-\\x7F]*");
    }
    }
