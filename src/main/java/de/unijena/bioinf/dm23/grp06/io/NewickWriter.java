package de.unijena.bioinf.dm23.grp06.io;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.Sequence;
import de.unijena.bioinf.dm23.grp06.model.Tree;
import de.unijena.bioinf.dm23.grp06.model.TreeNode;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.*;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

//Reader und Writer fuer FASTA Dateien
public class NewickWriter {
    private static final DecimalFormat decimalFormat = new DecimalFormat("#.##");

    public static void writeTreeToFile(Tree tree, HashMap<String, Sequence> labelMap , String filepath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepath))) {
            TreeNode root = tree.getAvailableNode();
            if (root != null) {
                writeNode(root, writer);
            }
            //LabelMap??
            for (Map.Entry<String, Sequence> entry : labelMap.entrySet()){
                writer.write(entry.getKey()+ ": " + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // finished writing, now read the content of the file and print it
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            System.out.println("Content of the output file:");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void writeNode(TreeNode node, BufferedWriter writer) throws IOException {
        if (node == null) {
            return; // Skip writing if the node is null
        }
        if (node.isLeaf) {
            // Leaf node, write the node label
            writer.write(node.getLabel());
        } else {
            // Internal node, recursively write the children
            writer.write("(");
            writeNode(node.getFirstConnectedNode(), writer);
            writer.write(":");
            writer.write(decimalFormat.format(node.distanceToFirst).replace(",", "."));
            TreeNode secondConnectedNode = node.getSecondConnectedNode();
            if (secondConnectedNode != null) {
                writer.write(",");
                writeNode(secondConnectedNode, writer);
                writer.write(":");
                writer.write(decimalFormat.format(node.distanceToSecond).replace(",", "."));
            }
            writer.write(")");
        }
    }

}
