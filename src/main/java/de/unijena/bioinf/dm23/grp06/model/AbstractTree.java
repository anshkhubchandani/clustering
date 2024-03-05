package de.unijena.bioinf.dm23.grp06.model;

import java.util.ArrayList;

public abstract class AbstractTree implements Tree {

    TreeNode availableNode;
    ArrayList<TreeNode> treeNodeList;

    public AbstractTree (TreeNode available, ArrayList<TreeNode> nodesOfNewTree) {
        this.treeNodeList = nodesOfNewTree;
    }

    public AbstractTree (TreeNode node) {
        this.treeNodeList = new ArrayList<>();
        this.treeNodeList.add(node);
        this.availableNode = this.treeNodeList.get(0);
    }

    @Override
    public ArrayList<TreeNode> getNodeList() {
        return this.treeNodeList;
    }

    @Override
    public void addNodes (ArrayList<TreeNode> newNodes) {
        treeNodeList.addAll(newNodes);
    }

    @Override
    public void addANode(TreeNode node) {
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
    public boolean equals(Object obj) {
        // TODO
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
