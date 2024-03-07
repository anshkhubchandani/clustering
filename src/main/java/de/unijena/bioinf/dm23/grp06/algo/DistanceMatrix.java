package de.unijena.bioinf.dm23.grp06.algo;


import java.util.ArrayList;
import java.util.HashMap;

public class DistanceMatrix {

    private final HashMap<LabelPair, Double> distances;
    private ArrayList<String> nodesInMatrix;

    public DistanceMatrix() {
        distances = new HashMap<>();
        nodesInMatrix = new ArrayList<>();
    }

    public void addDistance(String taxon1, String taxon2, double distance) throws InvalidDistanceException {
        //addToNodeList(taxon1);
        //addToNodeList(taxon2);
        if (Double.isNaN(distance) ||Double.isInfinite(distance)||distance <0){
            throw new InvalidDistanceException("Invalid distance provided: " + distance);
        }
        LabelPair tempTaxon = new LabelPair(taxon1, taxon2);
        if (!distances.containsKey(tempTaxon)) {
            distances.put(tempTaxon, distance);
        }
        else {
            System.err.println("Couldn't add new distance to distance matrix.");
        }
    }

    public void addToNodeList (String taxon) {
        if (!nodesInMatrix.contains(taxon)) {
            nodesInMatrix.add(taxon);
        }
    }

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
    
    public double getDistance (String taxon1, String taxon2) {
        old : return taxon1.equals(taxon2) ? 0 : this.distances.get(new LabelPair(taxon1, taxon2));
    }
    public ArrayList<String> getNodesInMatrix () {
        return this.nodesInMatrix;
    }

    private void deleteFromNodeList (String nodeLabel) {
        this.nodesInMatrix.remove(nodeLabel);
    }

    protected LabelPair getMinimumDistancePair () {
        //hier
        /*if (distances.isEmpty()){
            throw new IllegalStateException("The distance map is empty!");
        }*/

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


    /*protected NodePair getMinimumDistancePair() {
        return this.minDistancePair;
    }*/

    /*protected double getMinDistance () {
        return this.distances.get(this.minDistancePair);
    }*/

    //test
    /*
    public static void main(String[] args) throws InvalidDistanceException {
        DistanceMatrix dm = new DistanceMatrix();
        dm.addDistance("A", "B", 1.0);
        dm.addDistance("B", "C", 2.0);
        dm.addDistance("A", "C", 3.0);
        dm.addToNodeList("A");
        dm.addToNodeList("B");
        dm.addToNodeList("C");
        dm.checkMetric();
    } */

}
