package de.unijena.bioinf.dm23.grp06.model;

import java.util.Objects;

public class TreeNode {

    private final String label;
    private TreeNode firstConnectedNode;
    private TreeNode secondConnectedNode;
    private TreeNode thirdConnectedNode;
    double distanceToFirst;
    double distanceToSecond;
    int depth; // hier nochmal schauen!
    boolean isLeaf;

    public TreeNode (String label, TreeNode first, double distanceToFirst, TreeNode second, double distanceToSecond) {
        this.label = label;
        this.firstConnectedNode = first;
        this.distanceToFirst = distanceToFirst;
        this.secondConnectedNode = second;
        this.distanceToSecond = distanceToSecond;
    }

    public TreeNode (String leafLabel) {
        this.label = leafLabel;
        this.isLeaf = true;
        this.depth = 0;
    }

    public void setFirstConnectedNode (TreeNode firstNode) {
        this.firstConnectedNode = firstNode;
    }
    public void setSecondConnectedNode (TreeNode secondNode) {
        this.secondConnectedNode = secondNode;
    }
    public void setThirdConnectedNode (TreeNode thirdNode) {
        this.thirdConnectedNode = thirdNode;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        else if (obj instanceof TreeNode) {
            TreeNode anotherNode = (TreeNode) obj;

        }
        return super.equals(obj);
    }
}
