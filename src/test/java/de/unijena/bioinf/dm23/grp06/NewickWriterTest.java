package de.unijena.bioinf.dm23.grp06;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.AsciiSequence;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.Sequence;
import de.unijena.bioinf.dm23.grp06.io.NewickWriter;
import de.unijena.bioinf.dm23.grp06.model.RootedTree;
import de.unijena.bioinf.dm23.grp06.model.Tree;
import de.unijena.bioinf.dm23.grp06.model.TreeNode;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NewickWriterTest {
    @Test
    void testWriteSimpleTreeToFile() {
        // Create a simple tree with three leaf nodes
        TreeNode leaf1 = new TreeNode("A", true);
        TreeNode leaf2 = new TreeNode("B", true);
        TreeNode leaf3 = new TreeNode("C", true);

        // Create an internal node connecting the three leaf nodes
        TreeNode internal1 = new TreeNode("Internal1", false);
        internal1.setFirstConnectedNode(leaf1, 0.1);
        internal1.setSecondConnectedNode(leaf2, 0.2);

        // Create another internal node connecting internal1 and leaf3
        TreeNode internal2 = new TreeNode("Internal2", false);
        internal2.setFirstConnectedNode(internal1, 0.3);
        internal2.setSecondConnectedNode(leaf3, 0.4);

        // Create the root
        TreeNode root = new TreeNode("Root", false);
        root.setFirstConnectedNode(internal2, 0.5);

        ArrayList<TreeNode> listOfNodes = new ArrayList<>();
        listOfNodes.add(leaf1);
        listOfNodes.add(leaf2);
        listOfNodes.add(leaf3);
        listOfNodes.add(internal1);
        listOfNodes.add(internal2);
        listOfNodes.add(root);
        Tree tree = new RootedTree(root, listOfNodes);

        HashMap <String, Sequence> leafLabels = new HashMap<>();
        leafLabels.put("A", new AsciiSequence("fsdfsf", "leaf1"));
        leafLabels.put("B", new AsciiSequence("fsfds", "leaf2"));
        leafLabels.put("C", new AsciiSequence("sefsdfs", "leaf3"));

        String expectedNewick = "(((A:0.1,B:0.2):0.3,C:0.4):0.5)";

        // Define the file path for the test output
        String filepath = "test_tree_simple.newick";

        // write the tree to file
        tree.setListOfLeafLabels(leafLabels);
        NewickWriter.writeTreeToFile(tree, tree.getListOfLeafLabels(), filepath);
        // Read the content of the file
        String actualNewick = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            actualNewick = reader.readLine().trim();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Assert that the actual Newick format string matches the expected one
        assertEquals(expectedNewick, actualNewick);

        // Clean up the test output file
        try {
            Files.deleteIfExists(Paths.get(filepath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void test2() {
        // Create a simple tree with three leaf nodes
        TreeNode leaf1 = new TreeNode("A", true);
        TreeNode leaf2 = new TreeNode("B", true);
        TreeNode leaf3 = new TreeNode("C", true);
        TreeNode leaf4 = new TreeNode("D", true);
        TreeNode leaf5 = new TreeNode("E", true);



        // Create an internal node connecting the three leaf nodes
        TreeNode internal1 = new TreeNode("Internal1", false);
        internal1.setFirstConnectedNode(leaf1, 1);
        internal1.setSecondConnectedNode(leaf2, 2);

        // Create another internal node connecting internal1 and leaf3
        TreeNode internal2 = new TreeNode("Internal2", false);
        internal2.setFirstConnectedNode(leaf3, 2);
        internal2.setSecondConnectedNode(leaf4, 3);

        TreeNode internal3 = new TreeNode("Internal3", false);
        internal3.setFirstConnectedNode(internal1, 1);
        internal3.setSecondConnectedNode(internal2, 4);

        TreeNode root = new TreeNode("root", false);
        root.setFirstConnectedNode(internal3, 2);
        root.setSecondConnectedNode(leaf5, 8);


        ArrayList<TreeNode> listOfNodes = new ArrayList<>();
        listOfNodes.add(leaf1);
        listOfNodes.add(leaf2);
        listOfNodes.add(leaf3);
        listOfNodes.add(leaf4);
        listOfNodes.add(leaf5);
        listOfNodes.add(internal1);
        listOfNodes.add(internal2);
        listOfNodes.add(internal3);
        listOfNodes.add(root);

        Tree tree = new RootedTree(root, listOfNodes);

        HashMap <String, Sequence> leafLabels = new HashMap<>();
        leafLabels.put("A", new AsciiSequence("fsdfsf", "leaf1"));
        leafLabels.put("B", new AsciiSequence("fsfds", "leaf2"));
        leafLabels.put("C", new AsciiSequence("sefsdfs", "leaf3"));
        leafLabels.put("D", new AsciiSequence("sefsdfs", "leaf4"));
        leafLabels.put("E", new AsciiSequence("sefsdfs", "leaf5"));


        String expectedNewick = "(((A:1,B:2):1,(C:2,D:3):4):2,E:8)";

        // Define the file path for the test output
        String filepath = "test_tree_simple.newick";

        // write the tree to file
        tree.setListOfLeafLabels(leafLabels);
        NewickWriter.writeTreeToFile(tree, tree.getListOfLeafLabels(), filepath);
        // Read the content of the file
        String actualNewick = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            actualNewick = reader.readLine().trim();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Assert that the actual Newick format string matches the expected one
        assertEquals(expectedNewick, actualNewick);

        // Clean up the test output file
        try {
            Files.deleteIfExists(Paths.get(filepath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}