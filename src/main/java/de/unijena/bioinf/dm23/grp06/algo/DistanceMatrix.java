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

}
