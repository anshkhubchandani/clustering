package de.unijena.bioinf.dm23.grp06.model;

import java.util.Objects;

public class TreeNode {

    private final String label;
    private TreeNode firstConnectedNode;
    private TreeNode secondConnectedNode;
    private TreeNode thirdConnectedNode;
    public double distanceToFirst;
    public double distanceToSecond;
    double distanceToThird;
    public final boolean isLeaf;

    public TreeNode (String leafLabel, boolean leaf) {
        this.label = leafLabel;
        this.isLeaf = leaf;
    }

    public void setFirstConnectedNode (TreeNode firstNode, double distance) {
        this.firstConnectedNode = firstNode;
        this.distanceToFirst = distance;
    }
    public void setSecondConnectedNode (TreeNode secondNode, double distance) {
        this.secondConnectedNode = secondNode;
        this.distanceToSecond = distance;
    }
    public void setThirdConnectedNode (TreeNode thirdNode, double distance) {
        this.thirdConnectedNode = thirdNode;
        this.distanceToThird = distance;
    }

    public TreeNode getFirstConnectedNode() {
        return firstConnectedNode;
    }
    public TreeNode getSecondConnectedNode() {
        return secondConnectedNode;
    }
    public TreeNode getThirdConnectedNode() {
        return thirdConnectedNode;
    }

    public String getLabel() {
        return label;
    }

}
