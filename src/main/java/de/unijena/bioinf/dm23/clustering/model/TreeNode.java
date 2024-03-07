package de.unijena.bioinf.dm23.clustering.model;

/**
 * Represents a node in a phylogenetic tree.
 */
public class TreeNode {

    private final String label;
    private TreeNode firstConnectedNode;
    private TreeNode secondConnectedNode;
    private TreeNode thirdConnectedNode;
    public double distanceToFirst;
    public double distanceToSecond;
    private double distanceToThird;
    public final boolean isLeaf;

    /**
     * Constructs a tree node with the given label.
     *
     * @param leafLabel the label of the node
     * @param leaf      true if the node is a leaf, false otherwise
     */
    public TreeNode(String leafLabel, boolean leaf) {
        this.label = leafLabel;
        this.isLeaf = leaf;
    }

    /**
     * Sets the first node connected to this node and the distance to it.
     *
     * @param firstNode the first connected node
     * @param distance  the distance to the first node
     */
    public void setFirstConnectedNode(TreeNode firstNode, double distance) {
        this.firstConnectedNode = firstNode;
        this.distanceToFirst = distance;
    }

    /**
     * Sets the second node connected to this node and the distance to it.
     *
     * @param secondNode the second connected node
     * @param distance   the distance to the second node
     */
    public void setSecondConnectedNode(TreeNode secondNode, double distance) {
        this.secondConnectedNode = secondNode;
        this.distanceToSecond = distance;
    }

    /**
     * Sets the third node connected to this node and the distance to it.
     *
     * @param thirdNode the third connected node
     * @param distance  the distance to the third node
     */
    public void setThirdConnectedNode(TreeNode thirdNode, double distance) {
        this.thirdConnectedNode = thirdNode;
        this.distanceToThird = distance;
    }

    /**
     * Gets the first connected node.
     *
     * @return the first connected node
     */
    public TreeNode getFirstConnectedNode() {
        return firstConnectedNode;
    }

    /**
     * Gets the second connected node.
     *
     * @return the second connected node
     */
    public TreeNode getSecondConnectedNode() {
        return secondConnectedNode;
    }

    /**
     * Gets the third connected node.
     *
     * @return the third connected node
     */
    public TreeNode getThirdConnectedNode() {
        return thirdConnectedNode;
    }

    /**
     * Gets the label of the node.
     *
     * @return the label of the node
     */
    public String getLabel() {
        return label;
    }
}
