package de.unijena.bioinf.dm23.clustering.model;

import java.util.ArrayList;

/**
 * The RootedTree class represents a rooted tree structure.
 */
public class RootedTree extends AbstractTree {

    /**
     * The type of the tree.
     */
    public final TreeType type = TreeType.ROOTED;
    TreeNode root;

    /**
     * Constructs a RootedTree with the given available node and list of tree nodes.
     *
     * @param available       the available node for the tree
     * @param nodesOfNewTree  the list of nodes in the tree
     */
    public RootedTree (TreeNode available, ArrayList<TreeNode> nodesOfNewTree) {
        super(available, nodesOfNewTree);
    }
    /**
     * Constructs a RootedTree with the given node.
     *
     * @param node  the node to be added to the tree
     */
    public RootedTree (TreeNode node) {
        super(node);
    }

    private void setRoot () {
        this.root = this.availableNode;
    }

    @Override
    public void setAvailableNode (TreeNode node) {
        this.availableNode = node;
        setRoot();
    }

    @Override
    public TreeType getTreeType() {
        return this.type;
    }
}
