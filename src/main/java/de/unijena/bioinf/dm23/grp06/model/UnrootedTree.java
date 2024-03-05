package de.unijena.bioinf.dm23.grp06.model;

import java.util.ArrayList;

public class UnrootedTree extends AbstractTree {

    public final TreeType type = TreeType.UNROOTED;

    public UnrootedTree (TreeNode available, ArrayList<TreeNode> nodesOfNewTree) {
        super(available, nodesOfNewTree);
    }

    public UnrootedTree (TreeNode node) {
        super (node);
    }

    @Override
    public TreeType getTreeType() {
        return this.type;
    }
}
