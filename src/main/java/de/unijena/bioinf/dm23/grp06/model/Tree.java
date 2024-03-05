package de.unijena.bioinf.dm23.grp06.model;

import java.util.ArrayList;

public interface Tree {

    TreeType getTreeType();
    ArrayList<TreeNode> getNodeList ();
    TreeNode getAvailableNode ();
    void setAvailableNode (TreeNode node);
    void addNodes (ArrayList<TreeNode> newNodes);
    void addANode (TreeNode node);
}
