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
                        sequences.add(new AminoacidSequence(sequence, sequenceName));
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


    private static boolean isNucleotideSequence(String sequence) {
        return sequence.matches("[ACGT]*");
    }

    private static boolean isAminoacidSequence(String sequence) {
        return sequence.matches("[ARNDCEQGHILKMFPSTWYVXBZJ*]*");
    }

    /*private static boolean isAsciiSequence (String sequence) {
        return sequence.matches("[!\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~]*");
    }*/

    private static boolean isAsciiSequence(String sequence) {
        return sequence.matches("[\\x00-\\x7F]*");
    }



    }
