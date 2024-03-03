package de.unijena.bioinf.dm23.grp06.model;

import java.util.ArrayList;

public class UnrootedTree extends AbstractTree {

    public final TreeType type = TreeType.UNROOTED;

    public UnrootedTree (ArrayList<TreeNode> nodesOfNewTree) {
        super(nodesOfNewTree);
    }
}
