package de.unijena.bioinf.dm23.clustering.algo;
import java.util.ArrayList;
import java.util.HashMap;

public class DistanceMatrix {
    /**
     * The DistanceMatrix class represents a matrix containing pairwise distances between taxa.
     */
    private final HashMap<LabelPair, Double> distances;
    private ArrayList<String> nodesInMatrix;

    /**
     * Constructs a new DistanceMatrix object.
     */
    public DistanceMatrix() {
        distances = new HashMap<>();
        nodesInMatrix = new ArrayList<>();
    }
    /**
     * Adds a distance between two taxa to the distance matrix.
     *
     * @param taxon1   the first taxon
     * @param taxon2   the second taxon
     * @param distance the distance between the taxa
     * @throws InvalidDistanceException if the provided distance is invalid
     */
    public void addDistance(String taxon1, String taxon2, double distance) throws InvalidDistanceException {

        if (Double.isNaN(distance) ||Double.isInfinite(distance)||distance <0){
            throw new InvalidDistanceException("Invalid distance provided: " + distance);
        }
        LabelPair tempTaxon = new LabelPair(taxon1, taxon2);
            distances.put(tempTaxon, distance);

    }

    /**
     * Adds a taxon to the list of nodes in the matrix.
     *
     * @param taxon the taxon to add
     */
    public void addToNodeList (String taxon) {
        if (!nodesInMatrix.contains(taxon)) {
            nodesInMatrix.add(taxon);
        }
    }

    /**
     * Deletes distances involving the specified taxa from the distance matrix.
     *
     * @param taxon1 the first taxon
     * @param taxon2 the second taxon
     */
    protected void deleteDistances (String taxon1, String taxon2) {

        deleteFromNodeList(taxon1);
        deleteFromNodeList(taxon2);

        LabelPair[] keyArray = distances.keySet().toArray(new LabelPair[0]);

        for (LabelPair tp : keyArray) {
            if (tp.containsTaxon(taxon1)) {
                distances.remove(tp);
            }
            else if (tp.containsTaxon(taxon2)) {
                distances.remove(tp);
            }
        }

    }
    /**
     * Retrieves the distance between two taxa.
     *
     * @param taxon1 the first taxon
     * @param taxon2 the second taxon
     * @return the distance between the taxa
     */

    public double getDistance (String taxon1, String taxon2) {
       return taxon1.equals(taxon2) ? 0 : this.distances.get(new LabelPair(taxon1, taxon2));
    }

    /**
     * Retrieves the list of nodes in the matrix.
     *
     * @return the list of nodes
     */
    public ArrayList<String> getNodesInMatrix () {
        return this.nodesInMatrix;
    }

    /**
     * Deletes distances involving the specified taxa from the distance matrix.
     *
     * @param taxon1 the first taxon
     * @param taxon2 the second taxon
     */
    private void deleteFromNodeList (String nodeLabel) {
        this.nodesInMatrix.remove(nodeLabel);
    }

    protected LabelPair getMinimumDistancePair () {//hier
        LabelPair[] allKeys = distances.keySet().toArray(new LabelPair[0]);
        double tempMinDistance = Double.MAX_VALUE;
        LabelPair minNodePair = allKeys[0];

        for (LabelPair np : allKeys) {
            if (distances.get(np) < tempMinDistance) {
                tempMinDistance = distances.get(np);
                minNodePair = np;
            }
        }
        return minNodePair;
    }

    /**
     * This method tests if the given metric is valid.
     * @return true, if metric is valid, otherwise false.
     */

    public boolean checkMetric () {
        if(distances.isEmpty()){
            System.out.println("ERROR: Distance matrix is null");
            return false;
        }
        //Symmetrie
        else if (!isSymmetric()){
            System.out.println("ERROR: Distance matrix is not symmetric.");
            return false;
        }
        //Positive Definitheit
        else if(!isPositiveDefinite()){
            System.out.println("ERROR: Distance matrix is not positive definite.");
            return false;
        }
        //Dreiecksungleichung
        else if(!satisfiesTriangleInequality()){
            System.out.println("ERROR: Distance matrix does not satisfy the triangle inequality.");
            return false;
        }
        System.out.println("Metric is correct.");
        return true;
    }
    private boolean isSymmetric(){
        for (LabelPair np : distances.keySet()) {
            LabelPair reversePair = new LabelPair(np.getTaxon2(), np.getTaxon1());
            if (!distances.containsKey(reversePair) || !distances.get(reversePair).equals(distances.get(np))) {
                return false;
            }
        }
        return true;
    }

    private boolean isPositiveDefinite() {
        for (Double distance : distances.values()) {
            if (distance <= 0) {
                return false;
            }
        }
        return true;
    }
    private boolean satisfiesTriangleInequality() {
        for (String taxon1 : nodesInMatrix) {
            for (String taxon2 : nodesInMatrix) {
                for (String taxon3 : nodesInMatrix) {

                   if (!taxon1.equals(taxon2) && !taxon2.equals(taxon3) && !taxon1.equals(taxon3)) {
                        double dist12 = distances.getOrDefault(new LabelPair(taxon1, taxon2), 0.0); //Distanz wird auf 0 gesetzt, wenn keine Distanz vorliegt
                        double dist13 = distances.getOrDefault(new LabelPair(taxon1, taxon3), 0.0);
                        double dist23 = distances.getOrDefault(new LabelPair(taxon2, taxon3), 0.0);
                        // |a| + |b| >= |c| <- Korrekt

                       //das problem ist hier!!
                        if (dist12 + dist23 < dist13 || dist12 + dist13 < dist23 || dist13 + dist23 < dist12) {
                            return false;
                        }
                   }
                }
            }
        }
        return true;
    }

    public void checkConnection() throws MatrixNotConnectedException {
        for (String node : nodesInMatrix) {
            boolean connected = false;
            for (String otherNode : nodesInMatrix) {
                if (!node.equals(otherNode) && isConnected(node, otherNode)) {
                    connected = true;
                    break;
                }
            }
            if (!connected) {
                throw new MatrixNotConnectedException("Node " + node + " is not connected to any other node.");
            }
        }
    }

    private boolean isConnected(String node1, String node2) {
        return distances.containsKey(new LabelPair(node1, node2)) || distances.containsKey(new LabelPair(node2, node1));
    }

}
