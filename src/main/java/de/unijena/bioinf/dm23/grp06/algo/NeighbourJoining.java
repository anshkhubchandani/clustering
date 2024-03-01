package de.unijena.bioinf.dm23.grp06.algo;

import de.unijena.bioinf.dm23.grp06.model.Node;

import java.util.ArrayList;
import java.util.HashMap;

public class NeighbourJoining implements Clustering {

    private DistanceMatrix distanceMatrix;
    private HashMap<String, Double> rValues;
    private double minM;
    private NodePair minMNodePair;

    public NeighbourJoining (DistanceMatrix distMatrix) {
        this.distanceMatrix = distMatrix;
    }

    @Override
    public Node nextCluster() { // oder TaxonPair als return-Wert?

        double distanceToFirst = (this.distanceMatrix.getDistance(minMNodePair.first, minMNodePair.second) + rValues.get(minMNodePair.first) - rValues.get(minMNodePair.second));
        double distanceToSecond = this.distanceMatrix.getDistance(minMNodePair.first, minMNodePair.second) - distanceToFirst;

        // TODO !!!

        return null;
    }

    private void calculateRValues () {

        double sumOfDistances;
        ArrayList<String> nodesInDistanceMatrix = this.distanceMatrix.getNodesInMatrix();

        for (String fromNode : nodesInDistanceMatrix) {
            sumOfDistances = 0;
            for (String toNode : nodesInDistanceMatrix) {

                if (!fromNode.equals(toNode)) {
                    sumOfDistances += this.distanceMatrix.getDistance(fromNode, toNode);
                }

            }
            this.rValues.put(fromNode, ( (1 / nodesInDistanceMatrix.size()-2) * sumOfDistances ));
        }

    }

    private void calculateMinM () {

        ArrayList<String> nodesInDistanceMatrix = this.distanceMatrix.getNodesInMatrix();

        for (String fromNode : nodesInDistanceMatrix) {

            for (String toNode : nodesInDistanceMatrix) {

                if (!fromNode.equals(toNode) &&
                        ((this.distanceMatrix.getDistance(fromNode, toNode) - (rValues.get(fromNode) + rValues.get(toNode))) < minM)) {

                    minM = this.distanceMatrix.getDistance(fromNode, toNode) - (rValues.get(fromNode) + rValues.get(toNode));
                    minMNodePair = new NodePair(fromNode, toNode);
                }
            }
        }
    }

    private void updateDistanceMatrix (String newClusterNode) {
        double nodeDistance;

        for (String nodeInMatrix : this.distanceMatrix.getNodesInMatrix()) {

            nodeDistance = (this.distanceMatrix.getDistance(minMNodePair.first, nodeInMatrix) +
                    this.distanceMatrix.getDistance(minMNodePair.second, nodeInMatrix) -
                    this.distanceMatrix.getDistance(minMNodePair.first, minMNodePair.second)) / 2;
            this.distanceMatrix.addDistance(nodeInMatrix, newClusterNode, nodeDistance);
        }

        this.distanceMatrix.deleteDistances(minMNodePair.first, minMNodePair.second);
    }

    /*
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
                    minMNodePair = new NodePair(s, nodeList[k]);
                }

            }

            j++;
        }

    }
*/

}
