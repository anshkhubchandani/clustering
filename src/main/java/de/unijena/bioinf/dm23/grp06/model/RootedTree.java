package de.unijena.bioinf.dm23.grp06.model;

import java.util.ArrayList;

public class RootedTree extends AbstractTree {

    public final TreeType type = TreeType.ROOTED;
    TreeNode root;

    public RootedTree (ArrayList<TreeNode> nodesOfNewTree) {
        super(nodesOfNewTree);
    }

    protected void setRoot () {
        this.root = this.availableNode;
    }
}
