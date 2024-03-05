package de.unijena.bioinf.dm23.grp06.algo;

import de.unijena.bioinf.dm23.grp06.exactsearch.model.Sequence;
import de.unijena.bioinf.dm23.grp06.model.*;

import java.util.ArrayList;
import java.util.HashMap;

public class TreeBuilder {

    HashMap<String, Sequence> listOfLeafLabels;
    ArrayList<Tree> listOfTrees; // Durch HashMap ersetzen?
    HashMap<String, Tree> mapOfTrees;
    TreeType treeType;
    Clustering clusteringMethod;
    boolean finishedBuilding;

    public TreeBuilder (TreeType type, Clustering clusteringAlgorithm, HashMap<String, Sequence> leafLabels) {
        this.treeType = type;
        this.clusteringMethod = clusteringAlgorithm;
        this.listOfLeafLabels = leafLabels;
        createLeaves();
    }

    public void createLeaves () {
        String[] leaves = this.listOfLeafLabels.keySet().toArray(new String[0]);

        for (String leaf : leaves) {
            addNewLeaf(new TreeNode(leaf));
        }
    }

    /*public void buildTree () {

        while (!this.finishedBuilding) {
            clusteringMethod.nextCluster();
            // TODO

        }
    }*/

    protected void connectTrees (TreeNode connectingNode, Tree firstTree, Tree secondTree) {
        connectingNode.setFirstConnectedNode(firstTree.getAvailableNode());
        connectingNode.setSecondConnectedNode(secondTree.getAvailableNode());

        firstTree.getAvailableNode().setThirdConnectedNode(connectingNode);
        secondTree.getAvailableNode().setThirdConnectedNode(connectingNode);

        firstTree.addANode(connectingNode);
        firstTree.addNodes(secondTree.getNodeList());

    }

    private void addNewLeaf (TreeNode node) {
        if (this.treeType.equals(TreeType.ROOTED)) {
            this.mapOfTrees.put(node.getLabel(), new RootedTree(node));
        }
        else if (this.treeType.equals(TreeType.UNROOTED)) {
            this.mapOfTrees.put(node.getLabel(), new UnrootedTree(node));
        }
    }
}
