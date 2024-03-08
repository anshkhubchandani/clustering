package de.unijena.bioinf.dm23.clustering.model;

import de.unijena.bioinf.dm23.grp06.exactsearch.model.Sequence;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * The AbstractTree class provides a skeletal implementation of the Tree interface.
 */

public abstract class AbstractTree implements Tree {
    TreeNode availableNode;
    ArrayList<TreeNode> treeNodeList;
    HashMap<String, Sequence> listOfLeafLabels;
    /**
     * Constructs an AbstractTree with the given available node and list of tree nodes.
     *
     * @param available       the available node for the tree
     * @param nodesOfNewTree  the list of nodes in the tree
     */
    public AbstractTree (TreeNode available, ArrayList<TreeNode> nodesOfNewTree) {
        this.availableNode = available;
        this.treeNodeList = nodesOfNewTree;
    }

    /**
     * Constructs an AbstractTree with the given node.
     *
     * @param node  the node to be added to the tree
     */
    public AbstractTree (TreeNode node) {
        this.treeNodeList = new ArrayList<>();
        this.treeNodeList.add(node);
        this.availableNode = this.treeNodeList.get(0);
    }


    @Override
    public HashMap<String, Sequence> getListOfLeafLabels() {
        // TODO !!!
        return this.listOfLeafLabels;
    }

    @Override
    public ArrayList<TreeNode> getNodeList() {
        return this.treeNodeList;
    }

    @Override
    public void addNodesFromList(ArrayList<TreeNode> newNodes) {
        treeNodeList.addAll(newNodes);
    }

    @Override
    public void addNode(TreeNode node) {
        treeNodeList.add(node);
    }

    @Override
    public void setAvailableNode (TreeNode node) {
        this.availableNode = node;
    }

    @Override
    public TreeNode getAvailableNode() {
        return availableNode;
    }

    @Override
    public void setListOfLeafLabels(HashMap<String, Sequence> listOfLeafLabels) {
        this.listOfLeafLabels = listOfLeafLabels;
    }
}
