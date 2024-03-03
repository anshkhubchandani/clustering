package de.unijena.bioinf.dm23.grp06.algo;

import de.unijena.bioinf.dm23.grp06.algo.Clustering;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.Sequence;
import de.unijena.bioinf.dm23.grp06.model.*;

import java.util.ArrayList;
import java.util.HashMap;

public class TreeBuilder {

    HashMap<String, Sequence> listOfLeafLabels;
    ArrayList<Tree> listOfTrees; // Durch HashMap ersetzen?
    TreeType treeType;
    Clustering clusteringMethod;
    boolean finishedBuilding;

    public TreeBuilder (TreeType type, Clustering clusteringAlgorithm, HashMap<String, Sequence> leafLabels) {
        this.treeType = type;
        this.clusteringMethod = clusteringAlgorithm;
        this.listOfLeafLabels = leafLabels;
    }

    public void createLeaves () {
        String[] leaves = this.listOfLeafLabels.keySet().toArray(new String[0]);

        for (String leaf : leaves) {


        }
    }

    public void buildTree () {

        while (!this.finishedBuilding) {
            clusteringMethod.nextCluster();
            // TODO
        }
    }

    public void connectTrees (TreeNode connectingNode, Tree firstTree, Tree secondTree) {
        firstTree.getAvailableNode().setThirdConnectedNode(connectingNode);
        secondTree.getAvailableNode().setThirdConnectedNode(connectingNode);

        connectingNode.setFirstConnectedNode(firstTree.getAvailableNode());
        connectingNode.setSecondConnectedNode(secondTree.getAvailableNode());

        // TODO neuen Baum machen mit addNewTree ? Aber vorher Listen verknüpfen ?
    }

    private void addNewTree (ArrayList<TreeNode> nodes) {
        if (this.treeType.equals(TreeType.ROOTED)) {
            this.listOfTrees.add(new RootedTree(nodes));
        }
        else if (this.treeType.equals(TreeType.UNROOTED)) {
            this.listOfTrees.add(new UnrootedTree(nodes));
        }
    }

    private boolean isFinished () {
        return listOfTrees.size() == 1;
    }

}
