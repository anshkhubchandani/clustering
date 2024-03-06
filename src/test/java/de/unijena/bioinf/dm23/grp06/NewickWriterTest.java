package de.unijena.bioinf.dm23.grp06;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.Sequence;
import de.unijena.bioinf.dm23.grp06.io.NewickWriter;
import de.unijena.bioinf.dm23.grp06.model.Tree;
import de.unijena.bioinf.dm23.grp06.model.TreeNode;
import de.unijena.bioinf.dm23.grp06.model.TreeType;
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

        // Create the root node connecting internal2
        TreeNode root = new TreeNode("Root", false);
        root.setFirstConnectedNode(internal2, 0.5);


        // Create a tree object
        Tree tree = new Tree() {
            private TreeNode availableNode = root;
            private ArrayList<TreeNode> nodeList = new ArrayList<>();

            @Override
            public TreeType getTreeType() {
                return TreeType.ROOTED;
            }

            @Override
            public ArrayList<TreeNode> getNodeList() {
                return nodeList;
            }

            @Override
            public TreeNode getAvailableNode() {
                return availableNode;
            }

            @Override
            public void setAvailableNode(TreeNode node) {
                this.availableNode = node;
            }

            @Override
            public void addNodesFromList(ArrayList<TreeNode> newNodes) {
                nodeList.addAll(newNodes);
            }

            @Override
            public void addNode(TreeNode node) {
                nodeList.add(node);
            }

            @Override
            public HashMap<String, Sequence> getListOfLeafLabels() {
                return null;
            }
        };

        String expectedNewick = "(((A:0.1,B:0.2):0.3,C:0.4):0.5)";

        // Define the file path for the test output
        String filepath = "test_tree_simple.newick";

        // write the tree to file
        NewickWriter.writeTreeToFile(tree, filepath);

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