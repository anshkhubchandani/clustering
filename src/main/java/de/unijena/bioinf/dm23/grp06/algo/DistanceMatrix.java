package de.unijena.bioinf.dm23.grp06.algo;


import java.util.HashMap;

public class DistanceMatrix {

    private HashMap<TaxonPair, Double> distances;

    private TaxonPair minDistancePair;

    public DistanceMatrix() {
        distances = new HashMap<>();
    }

    protected TaxonPair getMinimumDistancePair() {
        return minDistancePair;
    }

    protected double getDistance (String taxon1, String taxon2) {
        return distances.get(new TaxonPair(taxon1, taxon2));
    }

    protected void addDistance (String taxon1, String taxon2, double distance) {
        TaxonPair tempTaxon = new TaxonPair(taxon1, taxon2);
        if (!distances.containsKey(tempTaxon)) {
            distances.put(tempTaxon, distance);

            if (isNewMinimum(distance)) {
                this.minDistancePair = tempTaxon;
            }
        }
        else {
            System.err.println("Couldn't add new distance to distance matrix.");
        }
    }

    protected void deleteDistances (String taxon1, String taxon2) {

        TaxonPair[] keyArray = distances.keySet().toArray(new TaxonPair[0]);

        for (TaxonPair tp : keyArray) {
            if (tp.containsTaxon(taxon1)) {
                distances.remove(tp);
            }
            else if (tp.containsTaxon(taxon2)) {
                distances.remove(tp);
            }
        }
    }

    protected void findNewMinimum () {
        TaxonPair[] keyArray = distances.keySet().toArray(new TaxonPair[0]);
        double tempMinDistance = Double.MAX_VALUE;

        for (TaxonPair tp : keyArray) {
            if (distances.get(tp) < tempMinDistance) {
                tempMinDistance = distances.get(tp);
                this.minDistancePair = tp;
            }
        }
    }

    private boolean isNewMinimum (double newDistance) {
        return (this.minDistancePair == null || newDistance < distances.get(minDistancePair));
    }

    // importDistanceMatrix mit DistanzmatrixReader !!
}
