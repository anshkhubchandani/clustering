package de.unijena.bioinf.dm23.grp06.model;

public class TreeNode {

    public final String label;
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

    public TreeNode (String nodeLabel) {
        this.label = nodeLabel;
        this.isLeaf = true;
        this.depth = 0;
    }

    protected void setFirstConnectedNode (TreeNode firstNode) {
        this.firstConnectedNode = firstNode;
    }
    protected void setSecondConnectedNode (TreeNode secondNode) {
        this.secondConnectedNode = secondNode;
    }
    protected void setThirdConnectedNode (TreeNode thirdNode) {
        this.thirdConnectedNode = thirdNode;
    }
}
