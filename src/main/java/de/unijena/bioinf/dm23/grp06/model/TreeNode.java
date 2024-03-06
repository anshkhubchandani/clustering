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

    /* public TreeNode (String label, TreeNode first, double distanceToFirst, TreeNode second, double distanceToSecond) {
        this.label = label;
        this.firstConnectedNode = first;
        this.distanceToFirst = distanceToFirst;
        this.secondConnectedNode = second;
        this.distanceToSecond = distanceToSecond;
    } */

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

    /* @Override
    public int hashCode() {
        // TODO z.B. primzahl * (labelhashcode + isleafhashcode)
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        else if (obj == null) {
            return false;
        }
        else if (!getClass().equals(obj.getClass())) {
            return false;
        }

        TreeNode anotherNode = (TreeNode) obj;
        return (Objects.equals(this.label, anotherNode.label) && Objects.equals(this.isLeaf, anotherNode.isLeaf));
    } */
}
