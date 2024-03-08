package de.unijena.bioinf.dm23.clustering.model;
/**
 * Enumeration representing the types of phylogenetic trees.
 * <p>
 * This enumeration defines two types of trees: rooted and unrooted.
 */
public enum TreeType {
    /**
     * Rooted phylogenetic tree.
     * <p>
     * In a rooted tree, one node is designated as the root, and all other nodes
     * are connected in a hierarchical manner, branching out from the root.
     */
    ROOTED,
    /**
     * Unrooted phylogenetic tree.
     * <p>
     * In an unrooted tree, no node is designated as the root. Instead, all nodes
     * are connected in a way that represents the evolutionary relationships
     * among the taxa, but without a clear hierarchical structure.
     */
    UNROOTED
}
