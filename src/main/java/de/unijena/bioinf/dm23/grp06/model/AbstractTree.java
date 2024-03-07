package de.unijena.bioinf.dm23.grp06.model;

import de.unijena.bioinf.dm23.grp06.exactsearch.model.Sequence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public abstract class AbstractTree implements Tree {

    TreeNode availableNode;
    ArrayList<TreeNode> treeNodeList;
    HashMap<String, Sequence> listOfLeafLabels;

    public AbstractTree (TreeNode available, ArrayList<TreeNode> nodesOfNewTree) {
        this.availableNode = available;
        this.treeNodeList = nodesOfNewTree;
    }

    public AbstractTree (TreeNode node) {
        this.treeNodeList = new ArrayList<>();
        this.treeNodeList.add(node);
        this.availableNode = this.treeNodeList.get(0);
    }

    @Override
    public HashMap<String, Sequence> getListOfLeafLabels() {
        // TODO !!!
        return null;
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
