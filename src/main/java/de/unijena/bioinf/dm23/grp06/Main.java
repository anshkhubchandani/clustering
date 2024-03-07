package de.unijena.bioinf.dm23.grp06;

import de.unijena.bioinf.dm23.grp06.algo.*;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.AsciiSequence;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.Sequence;
import de.unijena.bioinf.dm23.grp06.io.FastaReader;
import de.unijena.bioinf.dm23.grp06.io.NewickWriter;
import de.unijena.bioinf.dm23.grp06.model.RootedTree;
import de.unijena.bioinf.dm23.grp06.model.Tree;
import de.unijena.bioinf.dm23.grp06.model.TreeNode;
import de.unijena.bioinf.dm23.grp06.model.TreeType;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws IOException, InvalidDistanceException {

        /*Path filePath = Paths.get("/Users/larry/Git/testfiles/aquifex-tRNA-clean.fasta");
        ArrayList<Sequence> sequences = FastaReader.readFasta(filePath);

        for (Sequence s : sequences) {
            System.out.println("Name: " + s.getName());
            System.out.println("Sequence: " +s.getSequence());
        }*/

        /*
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
        */

        DistanceMatrix dm = new DistanceMatrix();
        dm.addDistance("Mensch", "Maus", 3);
        dm.addDistance("Mensch", "Rose", 14);
        dm.addDistance("Mensch", "Tulpe", 12);
        dm.addDistance("Maus", "Rose", 13);
        dm.addDistance("Maus", "Tulpe", 11);
        dm.addDistance("Rose", "Tulpe", 4);
        dm.addToNodeList("Mensch");
        dm.addToNodeList("Maus");
        dm.addToNodeList("Rose");
        dm.addToNodeList("Tulpe");
        Clustering test = new NeighbourJoining(dm);


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


        // /Users/larry/Git/testfiles/aquifex-tRNA-clean.fasta */

        //Sample tree for NewickWriter
        /* TreeNode leaf1 = new TreeNode("A", true); // A
        TreeNode leaf2 = new TreeNode("B", true); // B
        TreeNode leaf3 = new TreeNode("C", true); // C
        TreeNode internal1 = new TreeNode("Internal1",false); // Internal1
        internal1.setFirstConnectedNode(leaf1, 0.1);
        internal1.setSecondConnectedNode(leaf2, 0.2);
        TreeNode root = new TreeNode("Root",false); // Root
        root.setFirstConnectedNode(internal1, 0.3);
        root.setSecondConnectedNode(leaf3, 0.4);

        ArrayList<TreeNode> listOfNodes = new ArrayList<>();
        listOfNodes.add(leaf1);
        listOfNodes.add(leaf2);
        listOfNodes.add(leaf3);
        listOfNodes.add(internal1);
        listOfNodes.add(root);

        // Create a sample label map
        HashMap<String, String> labelMap = new HashMap<>();
        labelMap.put("A", "AGCT");
        labelMap.put("B", "ACCT");
        labelMap.put("C", "TGAA");
        labelMap.put("Internal1", "CTAC");
        labelMap.put("Root", "AACTS");

        HashMap <String, Sequence> leafLabels = new HashMap<>();
        leafLabels.put("A", new AsciiSequence("fsdfsf", "leaf1"));
        leafLabels.put("B", new AsciiSequence("fsfds", "leaf2"));
        leafLabels.put("C", new AsciiSequence("sefsdfs", "leaf3"));

        // Create a tree object
        Tree tree = new RootedTree(root, listOfNodes);
        tree.setListOfLeafLabels(leafLabels);
        String filepath = "tree.newick";
        NewickWriter.writeTreeToFile(tree, tree.getListOfLeafLabels(), filepath);
        System.out.println("Tree written to file: " + filepath); */

       // System.out.println("A: " + labelMap.get("A"));
        /*for (Map.Entry<String,String> entry : labelMap.entrySet()){
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }*/

        // Test writing the tree to a file


    }
    }