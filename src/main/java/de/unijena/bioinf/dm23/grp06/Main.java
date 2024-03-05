package de.unijena.bioinf.dm23.grp06;

import de.unijena.bioinf.dm23.grp06.exactsearch.model.Sequence;
import de.unijena.bioinf.dm23.grp06.io.FastaReader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;


// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws IOException {

        Path filePath = Paths.get("/Users/larry/Git/testfiles/aquifex-tRNA-clean.fasta");
        ArrayList<Sequence> sequences = FastaReader.readFasta(filePath);

        for (Sequence s : sequences) {
            System.out.println("Name: " + s.getName());
            System.out.println("Sequence: " +s.getSequence());
        }


        // /Users/larry/Git/testfiles/aquifex-tRNA-clean.fasta
    }
}