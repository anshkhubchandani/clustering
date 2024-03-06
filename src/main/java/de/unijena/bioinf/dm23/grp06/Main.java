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
        dm.addToNodeList("A");
        dm.addToNodeList("B");
        dm.addToNodeList("C");
        dm.addToNodeList("D");
        Clustering test = new UPGMA(dm);

        LabelPair lp1 = test.nextClusterCandidate();
        System.out.println(lp1.getTaxon1() + " " + test.distanceToFirst(lp1) + " "

                +  lp1.getTaxon2() + " " + test.distanceToSecond(lp1));
        test.updateDistanceMatrix(lp1, lp1.getTaxon1().concat("-").concat(lp1.getTaxon2()));
        LabelPair lp2 = test.nextClusterCandidate();
        System.out.println(lp2.getTaxon1() + " " + test.distanceToFirst(lp2) + " "
                +  lp2.getTaxon2() + " " + test.distanceToSecond(lp2));
        test.updateDistanceMatrix(lp2, lp2.getTaxon1().concat("-").concat(lp2.getTaxon2()));
        LabelPair lp3 = test.nextClusterCandidate();
        System.out.println(lp3.getTaxon1() + " " + test.distanceToFirst(lp3) + " "
                +  lp3.getTaxon2() + " " + test.distanceToSecond(lp3));



        // /Users/larry/Git/testfiles/aquifex-tRNA-clean.fasta
    }
}