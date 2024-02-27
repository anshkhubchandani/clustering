package de.unijena.bioinf.dm23.grp06.algo;

import de.unijena.bioinf.dm23.grp06.model.Node;

import java.util.HashMap;

public class NeighbourJoining implements Clustering {

    private DistanceMatrix distanceMatrix;
    private HashMap<String, Integer> r;
    private int minM;
    private TaxonPair minMCluster;

    public NeighbourJoining (DistanceMatrix distMatrix) {
        this.distanceMatrix = distMatrix;
    }

    private void calculateR () {
        // Vor dem Aufruf, Eintrag aus r löschen (die Knoten, die verknüpft wurden) und neue Knoten einfügen!!

        int score;
        int newScore;
        String[] keyList = r.keySet().toArray(new String[0]);

        for (String taxon : keyList) { // für alle aktuellen Taxa

            newScore = 0;
            score = 0;

            for (String secondTaxon : keyList) { // zu allen aktuellen Taxa

                if (!taxon.equals(secondTaxon)) {
                    score += distanceMatrix.getDistance(taxon, secondTaxon);
                }

            }

            newScore = (1/keyList.length-2)*score;
            r.replace(taxon, newScore);
        }

    }

    private void calculateMinM () { // Matrix M muss nicht komplett gespeichert werden
        // Aus r wurden die alten Knoten gelöscht und der neue Knoten eingefügt!

        String[] nodeList = r.keySet().toArray(new String[0]);

        int j = 1;
        int scoreM;

        for (String s : nodeList) {

            for (int k = j; k < nodeList.length; k++) {

                scoreM = distanceMatrix.getDistance(s, nodeList[k]) - (r.get(s) + r.get(nodeList[k]));
                if (scoreM < minM) {
                    minM = scoreM;
                    minMCluster = new TaxonPair(s, nodeList[k]);
                }

            }

            j++;
        }

    }

    @Override
    public Node nextCluster() { // oder TaxonPair als return-Wert?

        int distanceToFirst = (distanceMatrix.getDistance(minMCluster.first, minMCluster.second) + r.get(minMCluster.first) - r.get(minMCluster.second)) / 2;
        int distanceToSecond = distanceMatrix.getDistance(minMCluster.first, minMCluster.second) - distanceToFirst;

        // TODO !!!

        return null;
    }


}
