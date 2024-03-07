package de.unijena.bioinf.dm23.grp06.model;

import de.unijena.bioinf.dm23.grp06.exactsearch.model.Sequence;

import java.util.ArrayList;
import java.util.HashMap;

public interface Tree {

    TreeType getTreeType();
    ArrayList<TreeNode> getNodeList ();
    TreeNode getAvailableNode ();
    void setAvailableNode (TreeNode node);
    void addNodesFromList (ArrayList<TreeNode> newNodes);
    void addNode(TreeNode node);
    HashMap<String, Sequence> getListOfLeafLabels ();
    void setListOfLeafLabels(HashMap<String, Sequence> listOfLeafLabels);
}
