package de.unijena.bioinf.dm23.grp06.model;

import java.util.ArrayList;

public abstract class AbstractTree implements Tree {

    TreeNode availableNode;
    ArrayList<TreeNode> treeNodeList;

    public AbstractTree (ArrayList<TreeNode> nodesOfNewTree) {
        this.treeNodeList = nodesOfNewTree;
    }

    @Override
    public TreeNode getAvailableNode() {
        return availableNode;
    }
}
