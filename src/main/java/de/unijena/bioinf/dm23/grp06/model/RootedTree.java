package de.unijena.bioinf.dm23.grp06.model;

import java.util.ArrayList;

public class RootedTree extends AbstractTree {

    public final TreeType type = TreeType.ROOTED;
    TreeNode root;

    public RootedTree (TreeNode available, ArrayList<TreeNode> nodesOfNewTree) {
        super(available, nodesOfNewTree);
    }

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
