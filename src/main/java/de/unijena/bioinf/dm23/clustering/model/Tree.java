package de.unijena.bioinf.dm23.clustering.model;

import de.unijena.bioinf.dm23.grp06.exactsearch.model.Sequence;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * The Tree interface represents a phylogenetic tree.
 */
public interface Tree {

    /**
     * Gets the type of the tree.
     *
     * @return the type of the tree
     */
    TreeType getTreeType();

    /**
     * Gets the list of tree nodes.
     *
     * @return the list of tree nodes
     */
    ArrayList<TreeNode> getNodeList();

    /**
     * Gets the available node in the tree.
     *
     * @return the available node
     */
    TreeNode getAvailableNode();

    /**
     * Sets the available node in the tree.
     *
     * @param node the node to set as available
     */
    void setAvailableNode(TreeNode node);

    /**
     * Adds a list of nodes to the tree.
     *
     * @param newNodes the list of nodes to add
     */
    void addNodesFromList(ArrayList<TreeNode> newNodes);

    /**
     * Adds a node to the tree.
     *
     * @param node the node to add
     */
    void addNode(TreeNode node);

    /**
     * Gets the list of leaf labels associated with the tree.
     *
     * @return the list of leaf labels
     */
    HashMap<String, Sequence> getListOfLeafLabels();

    /**
     * Sets the list of leaf labels associated with the tree.
     *
     * @param listOfLeafLabels the list of leaf labels to set
     */
    void setListOfLeafLabels(HashMap<String, Sequence> listOfLeafLabels);
}
