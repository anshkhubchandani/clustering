package de.unijena.bioinf.dm23.grp06.algo;


import java.util.ArrayList;
import java.util.HashMap;

public class DistanceMatrix {

    private final HashMap<NodePair, Double> distances;
    private ArrayList<String> nodesInMatrix;
    private NodePair minDistancePair;

    public DistanceMatrix() {
        distances = new HashMap<>();
    }

    protected void addDistance (String taxon1, String taxon2, double distance) {
        NodePair tempTaxon = new NodePair(taxon1, taxon2);
        if (!distances.containsKey(tempTaxon)) {
            distances.put(tempTaxon, distance);
        }
        else {
            System.err.println("Couldn't add new distance to distance matrix.");
        }
    }

    protected void deleteDistances (String taxon1, String taxon2) {

        NodePair[] keyArray = distances.keySet().toArray(new NodePair[0]);

        for (NodePair tp : keyArray) {
            if (tp.containsTaxon(taxon1)) {
                distances.remove(tp);
            }
            else if (tp.containsTaxon(taxon2)) {
                distances.remove(tp);
            }
        }

        findNewMinimum();
    }
    
    protected double getDistance (String taxon1, String taxon2) {
        return this.distances.get(new NodePair(taxon1, taxon2));
    }


    //TODO Test if empty
    protected boolean checkMetric () {
        //Symmetrie
        if (!isSymmetric()){
            System.out.println("ERROR: Distance matrix is not symmetric.");
            return false;
        }
        //Positive Definitheit
        if(!isPositiveDefinite()){
            System.out.println("ERROR: Distance matrix is not positive definite.");
            return false;
        }
        //Dreiecksungleichung
        if(!satisfiesTriangleInequality()){
            System.out.println("ERROR: Distance matrix does not satisfy the triangle inequality.");
            return false;
        }
        return true;
    }
    private boolean isSymmetric(){
        for (NodePair np : distances.keySet()) {
            NodePair reversePair = new NodePair(np.getTaxon2(), np.getTaxon1());
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
                        double dist12 = distances.getOrDefault(new NodePair(taxon1, taxon2), 0.0); //Distanz wird auf 0 gesetzt, wenn keine Distanz vorliegt
                        double dist13 = distances.getOrDefault(new NodePair(taxon1, taxon3), 0.0);
                        double dist23 = distances.getOrDefault(new NodePair(taxon2, taxon3), 0.0);
                        // |a| + |b| >= |c|
                        if (dist12 + dist23 < dist13 || dist12 + dist13 < dist23 || dist13 + dist23 < dist12) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }


    protected NodePair getMinimumDistancePair() {
        return this.minDistancePair;
    }
    
    protected ArrayList<String> getNodesInMatrix () {
        return this.nodesInMatrix;
    }

    protected void findNewMinimum () {
        NodePair[] allKeys = distances.keySet().toArray(new NodePair[0]);
        double tempMinDistance = Double.MAX_VALUE;

        for (NodePair np : allKeys) {
            if (distances.get(np) < tempMinDistance) {
                tempMinDistance = distances.get(np);
                this.minDistancePair = np;
            }
        }
    }
    //test
//    public static void main(String[] args) {
//        DistanceMatrix distanceMatrix = new DistanceMatrix();
//        distanceMatrix.addDistance("Taxon1", "Taxon2", 1.0);
//        distanceMatrix.addDistance("Taxon1", "Taxon2", 2.0);
//        distanceMatrix.addDistance("Taxon1", "Taxon2", 5.0);
//        distanceMatrix.checkMetric();
//    }

}
