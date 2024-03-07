package de.unijena.bioinf.dm23.grp06.model;

import java.util.ArrayList;
/**
 * Represents an unrooted phylogenetic tree.
 * <p>
 * In an unrooted tree, no node is designated as the root. Instead, all nodes
 * are connected in a way that represents the evolutionary relationships
 * among the taxa, but without a clear hierarchical structure.
 */
public class UnrootedTree extends AbstractTree {

    /**
     * The type of the tree, which is always {@code TreeType.UNROOTED}.
     */
    public final TreeType type = TreeType.UNROOTED;

    /**
     * Constructs an unrooted tree with the specified available node and list of tree nodes.
     *
     * @param available     The available node in the unrooted tree.
     * @param nodesOfNewTree The list of tree nodes in the unrooted tree.
     */
    public UnrootedTree (TreeNode available, ArrayList<TreeNode> nodesOfNewTree) {
        super(available, nodesOfNewTree);
    }

    /**
     * Constructs an unrooted tree with the specified node.
     *
     * @param node The node to be included in the unrooted tree.
     */
    public UnrootedTree (TreeNode node) {
        super (node);
    }

    /**
     * Retrieves the type of the tree.
     *
     * @return The type of the tree, which is always {@code TreeType.UNROOTED}.
     */
    @Override
    public TreeType getTreeType() {
        return this.type;
    }
}
