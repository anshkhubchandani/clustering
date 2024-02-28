package de.unijena.bioinf.dm23.grp06.algo;


import java.util.ArrayList;
import java.util.HashMap;

public class DistanceMatrix {

    private HashMap<TaxonPair, Integer> distances;

    private TaxonPair minDistancePair;

    public DistanceMatrix() {
        distances = new HashMap<>();
    }

    protected int getMinimumDistance () {
        return distances.get(minDistancePair);
    }

    protected TaxonPair getMinDistancePair () {
        return minDistancePair;
    }

    protected int getDistance (String taxon1, String taxon2) {
        return distances.get(new TaxonPair(taxon1, taxon2));
    }

    protected void addDistance (String taxon1, String taxon2, int distance) {
        TaxonPair tempTaxon = new TaxonPair(taxon1, taxon2);
        if (!distances.containsKey(tempTaxon)) {
            distances.put(tempTaxon, distance);

            if (isNewMinimum(distance)) {
                this.minDistancePair = tempTaxon;
            }
        }
        else {
            // Fehlermeldung ?
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

    private boolean isNewMinimum (int newDistance) {
        return (this.minDistancePair == null || newDistance < distances.get(minDistancePair));
    }
}
