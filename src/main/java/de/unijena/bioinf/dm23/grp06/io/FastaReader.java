package de.unijena.bioinf.dm23.grp06.io;

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

    public static <S extends Sequence> Object[] readFasta(Path filepath, int type) {
        ArrayList<S> sequences = new ArrayList<>();
        ArrayList<Kanten> kanten = new ArrayList<>(); //(vertex)
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
                kanten.add(new Kanten(sequenceName));
                StringBuilder sequence = new StringBuilder();
                while (true) {
                    newLine = file.readLine();
                    if(newLine == null || newLine.startsWith(">")) {
                        break;
                    }
                    if(newLine.endsWith(System.lineSeparator())) {
                        newLine = newLine.substring(0, newLine.length()-System.lineSeparator().length());
                    }
                    sequence.append(newLine);
                }
                sequences.add(sequences.get(sequences.size()-1), NucleotideSequence);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return new Object[]{sequences, type, kanten};
    }

    }
    }
