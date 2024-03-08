package de.unijena.bioinf.dm23.clustering.algo;

import de.unijena.bioinf.dm23.clustering.model.*;
import de.unijena.bioinf.dm23.grp06.exactsearch.model.Sequence;

import java.util.HashMap;
/**
 * The TreeBuilder class is responsible for building trees using a specified clustering algorithm.
 */
public class TreeBuilder {

    private HashMap<String, Sequence> listOfLeafLabels;
    private HashMap<String, Tree> mapOfTrees;
    private final TreeType treeType;
    private final Clustering clusteringMethod;
    private Tree finishedTree;

    /**
     * Constructs a new TreeBuilder with the specified parameters.
     *
     * @param type               the type of the tree (rooted or unrooted)
     * @param clusteringAlgorithm the clustering algorithm to use
     * @param leafLabels         the labels of the leaf nodes
     */
    public TreeBuilder (TreeType type, Clustering clusteringAlgorithm, HashMap<String, Sequence> leafLabels) {
        this.mapOfTrees = new HashMap<>();
        this.treeType = type;
        this.clusteringMethod = clusteringAlgorithm;
        this.listOfLeafLabels = leafLabels;
        createLeaves();
    }

    private void createLeaves () {
        String[] leaves = this.listOfLeafLabels.keySet().toArray(new String[0]);

        for (String leaf : leaves) {
            addNewLeaf(new TreeNode(leaf, true));
        }
    }

    /**
     * Builds the tree using the specified clustering algorithm.
     */
    public void buildTree () {
        LabelPair nextCluster;
        String newLabel;

        while (!isFinishedBuilding()) {

            nextCluster = this.clusteringMethod.nextClusterCandidate();
            newLabel = nextCluster.first.concat("-").concat(nextCluster.second);

            connectTrees(nextCluster, newLabel, this.clusteringMethod.distanceToFirst(nextCluster), this.clusteringMethod.distanceToSecond(nextCluster));

            if (!isFinishedBuilding()) {
                this.clusteringMethod.updateDistanceMatrix(nextCluster, newLabel);
            }
        }

        if (treeType.equals(TreeType.UNROOTED)) {
            nextCluster = this.clusteringMethod.nextClusterCandidate();

            connectLastPartsOfUnrootedTree(this.mapOfTrees.get(nextCluster.first), this.mapOfTrees.get(nextCluster.second),
                    this.clusteringMethod.distanceToFirst(nextCluster));
        }

        String[] keys = this.mapOfTrees.keySet().toArray(new String[0]);
        this.finishedTree = mapOfTrees.get(keys[0]);
        this.finishedTree.setListOfLeafLabels(this.listOfLeafLabels);
    }

    private void connectTrees (LabelPair nextCluster, String newLabel, double distanceToFirst, double distanceToSecond) {

        Tree oneTree = mapOfTrees.get(nextCluster.first);
        Tree otherTree = mapOfTrees.get(nextCluster.second);
        mapOfTrees.remove(nextCluster.first);
        mapOfTrees.remove(nextCluster.second);

        TreeNode connectingNode = new TreeNode(newLabel, false);

        connectNodes(connectingNode, oneTree.getAvailableNode(), distanceToFirst);
        connectNodes(connectingNode, otherTree.getAvailableNode(), distanceToSecond);

        oneTree.addNode(connectingNode);
        oneTree.setAvailableNode(connectingNode);
        oneTree.addNodesFromList(otherTree.getNodeList());

        mapOfTrees.put(newLabel, oneTree);
    }

    private void connectNodes (TreeNode newNode, TreeNode nodeToAdd, double distance) {
        if (newNode.getFirstConnectedNode() == null) {
            newNode.setFirstConnectedNode(nodeToAdd, distance);
        }
        else if (newNode.getSecondConnectedNode() == null) {
            newNode.setSecondConnectedNode(nodeToAdd, distance);
        }
        nodeToAdd.setThirdConnectedNode(newNode, distance);
    }

    private void addNewLeaf (TreeNode node) {
        if (this.treeType.equals(TreeType.ROOTED)) {
            this.mapOfTrees.put(node.getLabel(), new RootedTree(node));
        }
        else if (this.treeType.equals(TreeType.UNROOTED)) {
            this.mapOfTrees.put(node.getLabel(), new UnrootedTree(node));
        }
    }

    public boolean isFinishedBuilding () {
        if (this.treeType.equals(TreeType.ROOTED) && (this.mapOfTrees.size() == 1)) {
            return true;
        }
        else if (this.treeType.equals(TreeType.UNROOTED) && (this.mapOfTrees.size() == 2)) {
            return true;
        }
        return false;
    }

    private void connectLastPartsOfUnrootedTree (Tree firstTree, Tree secondTree, double distance) {
        firstTree.getAvailableNode().setThirdConnectedNode(secondTree.getAvailableNode(), distance);
        secondTree.getAvailableNode().setThirdConnectedNode(firstTree.getAvailableNode(), distance);

        mapOfTrees.remove(firstTree.getAvailableNode().getLabel());
        mapOfTrees.remove(secondTree.getAvailableNode().getLabel());

        firstTree.addNodesFromList(secondTree.getNodeList());

        mapOfTrees.put(firstTree.getAvailableNode().getLabel(), firstTree);
    }

    /**
     * Returns the map of trees.
     *
     * @return the map of trees
     */
    public HashMap<String, Tree> getMapOfTrees () {
        return this.mapOfTrees;
    }

    /**
     * Returns the finished tree.
     *
     * @return the finished tree
     */

    public Tree getFinishedTree () {
        return this.finishedTree;
    }
}
