package de.unijena.bioinf.dm23.grp06;

import de.unijena.bioinf.dm23.grp06.algo.*;
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

        /*Path filePath = Paths.get("/Users/larry/Git/testfiles/aquifex-tRNA-clean.fasta");
        ArrayList<Sequence> sequences = FastaReader.readFasta(filePath);

        for (Sequence s : sequences) {
            System.out.println("Name: " + s.getName());
            System.out.println("Sequence: " +s.getSequence());
        }*/

        DistanceMatrix dm = new DistanceMatrix();
        dm.addDistance("A", "B", 2);
        dm.addDistance("A", "C", 5);
        dm.addDistance("A", "D", 6);
        dm.addDistance("B", "C", 3);
        dm.addDistance("B", "D", 8);
        dm.addDistance("C", "D", 11);
        Clustering test = new WPGMA(dm);

        LabelPair lp1 = test.nextClusterCandidate();
        System.out.println(lp1.getTaxon1() + " " + test.distanceToFirst(lp1) + " "

                +  lp1.getTaxon2() + " " + test.distanceToSecond(lp1));
        test.updateDistanceMatrix(lp1, "newCluster");
        LabelPair lp2 = test.nextClusterCandidate();
        System.out.println(lp2.getTaxon1() + " " + test.distanceToFirst(lp2) + " "
                +  lp2.getTaxon2() + " " + test.distanceToSecond(lp2));


        // /Users/larry/Git/testfiles/aquifex-tRNA-clean.fasta
    }
}