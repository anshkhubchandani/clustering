package de.unijena.bioinf.dm23.grp06.io;
import de.unijena.bioinf.dm23.grp06.model.Tree;
import de.unijena.bioinf.dm23.grp06.model.TreeNode;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

//Reader und Writer fuer FASTA Dateien
public class NewickWriter {

    public static void writeTreeToFile(Tree tree, String filepath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filepath))) {
            TreeNode root = tree.getAvailableNode();
            if (root != null) {
                writeNode(root, writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeNode(TreeNode node, BufferedWriter writer) throws IOException {
        if (node.isLeaf) {
            // Blattknoten, schreibe den Knotenwert
            writer.write(node.getLabel());
        } else {
            // Interner Knoten, schreibe rekursiv seine Kinder
            writer.write("(");
            writeNode(node.getFirstConnectedNode(), writer);
            writer.write(",");
            writeNode(node.getSecondConnectedNode(), writer);
            if (node.getThirdConnectedNode() != null) {
                writer.write(",");
                writeNode(node.getThirdConnectedNode(), writer);
            }
            writer.write(")");
            if (node.distanceToFirst > 0 || node.distanceToSecond > 0) {
                writer.write(":");
                writer.write(String.valueOf(node.distanceToFirst + node.distanceToSecond));
            }
        }
    }
}
